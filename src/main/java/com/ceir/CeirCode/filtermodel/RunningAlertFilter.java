package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class RunningAlertFilter extends AllRequest{

	
	public String  startDate;
	public String   endDate;
	private String alertId;
	private String searchString;
	private Integer status;
	public String order,orderColumnName,description;
	private String sort;
	
	
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getAlertId() {
		return alertId;
	}
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RunningAlertFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", alertId=");
		builder.append(alertId);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", status=");
		builder.append(status);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", sort=");
		builder.append(sort);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	

}
