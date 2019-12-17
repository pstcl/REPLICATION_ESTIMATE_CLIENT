package org.pstcl.estimate.util.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.pstcl.estimate.entity.Estimate;

import lombok.Data;

@Data
@Entity
@Table(name="estimate_replication_log")
public class EstimateReplicationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="ESTIMATE_CODE", referencedColumnName="estimate_code")
	private Estimate estimate;


	private String userName;


	private String clientName;

	private String clientIp;



	@Column
	@UpdateTimestamp
	private LocalDateTime updateDateTime;


	@Column
	@CreationTimestamp
	private LocalDateTime lastAccessTime;


}
