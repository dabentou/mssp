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
@Table(name="b2c_approve_log")
public class B2CApproveLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="process_step_id")
	private ProcessStep step;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2c_project_id")
	private B2CProject project;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="approve_user_id")
	private CommUser user;
	
	@Basic
	@Column(name = "approve_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date approveTime;
	
	@Basic
	@Column(name="approve_msg")
	private String approveMsg;
	
	@Basic
	@Column(name="is_pass")
	private Integer isPass;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProcessStep getStep() {
		return step;
	}

	public void setStep(ProcessStep step) {
		this.step = step;
	}

	public B2CProject getProject() {
		return project;
	}

	public void setProject(B2CProject project) {
		this.project = project;
	}

	public CommUser getUser() {
		return user;
	}

	public void setUser(CommUser user) {
		this.user = user;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveMsg() {
		return approveMsg;
	}

	public void setApproveMsg(String approveMsg) {
		this.approveMsg = approveMsg;
	}

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	
}
