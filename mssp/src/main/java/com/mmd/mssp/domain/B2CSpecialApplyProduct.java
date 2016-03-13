package com.mmd.mssp.domain;

import java.math.BigDecimal;
import java.util.List;

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
import javax.persistence.Transient;

@Entity
@Table(name="b2c_special_apply_product")
public class B2CSpecialApplyProduct {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="conditions")
	private String conditions;
	
	/*@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="product_id")
	private Product product;*/
	
	@Basic
	@Column(name="content")
	private String content;
	
	@Basic
	@Column(name="apply_price")
	private BigDecimal applyPrice;
	
	@Basic
	@Column(name="number")
	private Integer number;
	
	@Basic
	@Column(name="is_growth", columnDefinition="INT")
	private Integer isGrowth;
	
	@Basic
	@Column(name="is_picking", columnDefinition="INT")
	private Integer isPicking;
	
	@Basic
	@Column(name="growth_rate")
	private String growthRate;
	
	@Basic
	@Column(name="apply_total_price")
	private BigDecimal applyTotalPrice;
	
	@Basic
	@Column(name="comp_product")
	private String compProduct;
	
	@Basic
	@Column(name="comp_price")
	private BigDecimal compPrice;
	
	@Basic
	@Column(name="remark")
	private String remark;
	
	@Basic
	@Column(name="reply_total_price")
	private BigDecimal replyTotalPrice;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2c_project_id")
	private B2CProject project;

	@Basic
	@Column(name="is_agree", columnDefinition="INT")
	private Integer isAgree;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_user_id")
	private CommUser commUser;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="reply_log_id")
	private B2CSpecialApplyProductReplyLog replyLog;
	
	@Transient
	private BigDecimal poPrice;//开票价
	
	@Transient
	private BigDecimal netPrice;//net价
	
	@Transient
	private BigDecimal financePrice;//财报价
	
	@Transient
	private BigDecimal syPrice;//损益价

	@Transient
	List<B2CSpecialApplyProductReplyLog> replyLogList;
	

	public CommUser getCommUser() {
		return commUser;
	}

	public void setCommUser(CommUser commUser) {
		this.commUser = commUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getApplyPrice() {
		return applyPrice;
	}

	public void setApplyPrice(BigDecimal applyPrice) {
		this.applyPrice = applyPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getIsGrowth() {
		return isGrowth;
	}

	public void setIsGrowth(Integer isGrowth) {
		this.isGrowth = isGrowth;
	}

	public Integer getIsPicking() {
		return isPicking;
	}

	public void setIsPicking(Integer isPicking) {
		this.isPicking = isPicking;
	}

	public String getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(String growthRate) {
		this.growthRate = growthRate;
	}

	public BigDecimal getApplyTotalPrice() {
		return applyTotalPrice;
	}

	public void setApplyTotalPrice(BigDecimal applyTotalPrice) {
		this.applyTotalPrice = applyTotalPrice;
	}

	public String getCompProduct() {
		return compProduct;
	}

	public void setCompProduct(String compProduct) {
		this.compProduct = compProduct;
	}

	public BigDecimal getCompPrice() {
		return compPrice;
	}

	public void setCompPrice(BigDecimal compPrice) {
		this.compPrice = compPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getReplyTotalPrice() {
		return replyTotalPrice;
	}

	public void setReplyTotalPrice(BigDecimal replyTotalPrice) {
		this.replyTotalPrice = replyTotalPrice;
	}

	public B2CProject getProject() {
		return project;
	}

	public void setProject(B2CProject project) {
		this.project = project;
	}

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public List<B2CSpecialApplyProductReplyLog> getReplyLogList() {
		return replyLogList;
	}

	public void setReplyLogList(List<B2CSpecialApplyProductReplyLog> replyLogList) {
		this.replyLogList = replyLogList;
	}

	public BigDecimal getPoPrice() {
		return poPrice;
	}

	public void setPoPrice(BigDecimal poPrice) {
		this.poPrice = poPrice;
	}

	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}

	public BigDecimal getFinancePrice() {
		return financePrice;
	}

	public void setFinancePrice(BigDecimal financePrice) {
		this.financePrice = financePrice;
	}

	public BigDecimal getSyPrice() {
		return syPrice;
	}

	public void setSyPrice(BigDecimal syPrice) {
		this.syPrice = syPrice;
	}

	public B2CSpecialApplyProductReplyLog getReplyLog() {
		return replyLog;
	}

	public void setReplyLog(B2CSpecialApplyProductReplyLog replyLog) {
		this.replyLog = replyLog;
	}
	
}
