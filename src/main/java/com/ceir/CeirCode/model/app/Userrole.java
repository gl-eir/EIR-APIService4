package com.ceir.CeirCode.model.app;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Table;
@Entity
//@Audited
 @Table(name = "user_role")

public class Userrole {
	private static long serialVersionUID = 1L;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;

	@ManyToOne 
	@JoinColumn(name = "user_id",nullable = false) 
	private User userData;
	   
	@ManyToOne 
	@JoinColumn(name = "user_type_id",nullable = false) 
	private Usertype usertypeData; 
   
	
	
	public Userrole(User userData, Usertype usertypeData) {
		super();
		this.userData = userData;
		this.usertypeData = usertypeData;
	}
	
	

	public Userrole() {
		super();
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUserData() {
		return userData;
	}

	public void setUserData(User userData) {
		this.userData = userData;
	}

	public Usertype getUsertypeData() {
		return usertypeData;
	}

	public void setUsertypeData(Usertype usertypeData) {
		this.usertypeData = usertypeData;
	}

	@Override
	public String toString() {
		return "Userrole [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + "]";
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

	}
