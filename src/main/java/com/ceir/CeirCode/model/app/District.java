package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
@Entity
@Audited
@Table(name = "district_db")

public class District extends AllRequest {
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;

	private String province;
	private String district;
	@Transient
	private String currentDistrictName;
	
	@Transient
	private String publicIp;
	
	@Transient
	private String browser;
	
	/*
	 * @OneToMany(mappedBy = "district") private List<Commune> commune;
	 * 
	 * public List<Commune> getCommune() { return commune; }
	 * 
	 * public void setCommune(List<Commune> commune) { this.commune = commune; }
	 */

	public String getCurrentDistrictName() {
		return currentDistrictName;
	}

	public void setCurrentDistrictName(String currentDistrictName) {
		this.currentDistrictName = currentDistrictName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("District [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", province=");
		builder.append(province);
		builder.append(", district=");
		builder.append(district);
		builder.append(", currentDistrictName=");
		builder.append(currentDistrictName);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append(", browser=");
		builder.append(browser);
		builder.append("]");
		return builder.toString();
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
}
