package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import javax.persistence.Table;
@ApiModel
@Entity
  @Table(name="feature_rule")

public class RuleEngineMapping {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime modifiedOn;
	
//	@CreationTimestamp
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
//	private LocalDateTime createdOn;
//
//	@UpdateTimestamp
//	private LocalDateTime modifiedOn;

	@NotNull
	@Column(length = 20)
	private String feature;

	@NotNull
	//@Enumerated(EnumType.STRING)
	private String name; 
	
	@NotNull
	@Column(length = 20)
	private String graceAction;
	
	@NotNull
	@Column(length = 20)
	private String postGraceAction;
	
	@NotNull
	private Integer ruleOrder;
	
	@NotNull
	@Column(length = 20)
	private String userType;
	
	@Column(length = 10)
	private String failedRuleActionGrace;
	
	@Column(length = 10)
	private String failedRuleActionPostGrace;

	@Column(length = 1)
	private String output;
	
	//@Transient parameters 	
	@Transient
	public String userName;
	@Transient
	private Integer featureId;
	@Transient
	private Integer userTypeId;
	@Transient
	private String roleType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGraceAction() {
		return graceAction;
	}
	public void setGraceAction(String graceAction) {
		this.graceAction = graceAction;
	}
	public String getPostGraceAction() {
		return postGraceAction;
	}
	public void setPostGraceAction(String postGraceAction) {
		this.postGraceAction = postGraceAction;
	}
	public Integer getRuleOrder() {
		return ruleOrder;
	}
	public void setRuleOrder(Integer ruleOrder) {
		this.ruleOrder = ruleOrder;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getFailedRuleActionGrace() {
		return failedRuleActionGrace;
	}
	public void setFailedRuleActionGrace(String failedRuleActionGrace) {
		this.failedRuleActionGrace = failedRuleActionGrace;
	}
	public String getFailedRuleActionPostGrace() {
		return failedRuleActionPostGrace;
	}
	public void setFailedRuleActionPostGrace(String failedRuleActionPostGrace) {
		this.failedRuleActionPostGrace = failedRuleActionPostGrace;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleEngineMapping [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", name=");
		builder.append(name);
		builder.append(", graceAction=");
		builder.append(graceAction);
		builder.append(", postGraceAction=");
		builder.append(postGraceAction);
		builder.append(", ruleOrder=");
		builder.append(ruleOrder);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", failedRuleActionGrace=");
		builder.append(failedRuleActionGrace);
		builder.append(", failedRuleActionPostGrace=");
		builder.append(failedRuleActionPostGrace);
		builder.append(", output=");
		builder.append(output);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", userTypeId=");
		builder.append(userTypeId);
		builder.append(", roleType=");
		builder.append(roleType);
		builder.append("]");
		return builder.toString();
	}
	
}
