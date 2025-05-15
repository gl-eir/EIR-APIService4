
package com.ceir.CeirCode.model.app;

import com.ceir.CeirCode.model.oam.RequestHeaders;

public class ChangePassword extends RequestHeaders {
	private String password;
	private String oldPassword;
	private Integer userid;
	public String getPassword() {
		return password;
	} 
	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	@Override
	public String toString() {
		return "ChangePassword [password=" + password + ", oldPassword=" + oldPassword + ", userid=" + userid + "]";
	}
	
}
