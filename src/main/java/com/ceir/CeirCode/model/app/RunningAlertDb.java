package com.ceir.CeirCode.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Table;

@Entity
  @Table(name="sys_generated_alert")

public class RunningAlertDb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable =false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime createdOn;

	@Column(nullable =false)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime modifiedOn;

	private Integer userId;
	
	@Column(length = 20)
	private String alertId;
	private String description;
	private Integer status;
	@Transient
	private String statusInterp;
	private String username;
	
	public RunningAlertDb() {
		super();
	}
	
	
	public RunningAlertDb(String alertId, String description, Integer status) {
		super();

		this.alertId = alertId;
		this.description = description;
		this.status = status;
		
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAlertId() {
		return alertId;
	}
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getStatusInterp() {
		return statusInterp;
	}


	public void setStatusInterp(String statusInterp) {
		this.statusInterp = statusInterp;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RunningAlertDb [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", alertId=");
		builder.append(alertId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append(", statusInterp=");
		builder.append(statusInterp);
		builder.append(", username=");
		builder.append(username);
		builder.append("]");
		return builder.toString();
	}
	
	
}
