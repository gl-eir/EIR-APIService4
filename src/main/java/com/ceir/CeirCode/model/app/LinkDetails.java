package com.ceir.CeirCode.model.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "link")
public class LinkDetails {

	@Id
	@Column(nullable = false, unique = true)
	private String linkId;

	@Column(nullable = false)
	private LocalDateTime createdOn;

	@Column(nullable = false)
	private LocalDateTime modifiedOn;

	@Column(nullable = false)
	private String status;

	private String linkState;

	private Long userTypeId;

	//[{"linkId":"Server_8084","linkState":"CLOSED"},{"linkId":"Server_8889","linkState":"INACTIVE"}]
	// Getters and Setters

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}



	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}



	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLinkState() {
		return linkState;
	}

	public void setLinkState(String linkState) {
		this.linkState = linkState;
	}

	public long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getCreatedOn() {
		return createdOn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	public String getModifiedOn() {
		return modifiedOn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	@Override
	public String toString() {
		return "Link{" +
				"createdOn=" + getCreatedOn() +
				", modifiedOn=" + getModifiedOn() +
				", status='" + status + '\'' +
				", userTypeId='" + userTypeId + '\'' +
				", linkState='" + linkState + '\'' +
				", linkId='" + linkId + '\'' +
				'}';
	}
}