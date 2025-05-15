package com.ceir.CeirCode.model.app;

public class UserLogin extends UserHeader{
	private Integer userId;
	private String username;
	private String password;
	private String usertype;
	
	

	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLogin [userId=");
		builder.append(userId);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", usertype=");
		builder.append(usertype);
		builder.append("]");
		return builder.toString();
	}
	
	
}
