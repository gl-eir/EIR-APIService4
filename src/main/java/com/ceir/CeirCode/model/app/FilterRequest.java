package com.ceir.CeirCode.model.app;

public class FilterRequest {
	public String startDate;
	public String endDate;
	private Integer status;
	private Integer asType;
	private long userRoleTypeId;
	private Integer viewAllUserStatus;
	private Integer featureId;
	private long userId;
	private Integer userTypeId;
	private String searchString;
	private String email;
	private String phoneNo;
	private String username;
	private Long id;
	private String columnName;
	private String sort;
	private String userType;
	private String publicIp;
	private String browser;
	private String filteredUsername;
	
	
	
	public String getFilteredUsername() {
		return filteredUsername;
	}

	public void setFilteredUsername(String filteredUsername) {
		this.filteredUsername = filteredUsername;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAsType() {
		return asType;
	}

	public void setAsType(Integer asType) {
		this.asType = asType;
	}

	public long getUserRoleTypeId() {
		return userRoleTypeId;
	}

	public void setUserRoleTypeId(long userRoleTypeId) {
		this.userRoleTypeId = userRoleTypeId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getViewAllUserStatus() {
		return viewAllUserStatus;
	}

	public void setViewAllUserStatus(Integer viewAllUserStatus) {
		this.viewAllUserStatus = viewAllUserStatus;
	}

	public Integer getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer usertypeId) {
		this.userTypeId = usertypeId;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FilterRequest [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", asType=");
		builder.append(asType);
		builder.append(", userRoleTypeId=");
		builder.append(userRoleTypeId);
		builder.append(", viewAllUserStatus=");
		builder.append(viewAllUserStatus);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userTypeId=");
		builder.append(userTypeId);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", username=");
		builder.append(username);
		builder.append(", id=");
		builder.append(id);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append(", browser=");
		builder.append(browser);
		builder.append(", filteredUsername=");
		builder.append(filteredUsername);
		builder.append("]");
		return builder.toString();
	}

}