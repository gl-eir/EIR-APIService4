
package com.ceir.CeirCode.model.app;

public class ChangeUserStatus extends AllRequest {

	private Integer status;

	private long userId;
	
	private long id;
	private String username;
	private String remark;
	private String referenceId;
	private int action;
	private long role;
	private long usertype;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	
	public ChangeUserStatus(Integer status, long userId, long id, String username, String remark, String referenceId,
			int action, long roles) {
		super();
		this.status = status;
		this.userId = userId;
		this.id = id;
		this.username = username;
		this.remark = remark;
		this.referenceId = referenceId;
		this.action = action;
		this.role = roles;
	}
	public long getUsertype() {
		return usertype;
	}
	public void setUsertype(long usertype) {
		this.usertype = usertype;
	}
	public long getRole() {
		return role;
	}
	public void setRole(long role) {
		this.role = role;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeUserStatus [status=");
		builder.append(status);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", referenceId=");
		builder.append(referenceId);
		builder.append(", action=");
		builder.append(action);
		builder.append(", role=");
		builder.append(role);
		builder.append(", usertype=");
		builder.append(usertype);
		builder.append("]");
		return builder.toString();
	}
	
	
}
