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
@Table(name="b2c_special_product_update_log")
public class B2CSpecialProductUpdateLog {
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
	@JoinColumn(name="b2c_special_apply_product_id")
	private B2CSpecialApplyProduct b2CSpecialApplyProduct;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_user_id")
	private CommUser user;
	
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

	public B2CSpecialApplyProduct getB2CSpecialApplyProduct() {
		return b2CSpecialApplyProduct;
	}

	public void setB2CSpecialApplyProduct(
			B2CSpecialApplyProduct b2cSpecialApplyProduct) {
		b2CSpecialApplyProduct = b2cSpecialApplyProduct;
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
