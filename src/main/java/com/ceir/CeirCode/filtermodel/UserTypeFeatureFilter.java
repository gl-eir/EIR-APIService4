package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class UserTypeFeatureFilter extends AllRequest{
	
	public String  startDate;
	public String   endDate;
	private Integer usertypeId;
	private Integer feature;
	private String searchString;
	private Integer period;
	public String order,orderColumnName;
	private String sort;
	
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public Integer getFeature() {
		return feature;
	}
	public void setFeature(Integer feature) {
		this.feature = feature;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserTypeFeatureFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", usertypeId=");
		builder.append(usertypeId);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", period=");
		builder.append(period);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append(", sort=");
		builder.append(sort);
		builder.append("]");
		return builder.toString();
	}
	
}
