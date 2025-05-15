package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "user_login_status")
public class CurrentLogin {

	private static long serialVersionUID = 1L;
	@Id       
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable =false)
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;

	
	private Integer loginStatus;

	@ManyToOne
	@JoinColumn(name="user_id",nullable = false)
	private User currentUserLogin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public User getCurrentUserLogin() {
		return currentUserLogin;
	}

	public void setCurrentUserLogin(User currentUserLogin) {
		this.currentUserLogin = currentUserLogin;
	}

	public CurrentLogin(Integer loginStatus, User currentUserLogin) {
		super();
		this.loginStatus = loginStatus;
		this.currentUserLogin = currentUserLogin;
	}

	
	public CurrentLogin() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CurrentLogin [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", loginStatus=");
		builder.append(loginStatus);
		builder.append("]");
		return builder.toString();
	}

}
