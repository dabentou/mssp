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
@Table(name="b2i_sponsor_project")
public class B2ISponsorProject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="p_name")
	private String pName;
	
	@Basic
	@Column(name="p_code")
	private String pCode;
	
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="customer_id")
	private CommCustomer customer;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="agent_id")
	private CommAgent agent;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_si")
	private CommAgent commSi;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="apply_person")
	private CommUser applyPerson;
	
	@Basic
	@Column(name="activity_explain")
	private String explain;
	
	@Basic
	@Column(name="sign_in")
	private String signIn;
	
	@Basic
	@Column(name="contract")
	private String contract;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="approve_template_id")
	private ApproveTemplate temp;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="process_step_id")
	private ProcessStep step;
	
	@Basic
	@Column(name = "apply_date", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date applyDate;
	
	@Basic
	@Column(name="is_delete", columnDefinition="INT")
	private Integer isDelete;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="template_id")
	private B2iApplyTemplate applyTemplate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}


	public CommCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(CommCustomer customer) {
		this.customer = customer;
	}

	public CommAgent getAgent() {
		return agent;
	}

	public void setAgent(CommAgent agent) {
		this.agent = agent;
	}

	public CommAgent getCommSi() {
		return commSi;
	}

	public void setCommSi(CommAgent commSi) {
		this.commSi = commSi;
	}

	public ApproveTemplate getTemp() {
		return temp;
	}

	public void setTemp(ApproveTemplate temp) {
		this.temp = temp;
	}

	public ProcessStep getStep() {
		return step;
	}

	public void setStep(ProcessStep step) {
		this.step = step;
	}


	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public CommUser getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(CommUser applyPerson) {
		this.applyPerson = applyPerson;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public B2iApplyTemplate getApplyTemplate() {
		return applyTemplate;
	}

	public void setApplyTemplate(B2iApplyTemplate applyTemplate) {
		this.applyTemplate = applyTemplate;
	}

	public String getSignIn() {
		return signIn;
	}

	public void setSignIn(String signIn) {
		this.signIn = signIn;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

}
