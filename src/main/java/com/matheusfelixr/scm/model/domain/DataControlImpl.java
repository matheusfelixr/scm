package com.matheusfelixr.scm.model.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DataControlImpl implements Serializable {

	private static final long serialVersionUID = -1107231151490523860L;

	@Column(name = "CREATE_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_USER", referencedColumnName = "ID", nullable = false)
	private UserAuthentication createUser;

	@Column(name = "UPDATE_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATE_USER", referencedColumnName = "ID", nullable = false)
	private UserAuthentication updateUser;

	public void markModified(UserAuthentication updateUser) {
		this.setUpdateDate(new Date());
		this.setUpdateUser(updateUser);
	}

	public void markCreate(UserAuthentication createUser) {
		this.setCreateDate(new Date());
		this.setCreateUser(createUser);
		this.markModified(createUser);
	}
}