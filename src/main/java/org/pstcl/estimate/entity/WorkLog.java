package org.pstcl.estimate.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


/**
 * The persistent class for the work_master_log database table.
 * 
 */
@Data
@Entity
@Table(name="work_master_log")
public class WorkLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="AMENDMENT_NO")
	private String amendmentNo;

	private BigDecimal amount;

	@Column(name="DPR_CODE")
	private int dprCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ENTRY")
	private Date dtEntry;

	private int empid;

	@Column(name="LOG_EVENT")
	private String logEvent;

	@Id
	private String logid;

	private BigDecimal rate;

	private String userid;

	@Column(name="VOLTAGE_LEVEL")
	private String voltageLevel;

	@Column(name="WORK_CODE")
	private String workCode;

	@Column(name="WORK_DESC")
	private String workDesc;

	@Column(name="WORK_NAME")
	private String workName;

	@Column(name="WORK_SRNO")
	private String workSrno;

}