package org.pstcl.estimate.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.pstcl.estimate.entity.Estimate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EstimateRepository extends CrudRepository<Estimate, String>{

	@Query("select estimateObj from Estimate estimateObj where estimateObj.dtUpdated >= :dtUpdated")
	List<Estimate> findAllWithdtUpdatedAfter(@Param("dtUpdated") LocalDateTime dtUpdated);
	
	
	@Query("SELECT e FROM Estimate e WHERE e.estimateCode NOT IN (:estimateList)")  
	List<Estimate> findNotInList(@Param("estimateList")List<String> estimateList);
	
	List<Estimate> findAll();
}
