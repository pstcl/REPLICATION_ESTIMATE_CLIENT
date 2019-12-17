package org.pstcl.estimate.model;

import java.time.LocalDateTime;
import java.util.List;

import org.pstcl.estimate.entity.Estimate;

import lombok.Getter;
import lombok.Setter;
public class EstimateModel {
	
	
	
	@Getter
	@Setter
	private LocalDateTime previousAccessDateTime;

	@Getter
	@Setter
	private LocalDateTime accessTime;
	
	@Getter
	@Setter
	private List<Estimate> newEstimates;

}
