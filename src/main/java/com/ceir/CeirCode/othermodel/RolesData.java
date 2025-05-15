package com.ceir.CeirCode.othermodel;

public class RolesData {

	private long id;
	private String role;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RolesData [id=").append(id).append(", role=").append(role).append("]");
		return builder.toString();
	}
	public RolesData(long id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	
	
}
