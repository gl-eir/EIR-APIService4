package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class UserMgmtFilter extends AllRequest{

	public String  startDate;
	public String   endDate;
	private Integer usertypeId;
	private String searchString;
	public String order,orderColumnName;
	private String email;
	private String phoneNo;
	private String username;
	private String filteredUsername;
	private String sort;
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getFilteredUsername() {
		return filteredUsername;
	}
	public void setFilteredUsername(String filteredUsername) {
		this.filteredUsername = filteredUsername;
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
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderColumnName() {
		return orderColumnName;
	}
	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
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
	public Integer getUsertypeId() {
		return usertypeId;
	}
	public void setUsertypeId(Integer usertypeId) {
		this.usertypeId = usertypeId;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserMgmtFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", usertypeId=");
		builder.append(usertypeId);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", username=");
		builder.append(username);
		builder.append(", filteredUsername=");
		builder.append(filteredUsername);
		builder.append(", sort=");
		builder.append(sort);
		builder.append("]");
		return builder.toString();
	}
	
	
}
