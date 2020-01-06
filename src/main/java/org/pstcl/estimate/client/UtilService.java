package org.pstcl.estimate.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.model.EstimateDetailsModel;
import org.pstcl.estimate.model.EstimateModel;
import org.pstcl.estimate.repository.EstimateReplicationLogRepository;
import org.pstcl.estimate.repository.EstimateRepository;
import org.pstcl.estimate.repository.WorkRepository;
import org.pstcl.estimate.util.GlobalProperties;
import org.pstcl.estimate.util.entity.EstimateReplicationLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class UtilService {

	static final Logger logger = LoggerFactory.getLogger("ClientEstimateLogger1");


	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private EstimateRepository estimateRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GlobalProperties globalProperties;

	@Autowired
	private EstimateReplicationLogRepository estimateReplicationLogRepository;


	@Scheduled(fixedRate = 5* 60 * 1000)
	public void clientHttpRequest() {
		try {

			logger.info("INITIATING HTTP REQUEST");
			List<HttpMessageConverter<?>> msgConverters = restTemplate.getMessageConverters();
			msgConverters.add(new MappingJackson2HttpMessageConverter());
			restTemplate.setMessageConverters(msgConverters);
			String url = new String(globalProperties.urlEstimateListUrl());

			logger.info("HTTP URL"+url);
			EstimateModel result = restTemplate.getForObject(url, EstimateModel.class);
			List<Estimate> list = result.getNewEstimates();
			if (CollectionUtils.isNotEmpty(list)) {
				Collections.sort(list);
				logger.info("NUMBER OF ESTIMATES RETURNED:-"+list);				
				for (Estimate estimate : list) {
					getEstiamteDetails(estimate);
				}
			}
		} catch (RestClientException e) {
			logger.error(e.getMessage());	
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.error(e.getMessage());	
			e.printStackTrace();
		}
		
	}


	@Transactional
	private void getEstiamteDetails(Estimate estimate) {
		final String uri = "http://" + globalProperties.getServer() + ":" + globalProperties.getPort()
		+ "/estimateDetails/{estimateCode}";

		logger.info("HTTP REQUEST ESTIMATE DETAILS:-"+uri);
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("estimateCode", estimate.getEstimateCode());
			EstimateDetailsModel result = restTemplate.getForObject(uri, EstimateDetailsModel.class, params);
			Estimate estimate2 = result.getEstimate();
			logger.info("ESTIMATE DETAILS"+estimate2.getEstimateCode());

			Optional<Estimate> alreadyExistingEstimate= estimateRepository.findById(estimate2.getEstimateCode());
			logger.info(estimate2.getEstimateCode()+" is already in the db? "+alreadyExistingEstimate.isPresent());
			logger.info("ESTIMATE"+estimate2);
			if(alreadyExistingEstimate.isPresent())
			{
				if(null!=alreadyExistingEstimate.get().getDtUpdated())
				{
					if(estimate2.getDtUpdated().isAfter(alreadyExistingEstimate.get().getDtUpdated()))
					{
						saveOrUpdateEstimate(result, estimate2);

					}
					else
					{
						confirmReplication(estimate2);
					}
				}
				else
				{
					saveOrUpdateEstimate(result, estimate2);
				}
			}
			else
			{
				saveOrUpdateEstimate(result, estimate2);

			}

		} catch (Exception e) {
			logger.error("ERROR fetching estimate details for "+estimate);
			logger.error("EXCEPTION"+e.getMessage());
			replicationFailed(estimate);
			e.printStackTrace();
		}
	}


	private void saveOrUpdateEstimate(EstimateDetailsModel result, Estimate estimate2) {
		if(null!=estimate2)
		{
			if(null!=estimate2.getWorkMaster())
			{
				try {
					logger.info("Getting Work from DB "+ estimate2.getWorkMaster().getWorkCode());


					if(!workRepository.findById(estimate2.getWorkMaster().getWorkCode()).isPresent())
					{
						logger.info("Saving Work to DB "+ estimate2.getWorkMaster().getWorkCode());
						workRepository.save(estimate2.getWorkMaster());	
					}
					logger.info("Adding cost and item details to estimate "+ estimate2.getWorkMaster().getWorkCode());

					estimate2.setEstimateCostDetails(result.getEstimateCostDetails());
					estimate2.setEstimateItemDetails(result.getEstimateItemDetails());
					logger.info("Saving Estimate to DB "+ estimate2.getEstimateCode());
					estimateRepository.save(estimate2);

					confirmReplication(estimate2);
				} catch (Exception e) {
					logger.error("ERROR SAVING ESTIMATE"+e.getMessage());
					e.printStackTrace();
					replicationFailed(estimate2);

				}
			}

		}
	}



	private void confirmReplication(Estimate estimate) {
		try {
			final String uri = "http://" + globalProperties.getServer() + ":" + globalProperties.getPort()
			+ "/confirmReplication/{estimateCode}";

			logger.info("CONFIRMATION FOR REPLICATION TO SERVER:-"+estimate.getEstimateCode());

			logger.info("CONFIRMATION uri:-"+uri);

			Map<String, String> params = new HashMap<String, String>();
			params.put("estimateCode", estimate.getEstimateCode());
			EstimateDetailsModel result = restTemplate.getForObject(uri, EstimateDetailsModel.class, params);
			logReplication(estimate);
		}
		catch(Exception e)
		{
			logger.error("ERROR CONFIRMING REPLICATION"+e.getMessage());

		}
	}

	public Estimate logReplication(Estimate estimate) {
		try {
			logger.info("SUCCESSFULLY REPLICATED ESTIMATE:-"+estimate.getEstimateCode());


			if (estimate != null) {
				EstimateReplicationLog estimateReplicationLog = new EstimateReplicationLog();
				estimateReplicationLog.setEstimate(estimate);
				estimateReplicationLogRepository.save(estimateReplicationLog);
			}
		}		catch(Exception e)
		{
			logger.error("ERROR logging REPLICATION to client db"+e.getMessage());

		}

		return estimate;
	}

	private void replicationFailed(Estimate estimate) {
		try {
			final String uri = "http://" + globalProperties.getServer() + ":" + globalProperties.getPort()
			+ "/replicationFailed/{estimateCode}";

			logger.info("FAILURE NOTICE FOR REPLICATION TO SERVER:-"+estimate.getEstimateCode());

			logger.info("CONFIRMATION uri:-"+uri);

			Map<String, String> params = new HashMap<String, String>();
			params.put("estimateCode", estimate.getEstimateCode());
			EstimateDetailsModel result = restTemplate.getForObject(uri, EstimateDetailsModel.class, params);

		}		catch(Exception e)
		{
			logger.error("ERROR SENDING REPLICATION FAILURE TO SERVER"+e.getMessage());

		}
	}


}
