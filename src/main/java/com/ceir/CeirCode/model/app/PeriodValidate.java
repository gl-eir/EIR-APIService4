package com.ceir.CeirCode.model.app;

public class PeriodValidate {

	private long usertypeId;
	private long featureId;
	public long getUsertypeId() {
		return usertypeId;
	}
	public void setUsertypeId(long usertypeId) {
		this.usertypeId = usertypeId;
	}
	public long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}
	@Override
	public String toString() {
		return "PeriodValidate [usertypeId=" + usertypeId + ", featureId=" + featureId + "]";
	}
	
	
}
