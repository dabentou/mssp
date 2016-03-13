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
@Table(name="b2c_special_apply_product_reply_log")
public class B2CSpecialApplyProductReplyLog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="conditions")
	private String conditions;
	
	@Basic
	@Column(name="content")
	private String content;
	
	@Basic
	@Column(name="reply_advice")
	private String replyAdvice;
	
	@Basic
	@Column(name="reply_price")
	private BigDecimal replyPrice;
	
	@Basic
	@Column(name="apply_total_price")
	private BigDecimal applyTotalPrice;
	
	@Basic
	@Column(name="number")
	private Integer number;
	
	@Basic
	@Column(name = "reply_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date replyTime;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="apply_id")
	private B2CSpecialApplyProduct applyProduct;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="project_id")
	private B2CProject project;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private CommUser user;

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

	public String getReplyAdvice() {
		return replyAdvice;
	}

	public void setReplyAdvice(String replyAdvice) {
		this.replyAdvice = replyAdvice;
	}

	public BigDecimal getReplyPrice() {
		return replyPrice;
	}

	public void setReplyPrice(BigDecimal replyPrice) {
		this.replyPrice = replyPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public B2CSpecialApplyProduct getApplyProduct() {
		return applyProduct;
	}

	public void setApplyProduct(B2CSpecialApplyProduct applyProduct) {
		this.applyProduct = applyProduct;
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

	public BigDecimal getApplyTotalPrice() {
		return applyTotalPrice;
	}

	public void setApplyTotalPrice(BigDecimal applyTotalPrice) {
		this.applyTotalPrice = applyTotalPrice;
	}
	
	
}
