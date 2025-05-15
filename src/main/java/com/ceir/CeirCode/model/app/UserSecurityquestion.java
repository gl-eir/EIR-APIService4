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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity       
//@Audited
public class UserSecurityquestion {
	private static long serialVersionUID = 1L;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "question_id", nullable = false)
	private Securityquestion securityQuestion;
	
	private String answer;
	
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

	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	/*
	 * @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 * 
	 * @JoinColumn(name = "user_id1", nullable = false) private Users users;
	 */

	
	public long getId() {
		return id; 
	}
	public void setId(long id) {
		this.id = id;
	}
	public Securityquestion getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(Securityquestion securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/*
	 * public Users getUsers() { return users; } public void setUsers(Users users) {
	 * this.users = users; }
	 */
	
	public UserSecurityquestion() {
		super();
	}
	public UserSecurityquestion(Securityquestion securityQuestion, String answer) {
		super();
		this.securityQuestion = securityQuestion;
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "UserSecurityquestion [id=" + id + ", answer=" + answer + "]";
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public static void setSerialVersionUID(long serialVersionUID) {
		UserSecurityquestion.serialVersionUID = serialVersionUID;
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
