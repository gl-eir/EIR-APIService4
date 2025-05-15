package com.ceir.CeirCode.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
  @Table(name="feature_state_interpretation")

public class StatesInterpretationDb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime modifiedOn;

	@NotNull
	private Integer featureId;

	@NotNull
	private Integer state;
	
        @Column(name = "interpretation")
	private String interp;

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

	public Integer getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInterp() {
		return interp;
	}

	public void setInterp(String interp) {
		this.interp = interp;
	}

    @Override
    public String toString() {
        return "StatesInterpretationDb{" + "id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", featureId=" + featureId + ", state=" + state + ", interp=" + interp + '}';
    }

 
        
        

}
