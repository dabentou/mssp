package com.mmd.mssp.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="process_step")
public class ProcessStep implements Comparable<ProcessStep>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "type_id", columnDefinition = "INT")
	private ApproveTemplate temp;
	
	@Basic
	@Column(name="operate_status")
	private String operateStatus;
	
	@Basic
	@Column(name="status_value")
	private int statusValue;
	
	@Basic
	@Column(name="is_first")
	private int isFirst;
	
	@Basic
	@Column(name="email_user_ids")
	private String emailUserIds;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "pass_next", columnDefinition = "INT")
	private ProcessStep pnextId;//审核通过下个环节ID
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "reject_next", columnDefinition = "INT")
	private ProcessStep rnextId;//审核不通过下个环节ID
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name="comm_role_id", columnDefinition = "INT")
	private CommRole commRole;
	
	@Basic
	@Column(name="is_auto_approve")
	private int isAutoApprove;

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public ApproveTemplate getTemp() {
		return temp;
	}


	public void setTemp(ApproveTemplate temp) {
		this.temp = temp;
	}


	public String getOperateStatus() {
		return operateStatus;
	}


	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}


	public int getStatusValue() {
		return statusValue;
	}


	public void setStatusValue(int statusValue) {
		this.statusValue = statusValue;
	}


	public ProcessStep getPnextId() {
		return pnextId;
	}


	public void setPnextId(ProcessStep pnextId) {
		this.pnextId = pnextId;
	}


	public ProcessStep getRnextId() {
		return rnextId;
	}


	public void setRnextId(ProcessStep rnextId) {
		this.rnextId = rnextId;
	}


	public CommRole getCommRole() {
		return commRole;
	}


	public void setCommRole(CommRole commRole) {
		this.commRole = commRole;
	}


	public int getIsFirst() {
		return isFirst;
	}


	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}


	public String getEmailUserIds() {
		return emailUserIds;
	}


	public void setEmailUserIds(String emailUserIds) {
		this.emailUserIds = emailUserIds;
	}


	@Override
	public int compareTo(ProcessStep o) {
		return  this.getId() - o.getId();
	}


	public int getIsAutoApprove() {
		return isAutoApprove;
	}


	public void setIsAutoApprove(int isAutoApprove) {
		this.isAutoApprove = isAutoApprove;
	}
}
