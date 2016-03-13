package com.mmd.mssp.domain;

import java.math.BigDecimal;
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
@Table(name="b2i_meeting_project")
public class B2IMeetingProject {

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
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="business_id")
	private CommBusiness business;
	
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
	@Column(name="support_money")
	private BigDecimal supportMoney;
	
	@Basic
	@Column(name="door")
	private String door;
	
	@Basic
	@Column(name="persons")
	private String persons;
	
	@Basic
	@Column(name="machine_money")
	private String machineMoney;
	
	@Basic
	@Column(name="bg_img")
	private String bgImg;
	
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

	public B2iApplyTemplate getApplyTemplate() {
		return applyTemplate;
	}

	public void setApplyTemplate(B2iApplyTemplate applyTemplate) {
		this.applyTemplate = applyTemplate;
	}

	public CommBusiness getBusiness() {
		return business;
	}

	public void setBusiness(CommBusiness business) {
		this.business = business;
	}

	public BigDecimal getSupportMoney() {
		return supportMoney;
	}

	public void setSupportMoney(BigDecimal supportMoney) {
		this.supportMoney = supportMoney;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public String getMachineMoney() {
		return machineMoney;
	}

	public void setMachineMoney(String machineMoney) {
		this.machineMoney = machineMoney;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}
	
}
