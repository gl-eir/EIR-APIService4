package com.ceir.CeirCode.model.app;
public class Otp extends UserHeader{   
	private String phoneOtp;
	private String emailOtp;
	private long userid;
	private int forgotPassword=0;
	
	public String getPhoneOtp() {
		return phoneOtp;
	} 
	public void setPhoneOtp(String phoneOtp) {
		this.phoneOtp = phoneOtp;
	}
	public String getEmailOtp() {
		return emailOtp;
	}
	public void setEmailOtp(String emailOtp) {
		this.emailOtp = emailOtp;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public int getForgotPassword() {
		return forgotPassword;
	}
	public void setForgotPassword(int forgotPassword) {
		this.forgotPassword = forgotPassword;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Otp [phoneOtp=");
		builder.append(phoneOtp);
		builder.append(", emailOtp=");
		builder.append(emailOtp);
		builder.append(", userid=");
		builder.append(userid);
		builder.append(", forgotPassword=");
		builder.append(forgotPassword);
		builder.append("]");
		return builder.toString();
	}
	
	
}
