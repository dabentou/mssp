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
@Table(name="b2i_apply_product_update_log")
public class B2IApplyProductUpdateLog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="old_price")
	private BigDecimal oldPrice;
	
	@Basic
	@Column(name="old_number")
	private Integer oldNumber;
	
	@Basic
	@Column(name="target_price")
	private BigDecimal targetPrice;
	
	@Basic
	@Column(name="target_number")
	private Integer targetNumber;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_user_id")
	private CommUser user;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2i_apply_product_id")
	private B2IApplyProduct applyProduct;
	
	@Basic
	@Column(name="update_reason")
	private String updateReason;
	
	@Basic
	@Column(name = "update_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date updatetime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Integer getOldNumber() {
		return oldNumber;
	}

	public void setOldNumber(Integer oldNumber) {
		this.oldNumber = oldNumber;
	}

	public BigDecimal getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	public Integer getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(Integer targetNumber) {
		this.targetNumber = targetNumber;
	}

	public CommUser getUser() {
		return user;
	}

	public void setUser(CommUser user) {
		this.user = user;
	}

	public B2IApplyProduct getApplyProduct() {
		return applyProduct;
	}

	public void setApplyProduct(B2IApplyProduct applyProduct) {
		this.applyProduct = applyProduct;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
