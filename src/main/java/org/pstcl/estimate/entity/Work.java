package org.pstcl.estimate.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * The persistent class for the work_master database table.
 * 
 */
@Data
@Entity
@Table(name="work_master")
public class Work implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="WORK_CODE")
	private Integer workCode;

	@Column(name="AMENDMENT_DATE")
	private String amendmentDate;

	@Column(name="AMENDMENT_NO")
	private String amendmentNo;

	private BigDecimal amount;

	@Column(name="DPR_CODE")
	private String dprCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ENTRY")
	private Date dtEntry;

	private String empid;

	//bi-directional many-to-one association to EstimateMaster
	@JsonIgnore
	@OneToMany(mappedBy="workMaster", fetch=FetchType.EAGER)
	private List<Estimate> estimateMasters;

	@Column(name="LOCATION_CODE")
	private String locationCode;

	private BigDecimal rate;

	private String userid;

	@Column(name="VOLTAGE_LEVEL")
	private String voltageLevel;

	@Column(name="WORK_DESC")
	private String workDesc;

	@Column(name="WORK_NAME")
	private String workName;

	@Column(name="WORK_SRNO")
	private int workSrno;

}