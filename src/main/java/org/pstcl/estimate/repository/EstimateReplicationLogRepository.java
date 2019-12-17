package org.pstcl.estimate.repository;

import java.util.List;

import org.jboss.logging.Param;
import org.pstcl.estimate.util.entity.EstimateReplicationLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EstimateReplicationLogRepository extends CrudRepository<EstimateReplicationLog, Integer>{

	
	public EstimateReplicationLog findFirstByUserNameOrderByLastAccessTimeDesc( String userName);

	@Query("select e.estimate.estimateCode from EstimateReplicationLog e where e.clientIp=?1 and e.userName = ?2")
	public List<String> findEstimatesByClientIpAndUserName(String clientIp, String userName);


	public EstimateReplicationLog findByEstimate(String estimate);
	

	


}
