package org.pstcl.estimate.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.pstcl.estimate.entity.Estimate;
import org.pstcl.estimate.entity.EstimateCostDetail;
import org.pstcl.estimate.entity.EstimateItemDetail;

import lombok.Getter;
import lombok.Setter;
public class EstimateDetailsModel {
	
	
	
	@Getter
	@Setter
	private LocalDateTime previousAccessDateTime;

	@Getter
	@Setter
	private LocalDateTime accessTime;
	
	@Getter
	@Setter
	private Estimate estimate;

	@Getter
	@Setter
	private Set<EstimateItemDetail> estimateItemDetails;

	@Getter
	@Setter
	private Set<EstimateCostDetail> estimateCostDetails;
	
	
}
