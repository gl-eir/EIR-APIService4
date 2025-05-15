package com.ceir.CeirCode.othermodel;

public class PaymentParameters {

	
	private String tran_id;
	private double amount ;
	private String hash;
	private String firstname;
	private String lastname;
	private String phone ;
	private String email;
	private String return_url ;
	private String return_params;
	private String paymentUrl;
	
	
	
	public String getTran_id() {
		return tran_id;
	}
	public void setTran_id(String tran_id) {
		this.tran_id = tran_id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getReturn_params() {
		return return_params;
	}
	public void setReturn_params(String return_params) {
		this.return_params = return_params;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	public PaymentParameters() {
		super();
	}
	public PaymentParameters(String tran_id, double amount, String hash, String firstname, String lastname,
			String phone, String email, String return_url, String return_params,String paymentUrl) {
		super();
		this.tran_id = tran_id;
		this.amount = amount;
		this.hash = hash;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.return_url = return_url;
		this.return_params = return_params;
		this.paymentUrl=paymentUrl;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentParameters [tran_id=");
		builder.append(tran_id);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", hash=");
		builder.append(hash);
		builder.append(", firstname=");
		builder.append(firstname);
		builder.append(", lastname=");
		builder.append(lastname);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", return_url=");
		builder.append(return_url);
		builder.append(", return_params=");
		builder.append(return_params);
		builder.append(", paymentUrl=");
		builder.append(paymentUrl);
		builder.append("]");
		return builder.toString();
	}
}
