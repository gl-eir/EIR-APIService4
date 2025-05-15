package com.ceir.CeirCode.othermodel;

public class UserPort {

	
	private long userTypeId;
	private Integer arrivalPort;
	private Integer portAddress;
	public long getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}
	public Integer getArrivalPort() {
		return arrivalPort;
	}
	public void setArrivalPort(Integer arrivalPort) {
		this.arrivalPort = arrivalPort;
	}
	public Integer getPortAddress() {
		return portAddress;
	}
	public void setPortAddress(Integer portAddress) {
		this.portAddress = portAddress;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPort [userTypeId=");
		builder.append(userTypeId);
		builder.append(", arrivalPort=");
		builder.append(arrivalPort);
		builder.append(", portAddress=");
		builder.append(portAddress);
		builder.append("]");
		return builder.toString();
	}
}
