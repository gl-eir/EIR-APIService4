package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class UsertypeFilter extends AllRequest{
	public String  startDate;
	public String   endDate;
	private Integer id;
	private String searchString;
	private Integer status;
	private String columnName,sort;
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsertypeFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", id=");
		builder.append(id);
		builder.append(", searchString=");
		builder.append(searchString);
		builder.append(", status=");
		builder.append(status);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append(", sort=");
		builder.append(sort);
		builder.append("]");
		return builder.toString();
	}
	 
	
	 
}

