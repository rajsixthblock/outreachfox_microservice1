package com.companyservice.companyservice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table
@Entity
public class Payment {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique=true)
	@Id
	private String paymentId;
	
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"subscriptionId","password","status","address","createdAt","updatedAt"})
	private Company companyId;
	
	@JoinColumn(name = "id", referencedColumnName = "id")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"status","createdAt","updatedAt"})
	private Subscription subscriptionId;
	
	@NotBlank(message = "Transaction Id cannot be blank")
	@Column(name = "`transactionId`",columnDefinition = "VARCHAR(30)", unique=true)
	private String transactionId;
	
	@NotBlank(message = "Amount cannot be blank")
	@Column(name = "`amount`",columnDefinition = "VARCHAR(100)")
	private String amount;
	
	@NotBlank(message = "Payment Mode cannot be blank")
	@Column(name = "`paymentMode`",columnDefinition = "VARCHAR(30)")
	private String paymentMode;
	
	//@NotNull(message = "Paid Date cannot be blank")
	@Column(name = "`paidDate`")
	private Date paidDate;
	
	@Column(name = "`status`")
	private boolean status;
	
	@Column(name = "`createdAt`")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Date updatedAt;
	
	/**setters and getters */
	 
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


	
	public String getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}


	public Company getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}


	public Subscription getSubscriptionId() {
		return subscriptionId;
	}


	public void setSubscriptionId(Subscription subscriptionId) {
		this.subscriptionId = subscriptionId;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getPaymentMode() {
		return paymentMode;
	}


	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


	public Date getPaidDate() {
		return paidDate;
	}


	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	

	/** constructor with no arguments */
	
	public Payment() {
		super();
	}

	/** constructor with all arguments */
	public Payment(String paymentId, Company companyId, Subscription subscriptionId,
			@NotBlank(message = "Transaction Id cannot be blank") String transactionId,
			@NotBlank(message = "Amount cannot be blank") String amount,
			@NotBlank(message = "Payment Mode cannot be blank") String paymentMode, Date paidDate, boolean status,
			Date createdAt, Date updatedAt) {
		super();
		this.paymentId = paymentId;
		this.companyId = companyId;
		this.subscriptionId = subscriptionId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.paymentMode = paymentMode;
		this.paidDate = paidDate;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", companyId=" + companyId + ", subscriptionId=" + subscriptionId
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", paymentMode=" + paymentMode
				+ ", paidDate=" + paidDate + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
}
