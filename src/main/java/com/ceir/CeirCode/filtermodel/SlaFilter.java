package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class SlaFilter extends AllRequest{
	public String  startDate;
	public String  endDate;
	private String searchString;
	private Integer feature;
	private Integer usertype;
	public String order,orderColumnName,filteredUsername,txnId;
	
	
	
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getFilteredUsername() {
		return filteredUsername;
	}
	public void setFilteredUsername(String filteredUsername) {
		this.filteredUsername = filteredUsername;
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
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	public Integer getFeature() {
		return feature;
	}
	public void setFeature(Integer feature) {
		this.feature = feature;
	}
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SlaFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", usertype=");
		builder.append(usertype);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append(", filteredUsername=");
		builder.append(filteredUsername);
		builder.append(", txnId=");
		builder.append(txnId);
		builder.append("]");
		return builder.toString();
	}
	
	
}
