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
@Table(name="psi_sell_in_estimate_by_month")
public class PsiSellInEstimateByMonth {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Basic
	@Column(name="sell_in_volume")
	private Integer sellInVolume;
	
	@Basic
	@Column(name="sell_out_volume")
	private Integer sellOutVolume;
	
	@Basic
	@Column(name = "createtime", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;
	
	@Basic
	@Column(name = "date_month", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateMonth;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="agent_id")
	private CommAgent agent;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="info_id")
	private PsiSellInEstimateByMonthInfo info;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getSellInVolume() {
		return sellInVolume;
	}

	public void setSellInVolume(Integer sellInVolume) {
		this.sellInVolume = sellInVolume;
	}

	public Integer getSellOutVolume() {
		return sellOutVolume;
	}

	public void setSellOutVolume(Integer sellOutVolume) {
		this.sellOutVolume = sellOutVolume;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(Date dateMonth) {
		this.dateMonth = dateMonth;
	}

	public CommAgent getAgent() {
		return agent;
	}

	public void setAgent(CommAgent agent) {
		this.agent = agent;
	}

	public PsiSellInEstimateByMonthInfo getInfo() {
		return info;
	}

	public void setInfo(PsiSellInEstimateByMonthInfo info) {
		this.info = info;
	}
}