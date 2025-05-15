package com.ceir.CeirCode.model.app;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Table;

@Entity
//@Audited
 @Table(name = "user_feature")

public class UserToStakehoderfeatureMapping {

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

	//@JsonIgnore
	@NotAudited
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "feature_id", nullable = false) 
	private StakeholderFeature stakeholderFeature; 
	
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "user_type_id", nullable = false) 
	private Usertype userTypeFeature; 

	
	@Transient
	private String usertypeInterp;
	@Transient
	private String usertypeName;
	
	@Transient
	private String featureInterp;
	
	@Transient
	private String periodInterp;
	
	private Integer period;
	 
	private String modifiedBy;
	
	public long getId() {
		return id;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public StakeholderFeature getStakeholderFeature() {
		return stakeholderFeature;
	}

	public void setStakeholderFeature(StakeholderFeature stakeholderFeature) {
		this.stakeholderFeature = stakeholderFeature;
	}

	public Usertype getUserTypeFeature() {
		return userTypeFeature;
	}

	public void setUserTypeFeature(Usertype userTypeFeature) {
		this.userTypeFeature = userTypeFeature;
	}
	
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getUsertypeInterp() {
		return usertypeInterp;
	}

	public void setUsertypeInterp(String usertypeInterp) {
		this.usertypeInterp = usertypeInterp;
	}

	public String getFeatureInterp() {
		return featureInterp;
	}

	public void setFeatureInterp(String featureInterp) {
		this.featureInterp = featureInterp;
	}

	public String getPeriodInterp() {
		return periodInterp;
	}

	public void setPeriodInterp(String periodInterp) {
		this.periodInterp = periodInterp;
	}

	
	public String getUsertypeName() {
		return usertypeName;
	}

	public void setUsertypeName(String usertypeName) {
		this.usertypeName = usertypeName;
	}

	@Override
	public String toString() {
		return "UserToStakehoderfeatureMapping [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn
				+ ", stakeholderFeature=" + stakeholderFeature + ", userTypeFeature=" + userTypeFeature
				+ ", usertypeInterp=" + usertypeInterp + ", usertypeName=" + usertypeName + ", featureInterp="
				+ featureInterp + ", periodInterp=" + periodInterp + ", period=" + period + ", modifiedBy=" + modifiedBy
				+ "]";
	}

	 

}
