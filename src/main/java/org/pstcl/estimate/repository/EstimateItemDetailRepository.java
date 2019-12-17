package org.pstcl.estimate.repository;

import java.util.List;

import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.entity.EstimateItemCompositeKey;
import org.pstcl.estimate.entity.EstimateItemDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EstimateItemDetailRepository extends CrudRepository<EstimateItemDetail, EstimateItemCompositeKey>{
	
	public List<EstimateItemDetail> findById_estimate_estimateCode(String estimate);
}
