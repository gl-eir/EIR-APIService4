package com.ceir.CeirCode.response;

public class GenricResponse {

	private int errorCode;
	private String tag;
	private String message;
	private String txnId;
	private Object data;
	private int statusCode;
	public GenricResponse(){}
	public GenricResponse(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public GenricResponse(int errorCode, String message, String txnId) {
		this.errorCode = errorCode;
		this.message = message;
		this.txnId = txnId;
	}
	
	public GenricResponse(int errorCode, String tag, String message, String txnId) {
		this.errorCode = errorCode;
		this.tag = tag;
		this.message = message;
		this.txnId = txnId;
	}
	
	public GenricResponse(int errorCode, String message, String txnId, Object data) {
		this.errorCode = errorCode;
		this.message = message;
		this.txnId = txnId;
		this.data = data;
	}
	public GenricResponse(String message, int statusCode) {

		this.message = message;
		this.statusCode = statusCode;
	}
 
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "GenricResponse{" +
				"errorCode=" + errorCode +
				", tag='" + tag + '\'' +
				", message='" + message + '\'' +
				", txnId='" + txnId + '\'' +
				", data=" + data +
				", statusCode=" + statusCode +
				'}';
	}
}
