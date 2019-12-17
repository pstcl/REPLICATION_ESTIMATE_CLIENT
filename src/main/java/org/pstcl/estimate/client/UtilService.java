package org.pstcl.estimate.client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.entity.EstimateCostDetail;
import org.pstcl.estimate.entity.EstimateItemDetail;
import org.pstcl.estimate.model.EstimateDetailsModel;
import org.pstcl.estimate.model.EstimateModel;
import org.pstcl.estimate.repository.EstimateReplicationLogRepository;
import org.pstcl.estimate.repository.EstimateRepository;
import org.pstcl.estimate.util.GlobalProperties;
import org.pstcl.estimate.util.entity.EstimateReplicationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UtilService {

	@Autowired
	private EstimateRepository estimateRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GlobalProperties globalProperties;

	@Autowired
	private EstimateReplicationLogRepository estimateReplicationLogRepository;

	@Transactional
	@Scheduled(fixedRate = 60 * 1000)
	public void clientHttpRequest() {
		List<HttpMessageConverter<?>> msgConverters = restTemplate.getMessageConverters();
		msgConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(msgConverters);
		String url = new String(globalProperties.urlEstimateListUrl());
		EstimateModel result = restTemplate.getForObject(url, EstimateModel.class);
		List<Estimate> list = result.getNewEstimates();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Estimate estimate : list) {

				getRequest(estimate);
			}
		}
	}

	private void getRequest(Estimate estimate) {
		final String uri = "http://" + globalProperties.getServer() + ":" + globalProperties.getPort()
				+ "/estimateDetails/{estimateCode}";

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("estimateCode", estimate.getEstimateCode());
			EstimateDetailsModel result = restTemplate.getForObject(uri, EstimateDetailsModel.class, params);
			Estimate estimate2 = result.getEstimate();
			System.out.println("FIND THIS"+estimate2.getEstimateCode());
			System.out.println("FIND THIS"+estimate2.getEstimateCode());
			System.out.println("FIND THIS"+estimate2.getEstimateCode());
			System.out.println("FIND THIS"+estimate2.getEstimateCode());
			System.out.println("FIND THIS"+estimate2.getEstimateCode());
			
			Optional<Estimate> alreadyExistingEstimate= estimateRepository.findById(estimate2.getEstimateCode());
			if(alreadyExistingEstimate.isPresent())
			{
				if(estimate2.getDtUpdated().isAfter(alreadyExistingEstimate.get().getDtUpdated()))
				{
					saveOrUpdateEstimate(result, estimate2);

				}
			}
			else
			{
				saveOrUpdateEstimate(result, estimate2);
				
			}
			
		} catch (RestClientException e) {
			replicationFailed(estimate);
			e.printStackTrace();
		}
	}

	private void saveOrUpdateEstimate(EstimateDetailsModel result, Estimate estimate2) {
		estimate2.setEstimateCostDetails(result.getEstimateCostDetails());
		estimate2.setEstimateItemDetails(result.getEstimateItemDetails());
		estimateRepository.save(estimate2);
		confirmReplication(estimate2);
	}
	
	

	private void confirmReplication(Estimate estimate) {
		final String uri = "http://" + globalProperties.getServer() + ":" + globalProperties.getPort()
				+ "/confirmReplication/{estimateCode}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("estimateCode", estimate.getEstimateCode());
		EstimateDetailsModel result = restTemplate.getForObject(uri, EstimateDetailsModel.class, params);
		logReplication(estimate);
	}

	public Estimate logReplication(Estimate estimate) {

		if (estimate != null) {
			EstimateReplicationLog estimateReplicationLog = new EstimateReplicationLog();
			estimateReplicationLog.setEstimate(estimate);
			estimateReplicationLogRepository.save(estimateReplicationLog);
		}

		return estimate;
	}

	private void replicationFailed(Estimate estimate) {
		final String uri = "http://" + globalProperties.getServer() + ":" + globalProperties.getPort()
				+ "/replicationFailed/{estimateCode}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("estimateCode", estimate.getEstimateCode());
		EstimateDetailsModel result = restTemplate.getForObject(uri, EstimateDetailsModel.class, params);
		System.out.println(result);
	}

}
