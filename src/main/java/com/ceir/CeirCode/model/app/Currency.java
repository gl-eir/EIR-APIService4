package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Audited
public class Currency  extends AllRequest{
 
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
	
	@Column(nullable =false)
	private String monthDate;

	@Column(nullable =false)
	private Integer month;
	
	@Column(nullable =false)
	private Integer year;
	@Column(nullable =false)
	private Integer currency;
	
	private double riel;
	private double dollar;
	
	@Transient
	private String currencyInterp;
	
	@Transient
	private String monthInterp;
	
	private String modifiedBy;
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public static void setSerialVersionUID(long serialVersionUID) {
		Currency.serialVersionUID = serialVersionUID;
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


	public String getMonthDate() {
		return monthDate;
	}


	public void setMonthDate(String monthDate) {
		this.monthDate = monthDate;
	}


	public Integer getMonth() {
		return month;
	}


	public void setMonth(Integer month) {
		this.month = month;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public Integer getCurrency() {
		return currency;
	}


	public void setCurrency(Integer currency) {
		this.currency = currency;
	}


	public double getRiel() {
		return riel;
	}


	public void setRiel(double riel) {
		this.riel = riel;
	}


	public double getDollar() {
		return dollar;
	}


	public void setDollar(double dollar) {
		this.dollar = dollar;
	}


	public String getCurrencyInterp() {
		return currencyInterp;
	}


	public void setCurrencyInterp(String currencyInterp) {
		this.currencyInterp = currencyInterp;
	}


	public String getMonthInterp() {
		return monthInterp;
	}


	public void setMonthInterp(String monthInterp) {
		this.monthInterp = monthInterp;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Currency [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", monthDate=");
		builder.append(monthDate);
		builder.append(", month=");
		builder.append(month);
		builder.append(", year=");
		builder.append(year);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", riel=");
		builder.append(riel);
		builder.append(", dollar=");
		builder.append(dollar);
		builder.append(", currencyInterp=");
		builder.append(currencyInterp);
		builder.append(", monthInterp=");
		builder.append(monthInterp);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append("]");
		return builder.toString();
	}
}
