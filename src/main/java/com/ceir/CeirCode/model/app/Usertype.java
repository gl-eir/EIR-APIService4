package com.ceir.CeirCode.model.app;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Table;
@Entity 
//@Audited
 @Table(name = "user_type")
public class Usertype {

	@Id 
	private long id;
        @Column(name="user_type_name")
	private String usertypeName; 
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;

	@NotAudited
	@JsonIgnore 
	@OneToMany(mappedBy = "usertype", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<User> user;
	
	@NotAudited
	@JsonIgnore 
	@OneToMany(mappedBy = "usertypeData", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Userrole> userRole;    

	@NotAudited
	@JsonIgnore
	@OneToMany(mappedBy ="userTypeFeature",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<UserToStakehoderfeatureMapping> userTofeatureMapping;
	
	private Integer status=1;
    
	@Transient
	private String statusInterp;
	
	private Integer selfRegister;

	private String defaultLink;
	
	private String modifiedBy;
	public long getId() {
		return id; 
	}
	
	public String getUsertypeName() {
		return usertypeName;
	}
	public void setUsertypeName(String usertypeName) {
		this.usertypeName = usertypeName;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setId(long id) {
		this.id = id;
	} 

	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) { 
		this.user = user;
	}
	public List<Userrole> getUserRole() {
		return userRole;
	}
	public void setUserRole(List<Userrole> userRole) {
		this.userRole = userRole;
	}
	public List<UserToStakehoderfeatureMapping> getUserTofeatureMapping() {
		return userTofeatureMapping;
	}
	public void setUserTofeatureMapping(List<UserToStakehoderfeatureMapping> userTofeatureMapping) {
		this.userTofeatureMapping = userTofeatureMapping;
	}
	public Usertype(long id) {
		super();
		this.id = id;
	}
	public Usertype() {
		super();
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusInterp() {
		return statusInterp;
	}
	public void setStatusInterp(String statusInterp) {
		this.statusInterp = statusInterp;
	}
	public Integer getSelfRegister() {
		return selfRegister;
	}
	public void setSelfRegister(Integer selfRegister) {
		this.selfRegister = selfRegister;
	}
	public String getDefaultLink() {
		return defaultLink;
	}
	public void setDefaultLink(String defaultLink) {
		this.defaultLink = defaultLink;
	}
	public Usertype(long id, String usertypeName) {
		super();
		this.id = id;
		this.usertypeName = usertypeName;
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
