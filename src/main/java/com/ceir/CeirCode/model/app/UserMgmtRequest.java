package com.ceir.CeirCode.model.app;

import javax.persistence.Transient;


public class UserMgmtRequest {
	
	@Transient
	private long userTypeId;
	@Transient
	private long userId;
	public long getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMgmtRequest [userTypeId=");
		builder.append(userTypeId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}
}
