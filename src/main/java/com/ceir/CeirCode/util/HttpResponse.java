package com.ceir.CeirCode.util;
import com.ceir.CeirCode.model.app.User;
public class HttpResponse {
	private String response;
	private String tag;
	private Integer statusCode;
	private User user;
	
	
	public HttpResponse(String response, Integer statusCode) {
		super();
		this.response = response;
		this.statusCode = statusCode;
	}
	public HttpResponse(String response, Integer statusCode,String tag) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.tag=tag;
	}
	public HttpResponse(String response, Integer statusCode, User user) {
		super();
		this.response = response;
		this.statusCode = statusCode;
		this.user = user;
	}
	public HttpResponse() {
		super();
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	@Override
	public String toString() {
		return "HttpResponse [response=" + response + ", tag=" + tag + ", statusCode=" + statusCode + "]";
	}
	
}
