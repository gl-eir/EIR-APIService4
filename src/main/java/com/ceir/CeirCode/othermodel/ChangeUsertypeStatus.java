package com.ceir.CeirCode.othermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class ChangeUsertypeStatus extends AllRequest{

	private long usertypeId;
	private Integer status;
	public long getUsertypeId() {
		return usertypeId;
	}
	public void setUsertypeId(long usertypeId) {
		this.usertypeId = usertypeId;
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
		builder.append("ChangeUsertypeStatus [usertypeId=");
		builder.append(usertypeId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
	
}
