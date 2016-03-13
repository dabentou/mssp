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
@Table(name = "sample_in_out_log")
public class SampleInOutLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinColumn(name="sample_inventory_id")
	private SampleInventory sampleInventory;

	@Basic
	@Column(name = "createtime",columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;
	
	@Basic
	@Column(name="change_status",columnDefinition="INT")
	private String status;
	
	@Basic
	@Column(name = "change_volue")
	private Integer changeVolue;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	@JoinColumn(name="b2b_project_id")
	private B2BProject b2bProject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SampleInventory getSampleInventory() {
		return sampleInventory;
	}

	public void setSampleInventory(SampleInventory sampleInventory) {
		this.sampleInventory = sampleInventory;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getChangeVolue() {
		return changeVolue;
	}

	public void setChangeVolue(Integer changeVolue) {
		this.changeVolue = changeVolue;
	}

	public B2BProject getB2bProject() {
		return b2bProject;
	}

	public void setB2bProject(B2BProject b2bProject) {
		this.b2bProject = b2bProject;
	}
	
	
}
