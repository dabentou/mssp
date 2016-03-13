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
@Table(name="b2i_project")
public class B2IProject {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="p_name")
	private String pName;
	
	@Basic
	@Column(name="p_code")
	private String pCode;
	
	@Basic
	@Column(name = "purchase_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date purchaseTime;
	
/*	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="business_id")
	private CommBusiness business;*/
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="customer_id")
	private CommCustomer customer;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="agent_id")
	private CommAgent agent;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_si")
	private CommAgent commSi;
	
	@Basic
	@Column(name="project_context")
	private String projectContext;

	@Basic
	@Column(name="project_important")
	private String projectImportant;
	
	@Basic
	@Column(name="compete_condition")
	private String competeCondition;
	
	@Basic
	@Column(name="apply_reason")
	private String applyReason;
	
	@Basic
	@Column(name="specific_support_info")
	private String specificSupportInfo;
	
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
	@Column(name = "apply_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date applytime;
	
	@Basic
	@Column(name="photo_url")
	private String photoUrl;
	
	@Basic
	@Column(name="is_delete", columnDefinition="INT")
	private Integer isDelete;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="template_id")
	private B2iApplyTemplate applyTemplate;
	
	@Basic
	@Column(name="image")
	private String image;
	
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

	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
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

	public String getProjectContext() {
		return projectContext;
	}

	public void setProjectContext(String projectContext) {
		this.projectContext = projectContext;
	}

	public String getProjectImportant() {
		return projectImportant;
	}

	public void setProjectImportant(String projectImportant) {
		this.projectImportant = projectImportant;
	}

	public String getCompeteCondition() {
		return competeCondition;
	}

	public void setCompeteCondition(String competeCondition) {
		this.competeCondition = competeCondition;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getSpecificSupportInfo() {
		return specificSupportInfo;
	}

	public void setSpecificSupportInfo(String specificSupportInfo) {
		this.specificSupportInfo = specificSupportInfo;
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

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public B2iApplyTemplate getApplyTemplate() {
		return applyTemplate;
	}

	public void setApplyTemplate(B2iApplyTemplate applyTemplate) {
		this.applyTemplate = applyTemplate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
