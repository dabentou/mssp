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
@Table(name="psi_estimate_by_product")
public class PsiEstimateByProduct {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Basic
	@Column(name="next_month_estimate_volume")
	private Integer nextMonthEstimateVolume;
	
	@Basic
	@Column(name="nnext_month_estimate_volume")
	private Integer nnextMonthEstimateVolume;
	
	@Basic
	@Column(name = "createtime", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="estimate_template_id")
	private PsiSellInEstimateTemplate temp;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="agent_id")
	private CommAgent agent;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="manager_id")
	private CommUser user;
	
	@Basic
	@Column(name = "date_month", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateMonth;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNextMonthEstimateVolume() {
		return nextMonthEstimateVolume;
	}

	public void setNextMonthEstimateVolume(Integer nextMonthEstimateVolume) {
		this.nextMonthEstimateVolume = nextMonthEstimateVolume;
	}

	public Integer getNnextMonthEstimateVolume() {
		return nnextMonthEstimateVolume;
	}

	public void setNnextMonthEstimateVolume(Integer nnextMonthEstimateVolume) {
		this.nnextMonthEstimateVolume = nnextMonthEstimateVolume;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public PsiSellInEstimateTemplate getTemp() {
		return temp;
	}

	public void setTemp(PsiSellInEstimateTemplate temp) {
		this.temp = temp;
	}

	public CommAgent getAgent() {
		return agent;
	}

	public void setAgent(CommAgent agent) {
		this.agent = agent;
	}

	public CommUser getUser() {
		return user;
	}

	public void setUser(CommUser user) {
		this.user = user;
	}

	public Date getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(Date dateMonth) {
		this.dateMonth = dateMonth;
	}
}