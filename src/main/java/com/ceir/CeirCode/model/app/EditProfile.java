package com.ceir.CeirCode.model.app;
import java.util.List;
public class EditProfile {
	private Integer id;     
	private String firstName;  
	private String middleName;
	private String lastName;
	private String companyName;
	private String type;
	private Integer vatStatus;
	private String vatNo;
	private String propertyLocation;
	private String street;
	private String locality;
	private String province;
	private String country;
	private String passportNo;
	private String email;
	private String phoneNo;
	private List<QuestionPair> questionList ; 
	private Integer[] roles;     
	private Integer usertypeId; 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getVatStatus() {
		return vatStatus;
	}
	public void setVatStatus(Integer vatStatus) {
		this.vatStatus = vatStatus;
	}
	public String getVatNo() {
		return vatNo;
	}
	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}
	public String getPropertyLocation() {
		return propertyLocation;
	}
	public void setPropertyLocation(String propertyLocation) {
		this.propertyLocation = propertyLocation;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public List<QuestionPair> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<QuestionPair> questionList) {
		this.questionList = questionList;
	}
	public Integer[] getRoles() {
		return roles;
	}
	public void setRoles(Integer[] roles) {
		this.roles = roles;
	}
	public Integer getUsertypeId() {
		return usertypeId;
	}
	public void setUsertypeId(Integer usertypeId) {
		this.usertypeId = usertypeId;
	}
	@Override
	public String toString() {
		return "EditProfile [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", companyName=" + companyName + ", type=" + type + ", vatStatus=" + vatStatus + ", vatNo="
				+ vatNo + ", propertyLocation=" + propertyLocation + ", street=" + street + ", locality=" + locality
				+ ", province=" + province + ", country=" + country + ", passportNo=" + passportNo + ", email=" + email
				+ ", phoneNo=" + phoneNo + ", usertypeId=" + usertypeId + "]";
	}

	
	
	
	
}
