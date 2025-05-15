package com.ceir.CeirCode.filtermodel;

public class SearchAssignee {

	private Integer type;
	private String field;
	private Integer userTypeId;
	private int roleTypeId;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	
	public long getRoleTypeId() {
		return roleTypeId;
	}
	public void setRoleTypeId(int roleTypeId) {
		this.roleTypeId = roleTypeId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchAssignee [type=");
		builder.append(type);
		builder.append(", field=");
		builder.append(field);
		builder.append(", userTypeId=");
		builder.append(userTypeId);
		builder.append(", roleTypeId=");
		builder.append(roleTypeId);
		builder.append("]");
		return builder.toString();
	}
}
