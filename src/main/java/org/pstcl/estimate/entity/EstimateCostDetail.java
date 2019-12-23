package org.pstcl.estimate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the estimate_cost_details database table.
 * 
 */
@Data
@Entity
@Table(name="estimate_cost_details")
public class EstimateCostDetail  implements Serializable {
	private static final long serialVersionUID = 1L;

	public EstimateCostDetail()
	{
		super();
		this.id=new EstimateCostCompositeKey();
	}

	@EmbeddedId
	@lombok.experimental.Delegate
	private EstimateCostCompositeKey id;
	
	
	
	
	@Column(name="cost_desc")
	private String costDesc;



	@Column(name="total_amt")
	private String totalAmt;

	

}