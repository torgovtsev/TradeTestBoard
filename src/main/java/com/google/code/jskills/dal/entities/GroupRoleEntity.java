package com.google.code.jskills.dal.entities;

// Generated 25.06.2013 17:52:09 by Hibernate Tools 3.6.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * GroupRoleEntity generated by hbm2java
 */
@Entity
@Table(name = "GROUP_ROLE", schema = "PUBLIC", catalog = "PUBLIC", uniqueConstraints = @UniqueConstraint(columnNames = {
		"GROUP_ID", "ROLE_ID" }))
public class GroupRoleEntity implements java.io.Serializable {

	private GroupRoleEntityId id;
	private GroupEntity groupEntity;
	private RoleEntity roleEntity;

	public GroupRoleEntity() {
	}

	public GroupRoleEntity(GroupRoleEntityId id, GroupEntity groupEntity,
			RoleEntity roleEntity) {
		this.id = id;
		this.groupEntity = groupEntity;
		this.roleEntity = roleEntity;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "groupId", column = @Column(name = "GROUP_ID", nullable = false)),
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false)) })
	public GroupRoleEntityId getId() {
		return this.id;
	}

	public void setId(GroupRoleEntityId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID", nullable = false, insertable = false, updatable = false)
	public GroupEntity getGroupEntity() {
		return this.groupEntity;
	}

	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
	public RoleEntity getRoleEntity() {
		return this.roleEntity;
	}

	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

}
