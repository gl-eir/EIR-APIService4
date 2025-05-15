package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.ceir.CeirCode.util.Utility;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Table;
@Entity
//@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  @Table(name="user_pwd_security_question")

public class Securityquestion {
	private static long serialVersionUID = 1L;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String question;
	private Integer category;
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn=LocalDateTime.now();
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn=LocalDateTime.now();

	@NotAudited
	@JsonIgnore
	@OneToMany(mappedBy = "securityQuestion",  fetch = FetchType.LAZY)
	private List<UserSecurityquestion> userSecurityquestion;


	public List<UserSecurityquestion> getUserSecurityquestion() {
		return userSecurityquestion;
	}
	public void setUserSecurityquestion(List<UserSecurityquestion> userSecurityquestion) {
		this.userSecurityquestion = userSecurityquestion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Securityquestion [id=" + id + ", question=" + question + ", category=" + category + "]";
	}
	public Securityquestion(long id, String question) {
		super();
		this.id = id;
		this.question = question;
	}
	public Securityquestion() {
		super();
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
	public Securityquestion(long id, String question, Integer category) {
		super();
		this.id = id;
		this.question = question;
		this.category = category;
	}
	

	
}
