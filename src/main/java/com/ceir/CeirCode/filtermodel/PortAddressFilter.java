package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class PortAddressFilter  extends AllRequest{

	public String  startDate;
	public String   endDate;
	private Integer port;
	private String address;
	public String order,orderColumnName;
	
	
	
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
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PortAddressFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", port=");
		builder.append(port);
		builder.append(", address=");
		builder.append(address);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
