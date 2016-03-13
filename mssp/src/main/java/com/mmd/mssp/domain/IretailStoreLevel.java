package com.mmd.mssp.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="iretail_store_level")
public class IretailStoreLevel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private String name;
	
	@Basic
	@Column(name="decorate_level")
	private String decorateLevel;
	
	@Basic
	@Column(name="support_money", columnDefinition="INT")
	private Integer supportMoney;
	
	@Basic
	@Column(name="target_volume", columnDefinition="INT")
	private Integer targetVolume;
	
	@Basic
	@Column(name="integral_volume", columnDefinition="INT")
	private Integer integralVolume;
	
	@Basic
	@Column(name = "create_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;
	
	@Basic
	@Column(name="is_delete", columnDefinition="INT")
	private Integer isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecorateLevel() {
		return decorateLevel;
	}

	public void setDecorateLevel(String decorateLevel) {
		this.decorateLevel = decorateLevel;
	}

	public Integer getSupportMoney() {
		return supportMoney;
	}

	public void setSupportMoney(Integer supportMoney) {
		this.supportMoney = supportMoney;
	}

	public Integer getTargetVolume() {
		return targetVolume;
	}

	public void setTargetVolume(Integer targetVolume) {
		this.targetVolume = targetVolume;
	}

	public Integer getIntegralVolume() {
		return integralVolume;
	}

	public void setIntegralVolume(Integer integralVolume) {
		this.integralVolume = integralVolume;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}
