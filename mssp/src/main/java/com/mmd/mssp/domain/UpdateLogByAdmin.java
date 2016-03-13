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
@Table(name="update_log_by_admin")
public class UpdateLogByAdmin {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="change_volume")
	private Integer changeVolume;//修改后的值
	
	@Basic
	@Column(name = "createtime", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="update_log_id")
	private PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private CommUser commUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChangeVolume() {
		return changeVolume;
	}

	public void setChangeVolume(Integer changeVolume) {
		this.changeVolume = changeVolume;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public PsiSellOutUpdateApplyLog getPsiSellOutUpdateApplyLog() {
		return psiSellOutUpdateApplyLog;
	}

	public void setPsiSellOutUpdateApplyLog(
			PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog) {
		this.psiSellOutUpdateApplyLog = psiSellOutUpdateApplyLog;
	}

	public CommUser getCommUser() {
		return commUser;
	}

	public void setCommUser(CommUser commUser) {
		this.commUser = commUser;
	}
	
}
