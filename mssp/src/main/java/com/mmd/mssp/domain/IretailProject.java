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
@Table(name="iretail_project")
public class IretailProject {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="ppn")
	private String ppn;
	
	@Basic
	@Column(name="cpn")
	private String cpn;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="store_id")
	private CommAgent store;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="province_id")
	private CommProvince province;
	
	@Basic
	@Column(name = "apply_date", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date applyDate;
	
	@Basic
	@Column(name="apply_theme")
	private String applyTheme;
	
	@Basic
	@Column(name="apply_price")
	private BigDecimal applyPrice;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@Column(name="apply_user_id")
	private CommAgent applyUser;
	
	@Basic
	@Column(name = "valid_begin_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date beginTime;
	
	@Basic
	@Column(name = "valid_end_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date endTime;
	
	@Basic
	@Column(name="provider")
	private String provider;
	
	@Basic
	@Column(name="approve_email")
	private String approveEmail;
	
	@Basic
	@Column(name="states")
	private int states;
	
	@Basic
	@Column(name="i_type")
	private String iType;
	
	@Basic
	@Column(name="apply_content")
	private String applyContent;
	
	@Basic
	@Column(name="remark")
	private String remark;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="approve_template_id")
	private ApproveTemplate approveTemplate;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="process_step_id")
	private ProcessStep processStep;
	
	@Basic
	@Column(name="image")
	private String image;
	
	@Basic
	@Column(name="is_delete", columnDefinition="INT")
	private Integer isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPpn() {
		return ppn;
	}

	public void setPpn(String ppn) {
		this.ppn = ppn;
	}

	public CommAgent getStore() {
		return store;
	}

	public void setStore(CommAgent store) {
		this.store = store;
	}


	public CommProvince getProvince() {
		return province;
	}

	public void setProvince(CommProvince province) {
		this.province = province;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyTheme() {
		return applyTheme;
	}

	public void setApplyTheme(String applyTheme) {
		this.applyTheme = applyTheme;
	}

	public BigDecimal getApplyPrice() {
		return applyPrice;
	}

	public void setApplyPrice(BigDecimal applyPrice) {
		this.applyPrice = applyPrice;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getApproveEmail() {
		return approveEmail;
	}

	public void setApproveEmail(String approveEmail) {
		this.approveEmail = approveEmail;
	}

	public int getStates() {
		return states;
	}

	public void setStates(int states) {
		this.states = states;
	}

	public String getiType() {
		return iType;
	}

	public void setiType(String iType) {
		this.iType = iType;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCpn() {
		return cpn;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CommAgent getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(CommAgent applyUser) {
		this.applyUser = applyUser;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
