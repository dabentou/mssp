package com.mmd.mssp.domain;

import java.util.List;

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
@Table(name="b2c_apply_other")
public class B2CApplyOther {

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
	@Column(name="type")
	private Integer type;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2c_project_id")
	private B2CProject b2CProject;
	
	@Basic
	@Column(name="is_agree", columnDefinition="INT")
	private Integer isAgree;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="comm_user_id")
	private CommUser commUser;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="reply_log_id")
	private B2CApplyOtherReplyLog replyLog;
	
	@Basic
	@Column(name="approve_sug")
	private String approveSug;
	
	@Basic
	@Column(name="approve_rem")
	private String approveRem;
	
	List<B2CApplyOtherReplyLog> replyLogList;

	public CommUser getCommUser() {
		return commUser;
	}

	public void setCommUser(CommUser commUser) {
		this.commUser = commUser;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public B2CProject getB2CProject() {
		return b2CProject;
	}

	public void setB2CProject(B2CProject b2cProject) {
		b2CProject = b2cProject;
	}

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public List<B2CApplyOtherReplyLog> getReplyLogList() {
		return replyLogList;
	}

	public void setReplyLogList(List<B2CApplyOtherReplyLog> replyLogList) {
		this.replyLogList = replyLogList;
	}

	public B2CApplyOtherReplyLog getReplyLog() {
		return replyLog;
	}

	public void setReplyLog(B2CApplyOtherReplyLog replyLog) {
		this.replyLog = replyLog;
	}

	public String getApproveSug() {
		return approveSug;
	}

	public void setApproveSug(String approveSug) {
		this.approveSug = approveSug;
	}

	public String getApproveRem() {
		return approveRem;
	}

	public void setApproveRem(String approveRem) {
		this.approveRem = approveRem;
	}
	
}
