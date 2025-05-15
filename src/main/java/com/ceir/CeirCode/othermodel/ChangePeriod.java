package com.ceir.CeirCode.othermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class ChangePeriod extends AllRequest{


	private Integer period;

	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangePeriod [period=");
		builder.append(period);
		builder.append("]");
		return builder.toString();
	}
	
	
}
