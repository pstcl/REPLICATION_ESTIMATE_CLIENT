package org.pstcl.estimate.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * The persistent class for the estimate_master database table.
 * All properties are mapped to columns by default.
 * @column annotation is needed only if we want to change the names,default length etc
 */
@Data
@Entity
@Table(name="estimate_master")
public class Estimate implements Comparable<Estimate>,Serializable {
	private static final long serialVersionUID = 1L;

	private String approved;

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ENTRY")
	//@DateTimeFormat(iso = ISO.DATE_TIME)
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dtEntry;

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_UPDATED")
	//@DateTimeFormat(iso = ISO.DATE_TIME)
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dtUpdated;

	private String empid;

	@Column(name="ENTRY_SOURCE")
	private String entrySource;

	@Column(name="ESTIMATE_AMOUNT")
	private BigDecimal estimateAmount;


	@Id
	@Column(name="estimate_code")
	private String estimateCode;


	@JsonIgnore
	@OneToMany(mappedBy="id.estimate", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<EstimateCostDetail> estimateCostDetails;

	@JsonIgnore
	@OneToMany(mappedBy="id.estimate", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<EstimateItemDetail> estimateItemDetails;

	@Column(name="ESTIMATE_NAME")
	private String estimateName;

	@Column(name="ESTIMATE_NO")
	private String estimateNo;

	@Column(name="ESTIMATE_SANCT")
	private String estimateSanct;

	@Column(name="ESTIMATE_SANCTION_DATE")
	private LocalDateTime estimateSanctionDate;



	@Column(name="ESTIMATE_SANCTION_DATE_OLD")
	private String estimateSanctionDateOld;


	@Column(name="ESTIMATE_TYPE")
	private String estimateType;

	@Column(name="EST_YEAR")
	private String estYear;

	@Column(name="JE_EMPID")
	private String jeEmpid;

	@Column(name="JE_NAME")
	private String jeName;

	@Column(name="LOCATION_CODE")
	private String locationCode;

	@Column(name="NATURE_EST")
	private String natureEst;

	@Column(name="OLD_EST_CODE")
	private String oldEstCode;

	private String period;

	@Column(name="SCH_CODE")
	private String schCode;

	private String userid;


	//bi-directional many-to-one association to WorkMaster
	@ManyToOne
	@JoinColumn(name="WORK_CODE", referencedColumnName="WORK_CODE")
	private Work workMaster;


	@JsonIgnore
	@Override
	public String toString() {
		return "Estimate [approved=" + approved + ", dtEntry=" + dtEntry + ", estimateCode=" + estimateCode
				+ ", dtUpdated=" + dtUpdated + ", empid=" + empid + ", entrySource=" + entrySource + ", estimateAmount="
				+ estimateAmount + ", estimateName=" + estimateName + ", estimateNo=" + estimateNo + ", estimateSanct="
				+ estimateSanct + ", estimateSanctionDate=" + estimateSanctionDate + ", estimateType=" + estimateType
				+ ", estYear=" + estYear + ", jeEmpid=" + jeEmpid + ", jeName=" + jeName + ", locationCode="
				+ locationCode + ", natureEst=" + natureEst + ", oldEstCode=" + oldEstCode + ", period=" + period
				+ ", schCode=" + schCode + ", userid=" + userid + ", workMaster=" + workMaster + "]";
	}


	@JsonIgnore
	@Override
	public int compareTo(Estimate o) {
		int result=0;
		if(null==this.dtUpdated)
		{
			result=-1;
		}
		else if(null==o.dtUpdated)
		{
			result=1;
		}
		else
		{
			result=this.dtUpdated.compareTo(o.dtUpdated);
		}
		return result;
	}





}