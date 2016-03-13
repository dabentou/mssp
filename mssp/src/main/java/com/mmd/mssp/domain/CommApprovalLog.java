package com.mmd.mssp.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="comm_approval_log")
public class CommApprovalLog {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="data_type")
	private Integer dataType;//0 sellout修改
	
	@Basic
	@Column(name="date_id")
	private Integer dataId;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="approval_user_id")
	private CommUser commUser;

	@Basic
	@Column(name="approval_status")
	private Integer approvalStatus;
	
	@Basic
	@Column(name="approval_msg",length=45)
	private Integer approvalMsg;
	
	@Basic
	@Column(name = "approval_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date approvalTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public CommUser getCommUser() {
		return commUser;
	}

	public void setCommUser(CommUser commUser) {
		this.commUser = commUser;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Integer getApprovalMsg() {
		return approvalMsg;
	}

	public void setApprovalMsg(Integer approvalMsg) {
		this.approvalMsg = approvalMsg;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	
	
}
