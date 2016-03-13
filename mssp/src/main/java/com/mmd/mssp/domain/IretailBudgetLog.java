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
@Table(name="iretail_budget_log")
public class IretailBudgetLog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="years")
	private int year;
	
	@Basic
	@Column(name="quarters")
	private int quarter;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_province_id")
	private CommProvince province;
	
	@Basic
	@Column(name="type")
	private String type;
	
	@Basic
	@Column(name="old_volum")
	private BigDecimal oldVolum;
	
	@Basic
	@Column(name="new_volum")
	private BigDecimal newVolum;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private CommUser user;
	
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public CommProvince getProvince() {
		return province;
	}

	public void setProvince(CommProvince province) {
		this.province = province;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getOldVolum() {
		return oldVolum;
	}

	public void setOldVolum(BigDecimal oldVolum) {
		this.oldVolum = oldVolum;
	}

	public BigDecimal getNewVolum() {
		return newVolum;
	}

	public void setNewVolum(BigDecimal newVolum) {
		this.newVolum = newVolum;
	}

	public CommUser getUser() {
		return user;
	}

	public void setUser(CommUser user) {
		this.user = user;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
