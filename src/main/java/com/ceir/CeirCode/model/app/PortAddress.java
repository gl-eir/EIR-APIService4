package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Audited
public class PortAddress extends AllRequest{

	private static long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	
	@Column(nullable =false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;
	
	private String address;
	
	private Integer port;
	
	private String approvedBy;
	@Transient
	private String portInterp;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public static void setSerialVersionUID(long serialVersionUID) {
		PortAddress.serialVersionUID = serialVersionUID;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public LocalDateTime getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}



	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}



	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public Integer getPort() {
		return port;
	}



	public void setPort(Integer port) {
		this.port = port;
	}



	public String getApprovedBy() {
		return approvedBy;
	}



	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}



	public String getPortInterp() {
		return portInterp;
	}



	public void setPortInterp(String portInterp) {
		this.portInterp = portInterp;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PortAddress [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", address=");
		builder.append(address);
		builder.append(", port=");
		builder.append(port);
		builder.append(", approvedBy=");
		builder.append(approvedBy);
		builder.append(", portInterp=");
		builder.append(portInterp);
		builder.append("]");
		return builder.toString();
	}
	
	
}
