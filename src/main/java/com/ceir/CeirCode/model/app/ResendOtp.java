package com.ceir.CeirCode.model.app;

public class ResendOtp extends UserHeader{
 public ResendOtp(String userAgent, String publicIp, Integer userId) {
		super(userAgent, publicIp);
		this.userId = userId;
	}

public ResendOtp() {
		
	}

private Integer userId;

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

@Override
public String toString() {
	return "ResendOtp [userId=" + userId + "]";
}
 

}
