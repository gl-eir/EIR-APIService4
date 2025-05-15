package com.ceir.CeirCode.model.app;

//package com.ceir.CeirCode.model;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.Type;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//
//@Entity
//public class TypeApprovedDb {
//
//	 //private final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//			 
//			 
//	private static long serialVersionUID = 1L;
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
//	private String manufacturerId;
//	private String manufacturerName;
//	private String country;
//
//	@Type(type="date")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date requestDate;
//	private String tac;
//	private Integer status=1;
//	private Integer approveStatus;
//
//	
//	@Type(type="date")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date approveDisapproveDate;
//	
//	private String remark;
//	private String file;
//	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//	@CreationTimestamp
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private Date createdOn;
//	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//	@UpdateTimestamp
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	private Date modifiedOn;
//	private String txnId;
//
//
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "user_id", nullable = false) 
//	private User userForTypeApprove;
//
//
//
//	public long getId() {
//		return id;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
//	public String getManufacturerId() {
//		return manufacturerId;
//	}
//	public void setManufacturerId(String manufacturerId) {
//		this.manufacturerId = manufacturerId;
//	}
//	public String getManufacturerName() {
//		return manufacturerName;
//	}
//	public void setManufacturerName(String manufacturerName) {
//		this.manufacturerName = manufacturerName;
//	}
//	public String getCountry() {
//		return country;
//	}
//	public void setCountry(String country) {
//		this.country = country;
//	}
//	public Date getRequestDate() {
//		return requestDate;
//	}
//	public void setRequestDate(Date requestDate) {
//		this.requestDate = requestDate;
//	}
//	public String getTac() {
//		return tac;
//	}
//	public void setTac(String tac) {
//		this.tac = tac;
//	}
//	public Integer getStatus() {
//		return status;
//	}
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//	public Date getApproveDisapproveDate() {
//		return approveDisapproveDate;
//	}
//	public void setApproveDisapproveDate(Date approveDisapproveDate) {
//		this.approveDisapproveDate = approveDisapproveDate;
//	}
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//	public String getFile() {
//		return file;
//	}
//	public void setFile(String file) {
//		this.file = file;
//	}
//	public Date getCreatedOn() {
//		return createdOn;
//	}
//	public void setCreatedOn(Date createdOn) {
//		this.createdOn = createdOn;
//	}
//	public Date getModifiedOn() {
//		return modifiedOn;
//	}
//	public void setModifiedOn(Date modifiedOn) {
//		this.modifiedOn = modifiedOn;
//	}
//	public Integer getApproveStatus() {
//		return approveStatus;
//	}
//	public void setApproveStatus(Integer approveStatus) {
//		this.approveStatus = approveStatus;
//	}
//	public String getTxnId() {
//		return txnId;
//	}
//	public void setTxnId(String txnId) {
//		this.txnId = txnId;
//	}
//
//
//
//	public User getUserForTypeApprove() { 
//		return userForTypeApprove; 
//	} 
//	public void setUserForTypeApprove(User userForTypeApprove) { 
//		this.userForTypeApprove
//		= userForTypeApprove; 
//	}
//
//
//
//	@Override
//	public String toString() {
//		return "TypeApprovedDb [id=" + id + ", manufacturerId=" + manufacturerId + ", manufacturerName="
//				+ manufacturerName + ", country=" + country + ", requestDate=" + requestDate + ", tac=" + tac
//				+ ", status=" + status + ", approveStatus=" + approveStatus + ", approveDisapproveDate="
//				+ approveDisapproveDate + ", remark=" + remark + ", file=" + file + ", createdOn=" + createdOn
//				+ ", modifiedOn=" + modifiedOn + ", txnId=" + txnId + "]";
//	}
//}
