package com.ceir.CeirCode.model.report;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Table;

@Entity
@Table(name = "user_login_daily_new_count")

public class UserLoginReport {

	private static long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createdOn;
	
	
	private Integer noUserLogged;  
	private Integer uniqueUserLogged;
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		UserLoginReport.serialVersionUID = serialVersionUID;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLoginReport [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", noUserLogged=");
		builder.append(noUserLogged);
		builder.append(", uniqueUserLogged=");
		builder.append(uniqueUserLogged);
		builder.append("]");
		return builder.toString();
	}
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
	public Integer getNoUserLogged() {
		return noUserLogged;
	}
	public void setNoUserLogged(Integer noUserLogged) {
		this.noUserLogged = noUserLogged;
	}
	public Integer getUniqueUserLogged() {
		return uniqueUserLogged;
	}
	public void setUniqueUserLogged(Integer uniqueUserLogged) {
		this.uniqueUserLogged = uniqueUserLogged;
	}

	
}
