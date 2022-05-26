package com.companyservice.companyservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Entity
public class Subscription {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique=true)
	@Id
	private String id;
	
	@NotBlank(message = "Please assign a name to this plan")
	@Column(name = "`planName`",columnDefinition = "VARCHAR(100)",unique=true)
	private String planName;
	@Column(name = "`amount`",columnDefinition = "VARCHAR(100)")
	private double amount;
	@Column(name = "`currencyType`",columnDefinition = "VARCHAR(25)")
	private String currencyType;
	@Column(name = "`emailsLimit`",columnDefinition = "VARCHAR(100)")
	private long emailsLimit;
	
	@Column(name = "`status`")
	private boolean status;
	
	@Column(name = "`createdAt`")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Date updatedAt;
	
	/**setters and getters */
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public long getEmailsLimit() {
		return emailsLimit;
	}
	public void setEmailsLimit(long emailsLimit) {
		this.emailsLimit = emailsLimit;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	/** constructor with all arguments */
	
	public Subscription(String id, @NotBlank(message = "Please assign a name to this plan") String planName,
			double amount, String currencyType, long emailsLimit, boolean status, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.planName = planName;
		this.amount = amount;
		this.currencyType = currencyType;
		this.emailsLimit = emailsLimit;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	/** To String method */
	
	public Subscription() {
		super();
	}
	
	/** To String method */
	
	@Override
	public String toString() {
		return "Subscription [id=" + id + ", planName=" + planName + ", amount=" + amount + ", currencyType="
				+ currencyType + ", emailsLimit=" + emailsLimit + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
	
	
	
	
	
	
	
	
}
