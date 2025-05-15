package com.ceir.CeirCode.model.app;
public class UserStatusRequest extends UserHeader{
	private String status;
	private Integer userId;
	private Integer statusValue;
	private String remark;
	private long featureId;
	private String username;
	private long id;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
		public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}
	public Integer getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserStatusRequest [status=");
		builder.append(status);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", statusValue=");
		builder.append(statusValue);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", username=");
		builder.append(username);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
