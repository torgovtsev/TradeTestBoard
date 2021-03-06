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
 * UserGroupEntity generated by hbm2java
 */
@Entity
@Table(name = "USER_GROUP", schema = "PUBLIC", catalog = "PUBLIC", uniqueConstraints = @UniqueConstraint(columnNames = {
		"USER_ID", "GROUP_ID" }))
public class UserGroupEntity implements java.io.Serializable {

	private UserGroupEntityId id;
	private UserEntity userEntity;
	private GroupEntity groupEntity;

	public UserGroupEntity() {
	}

	public UserGroupEntity(UserGroupEntityId id, UserEntity userEntity,
			GroupEntity groupEntity) {
		this.id = id;
		this.userEntity = userEntity;
		this.groupEntity = groupEntity;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false)),
			@AttributeOverride(name = "groupId", column = @Column(name = "GROUP_ID", nullable = false)) })
	public UserGroupEntityId getId() {
		return this.id;
	}

	public void setId(UserGroupEntityId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
	public UserEntity getUserEntity() {
		return this.userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID", nullable = false, insertable = false, updatable = false)
	public GroupEntity getGroupEntity() {
		return this.groupEntity;
	}

	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}

}
