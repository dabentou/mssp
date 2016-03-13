package com.mmd.mssp.domain;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="approve_template")
public class ApproveTemplate {
	
	public  static int TMEP_B2B_APPLY=1;//b2b项目申请
	
	public static int TMEP_B2B_CHECK_SALE=2;//b2b项目核销
	
	public static int TMEP_B2B_CLOSE_PROJECT=3;//b2b项目结案
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="type")
	private String type;
	
	@Basic
	@Column(name="sys_id")
	private String sysId;
	
	@Basic
	@Column(name="temp_name")
	private String tempName;
	
	@Basic
	@Column(name="description")
	private String desc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if(id==null){
			this.id = 0;
		}else{
			this.id = id;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	
}
