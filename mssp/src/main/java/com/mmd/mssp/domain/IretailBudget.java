package com.mmd.mssp.domain;

import java.math.BigDecimal;

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

@Entity
@Table(name="iretail_budget")
public class IretailBudget {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="years")
	private int year;
	
	@Basic
	@Column(name="months")
	private int months;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_province_id")
	private CommProvince commonProvince;
	
	@Basic
	@Column(name="R02")
	private BigDecimal r02;
	
	@Basic
	@Column(name="R03")
	private BigDecimal r03;
	
	@Basic
	@Column(name="R04")
	private BigDecimal r04;
	
	@Basic
	@Column(name="R05")
	private BigDecimal r05;

	@Basic
	@Column(name="R06")
	private BigDecimal r06;
	
	@Basic
	@Column(name="R07")
	private BigDecimal r07;
	
	@Basic
	@Column(name="R08")
	private BigDecimal r08;
	
	@Basic
	@Column(name="R09")
	private BigDecimal r09;
	
	@Basic
	@Column(name="R10")
	private BigDecimal r10;
	
	@Basic
	@Column(name="R11")
	private BigDecimal r11;
	
	@Basic
	@Column(name="R12")
	private BigDecimal r12;

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


	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public CommProvince getCommonProvince() {
		return commonProvince;
	}

	public void setCommonProvince(CommProvince commonProvince) {
		this.commonProvince = commonProvince;
	}

	public BigDecimal getR02() {
		return r02;
	}

	public void setR02(BigDecimal r02) {
		this.r02 = r02;
	}

	public BigDecimal getR03() {
		return r03;
	}

	public void setR03(BigDecimal r03) {
		this.r03 = r03;
	}

	public BigDecimal getR04() {
		return r04;
	}

	public void setR04(BigDecimal r04) {
		this.r04 = r04;
	}

	public BigDecimal getR05() {
		return r05;
	}

	public void setR05(BigDecimal r05) {
		this.r05 = r05;
	}

	public BigDecimal getR06() {
		return r06;
	}

	public void setR06(BigDecimal r06) {
		this.r06 = r06;
	}

	public BigDecimal getR07() {
		return r07;
	}

	public void setR07(BigDecimal r07) {
		this.r07 = r07;
	}

	public BigDecimal getR08() {
		return r08;
	}

	public void setR08(BigDecimal r08) {
		this.r08 = r08;
	}

	public BigDecimal getR09() {
		return r09;
	}

	public void setR09(BigDecimal r09) {
		this.r09 = r09;
	}

	public BigDecimal getR10() {
		return r10;
	}

	public void setR10(BigDecimal r10) {
		this.r10 = r10;
	}

	public BigDecimal getR11() {
		return r11;
	}

	public void setR11(BigDecimal r11) {
		this.r11 = r11;
	}

	public BigDecimal getR12() {
		return r12;
	}

	public void setR12(BigDecimal r12) {
		this.r12 = r12;
	}
}
