package com.ceir.CeirCode.model.app;

public class UserDetails extends AllRequest{

	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNo;
	private String email;
	private String password;
	private String userName;
	private long id;
	private long usertypeId;
	

	private String viewUserType;
	private String approvedBy;
	private Integer currentStatus; 
	
	

	public Integer getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	private String remarks;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getUsertypeId() {
		return usertypeId;
	}
	public void setUsertypeId(long usertypeId) {
		this.usertypeId = usertypeId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getViewUserType() {
		return viewUserType;
	}
	public void setViewUserType(String viewUserType) {
		this.viewUserType = viewUserType;
	}


	public UserDetails(String firstName, String middleName, String lastName,
			String phoneNo, String email, String userType, String userName, long id, long
			userTypeId, String remarks, String approvedBy, Integer currentStatus) {
		super();
		this.firstName = firstName;
			this.middleName = middleName;
			this.lastName = lastName; 
			this.phoneNo =phoneNo; 
			this.email = email; 
			this.userName = userName;
			this.viewUserType=userType; 
			this.id = id; 
			this.usertypeId = userTypeId;
			this.remarks = remarks; 
			this.approvedBy = approvedBy;
			this.currentStatus = currentStatus;
			}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDetails [firstName=");
		builder.append(firstName);
		builder.append(", middleName=");
		builder.append(middleName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", email=");
		builder.append(email);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", id=");
		builder.append(id);
		builder.append(", usertypeId=");
		builder.append(usertypeId);
		builder.append(", viewUserType=");
		builder.append(viewUserType);
		builder.append(", approvedBy=");
		builder.append(approvedBy);
		builder.append(", currentStatus=");
		builder.append(currentStatus);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append("]");
		return builder.toString();
	}

}
