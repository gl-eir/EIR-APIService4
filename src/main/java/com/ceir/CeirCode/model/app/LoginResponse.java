package com.ceir.CeirCode.model.app;
import java.util.List;
public class LoginResponse {
	private String response;
	private Integer statusCode;
	private List<Usertype> userRoles;
	private String username; 
	private long userId;
	private String name;
	private String primaryRole;
	private long primaryRoleId; 
	private String status;
	private Integer statusValue;
	private String operatorTypeName;
    private Integer operatorTypeId;
	private String userLanguage;
	private String password;
	private String period;
	private Integer selfRegister;
	private String defaultLink;
	private String token;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status; 
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public List<Usertype> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<Usertype> userRoles) {
		this.userRoles = userRoles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrimaryRole() {
		return primaryRole;
	}
	public void setPrimaryRole(String primaryRole) {
		this.primaryRole = primaryRole;
	}
	public long getPrimaryRoleId() {
		return primaryRoleId;
	}
	public void setPrimaryRoleId(long primaryRoleId) {
		this.primaryRoleId = primaryRoleId;
	}
	public String getOperatorTypeName() {
		return operatorTypeName;
	}
	public void setOperatorTypeName(String operatorTypeName) {
		this.operatorTypeName = operatorTypeName;
	}
	public Integer getOperatorTypeId() {
		return operatorTypeId;
	}
	public void setOperatorTypeId(Integer operatorTypeId) {
		this.operatorTypeId = operatorTypeId;
	}
	
	public String getUserLanguage() {
		return userLanguage;
	}
	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public LoginResponse() {
		super();
	}
	public Integer getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LoginResponse(String response, Integer statusCode, List<Usertype> userRoles, String username, long userId,
			String name, String primaryRole, long primaryRoleId, String status, String operatorTypeName,
			Integer operatorTypeId, String userLanguage,String period,Integer statusValue,Integer selfRegister,String defaultLink) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.userRoles = userRoles;
		this.username = username;
		this.userId = userId;
		this.name = name;
		this.primaryRole = primaryRole;
		this.primaryRoleId = primaryRoleId;
		this.status = status;
		this.operatorTypeName = operatorTypeName;
		this.operatorTypeId = operatorTypeId;
		this.userLanguage = userLanguage;
		this.period=period;
		this.statusValue=statusValue;
		this.selfRegister=selfRegister;
		this.defaultLink=defaultLink;
	}
	public LoginResponse(String response, Integer statusCode, long userId) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.userId = userId;
	}
	
	
	public LoginResponse(String response, Integer statusCode, List<Usertype> userRoles, String username, long userId,
			String name, String primaryRole, long primaryRoleId, String status, String operatorTypeName,
			Integer operatorTypeId, String userLanguage,String period,Integer statusValue,String password) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.userRoles = userRoles;
		this.username = username;
		this.userId = userId;
		this.name = name;
		this.primaryRole = primaryRole;
		this.primaryRoleId = primaryRoleId;
		this.status = status;
		this.statusValue = statusValue;
		this.operatorTypeName = operatorTypeName;
		this.operatorTypeId = operatorTypeId;
		this.userLanguage = userLanguage;
		this.password = password;
		this.period = period;
	}
	
	
	public LoginResponse(String response, Integer statusCode, List<Usertype> userRoles, String username, long userId,
			String name, String primaryRole, long primaryRoleId, String status, String operatorTypeName,
			Integer operatorTypeId, String userLanguage,String period,Integer statusValue,String password,Integer selfRegister) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.userRoles = userRoles;
		this.username = username;
		this.userId = userId;
		this.name = name;
		this.primaryRole = primaryRole;
		this.primaryRoleId = primaryRoleId;
		this.status = status;
		this.statusValue = statusValue;
		this.operatorTypeName = operatorTypeName;
		this.operatorTypeId = operatorTypeId;
		this.userLanguage = userLanguage;
		this.password = password;
		this.period = period;
		this.selfRegister=selfRegister;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [response=");
		builder.append(response);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", userRoles=");
		builder.append(userRoles);
		builder.append(", username=");
		builder.append(username);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", primaryRole=");
		builder.append(primaryRole);
		builder.append(", primaryRoleId=");
		builder.append(primaryRoleId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", statusValue=");
		builder.append(statusValue);
		builder.append(", operatorTypeName=");
		builder.append(operatorTypeName);
		builder.append(", operatorTypeId=");
		builder.append(operatorTypeId);
		builder.append(", userLanguage=");
		builder.append(userLanguage);
		builder.append(", password=");
		builder.append(password);
		builder.append(", period=");
		builder.append(period);
		builder.append(", selfRegister=");
		builder.append(selfRegister);
		builder.append(", defaultLink=");
		builder.append(defaultLink);
		builder.append("]");
		return builder.toString();
	}
	
	

}