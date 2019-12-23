package org.pstcl.estimate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the estimate_item_details database table.
 * 
 */
@Data
@Entity
@Table(name="estimate_item_details")
public class EstimateItemDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	public EstimateItemDetail()
	{
		super();
		this.id=new EstimateItemCompositeKey();
	}
	
	 
	@EmbeddedId
	@lombok.experimental.Delegate
	private EstimateItemCompositeKey id;
	
	



	
	@Column(name="cost_type")
	private String costType;


	@Column(name="goods_cond")
	private String goodsCond;

	@Column(name="item_code")
	private String itemCode;

	@Column(name="item_name")
	private String itemName;


	private String qty;

	@Column(name="total_amt")
	private String totalAmt;

	private String unit;

	@Column(name="unit_price")
	private String unitPrice;

}