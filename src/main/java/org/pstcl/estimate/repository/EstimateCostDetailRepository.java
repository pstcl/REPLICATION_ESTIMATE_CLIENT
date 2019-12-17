package org.pstcl.estimate.repository;

import java.util.List;

import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.entity.EstimateCostCompositeKey;
import org.pstcl.estimate.entity.EstimateCostDetail;
import org.pstcl.estimate.entity.EstimateItemDetail;
import org.springframework.data.repository.CrudRepository;

public interface EstimateCostDetailRepository extends CrudRepository<EstimateCostDetail, EstimateCostCompositeKey>{

	public List<EstimateCostDetail> findById_estimate_estimateCode(String estimate);

}
