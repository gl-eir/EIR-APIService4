package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class ReqHeaderFilter extends AllRequest{

	public String  startDate;
	public String   endDate;
	private String searchString;
	private String publicIp;
	private String username;
	public String order,orderColumnName,browser;
	public String filterPublicIp,filterBrowser,filteredUsername;
	
	
	
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getFilterPublicIp() {
		return filterPublicIp;
	}
	public void setFilterPublicIp(String filterPublicIp) {
		this.filterPublicIp = filterPublicIp;
	}
	public String getFilterBrowser() {
		return filterBrowser;
	}
	public void setFilterBrowser(String filterBrowser) {
		this.filterBrowser = filterBrowser;
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
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReqHeaderFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append(", username=");
		builder.append(username);
		builder.append(", order=");
		builder.append(order);
		builder.append(", orderColumnName=");
		builder.append(orderColumnName);
		builder.append(", browser=");
		builder.append(browser);
		builder.append(", filterPublicIp=");
		builder.append(filterPublicIp);
		builder.append(", filterBrowser=");
		builder.append(filterBrowser);
		builder.append(", filteredUsername=");
		builder.append(filteredUsername);
		builder.append("]");
		return builder.toString();
	}
	
		
	
	
}
