package com.mmd.mssp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Auto-generated by:
 * org.apache.openjpa.jdbc.meta.ReverseMappingTool$AnnotatedCodeGenerator
 */
@Entity
@Table(name="comm_role_permission")
public class CommRolePermission {
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="permission_id")
	private CommPermission commPermission;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="role_id")
	private CommRole commRole;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	public CommRolePermission() {
	}

	public CommRolePermission(CommPermission commPermission, CommRole commRole) {
		this.commPermission = commPermission;
		this.commRole = commRole;
	}

	public CommRolePermission(long id) {
		this.id = id;
	}

	public CommPermission getCommPermission() {
		return commPermission;
	}

	public void setCommPermission(CommPermission commPermission) {
		this.commPermission = commPermission;
	}

	public CommRole getCommRole() {
		return commRole;
	}

	public void setCommRole(CommRole commRole) {
		this.commRole = commRole;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}