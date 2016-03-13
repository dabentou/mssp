package com.mmd.mssp.domain;

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
@Table(name="desc_setting")
public class DescSetting {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="province_id")
	private CommProvince province;
	
	@Basic
	@Column(name="is_desc", columnDefinition="INT")
	private Integer is_desc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CommProvince getProvince() {
		return province;
	}

	public void setProvince(CommProvince province) {
		this.province = province;
	}

	public Integer getIs_desc() {
		return is_desc;
	}

	public void setIs_desc(Integer is_desc) {
		this.is_desc = is_desc;
	}

}
