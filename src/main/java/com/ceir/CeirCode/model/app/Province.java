package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
@Audited
@Table(name = "province_db")
public class Province extends AllRequest {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;
	private String province;

	private String country = "Cambodia";
	@Transient
	private String updatingProvinceName;
	
	@Transient
	private String publicIP;
	

	@Transient
	private String publicIp;
	
	
	@Transient
	private String browser;
	
	
	
	public String getPublicIP() {
		return publicIP;
	}

	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUpdatingProvinceName() {
		return updatingProvinceName;
	}

	public void setUpdatingProvinceName(String updatingProvinceName) {
		this.updatingProvinceName = updatingProvinceName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	@Override
	public String toString() {
		return "Province [id=" + id + ", createdOn=" + createdOn + ", province=" + province + ", country=" + country
				+ ", updatingProvinceName=" + updatingProvinceName + ", publicIP=" + publicIP + ", publicIp=" + publicIp
				+ ", browser=" + browser + "]";
	}

	
	
	
}
