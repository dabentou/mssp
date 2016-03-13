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
@Table(name="apply_close_project")
public class ApplyCloseProject {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2b_project_id")
	private B2BProject b2bProject;
	
	@Basic
	@Column(name="project_info")
	private String proInfo ;
	
	@Basic
	@Column(name="upload_project_info")
	private String upInfo;
	
	@Basic
	@Column(name="remark")
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public B2BProject getB2bProject() {
		return b2bProject;
	}

	public void setB2bProject(B2BProject b2bProject) {
		this.b2bProject = b2bProject;
	}

	public String getProInfo() {
		return proInfo;
	}

	public void setProInfo(String proInfo) {
		this.proInfo = proInfo;
	}

	public String getUpInfo() {
		return upInfo;
	}

	public void setUpInfo(String upInfo) {
		this.upInfo = upInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
