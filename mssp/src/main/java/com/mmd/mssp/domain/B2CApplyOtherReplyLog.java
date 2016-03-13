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
@Table(name="b2c_apply_other_reply_log")
public class B2CApplyOtherReplyLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="condition_id")
	private B2CCondition condition;
	
	@Basic
	@Column(name="content")
	private String content;
	
	@Basic
	@Column(name="reply_advice")
	private String replyAdvice;
	
	@Basic
	@Column(name = "reply_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date replyTime;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="apply_id")
	private B2CApplyOther applyOther;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="project_id")
	private B2CProject project;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="user_id")
	private CommUser user;
	
	@Basic
	@Column(name="type", columnDefinition="INT")
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public B2CCondition getCondition() {
		return condition;
	}

	public void setCondition(B2CCondition condition) {
		this.condition = condition;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyAdvice() {
		return replyAdvice;
	}

	public void setReplyAdvice(String replyAdvice) {
		this.replyAdvice = replyAdvice;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public B2CApplyOther getApplyOther() {
		return applyOther;
	}

	public void setApplyOther(B2CApplyOther applyOther) {
		this.applyOther = applyOther;
	}

	public B2CProject getProject() {
		return project;
	}

	public void setProject(B2CProject project) {
		this.project = project;
	}

	public CommUser getUser() {
		return user;
	}

	public void setUser(CommUser user) {
		this.user = user;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
