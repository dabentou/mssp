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
@Table(name="b2c_project")
public class B2CProject {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="p_code")
	private String pCode;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="agent_id")
	private CommAgent agent;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private CommUser user;
	
	@Basic
	@Column(name="theme")
	private String theme;
	
	@Basic
	@Column(name="apply_reason")
	private String applyReason;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="approve_template_id")
	private ApproveTemplate approveTemplate;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="process_step_id")
	private ProcessStep processStep;
	
	@Basic
	@Column(name = "apply_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date applytime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public CommAgent getAgent() {
		return agent;
	}

	public void setAgent(CommAgent agent) {
		this.agent = agent;
	}

	public CommUser getUser() {
		return user;
	}

	public void setUser(CommUser user) {
		this.user = user;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public ApproveTemplate getApproveTemplate() {
		return approveTemplate;
	}

	public void setApproveTemplate(ApproveTemplate approveTemplate) {
		this.approveTemplate = approveTemplate;
	}

	public ProcessStep getProcessStep() {
		return processStep;
	}

	public void setProcessStep(ProcessStep processStep) {
		this.processStep = processStep;
	}

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}
}
