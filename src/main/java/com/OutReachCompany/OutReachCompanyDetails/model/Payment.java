package com.OutReachCompany.OutReachCompanyDetails.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Table
@Entity
public class Payment {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique=true)
	@Id
	private String paymentId;
	
	//@NotNull(message = "Company Id cannot be blank")
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	private Company companyId;
	
	//@NotNull(message = "Subscription Id cannot be blank")
	@JoinColumn(name = "id", referencedColumnName = "id")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
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
	

	 /**setters and getters */
	 
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
	
	/** constructor with all arguments */
	
	public Payment(String paymentId, @NotBlank(message = "Company Id cannot be blank") Company companyId,
			@NotBlank(message = "Subscription Id cannot be blank") Subscription subscriptionId,
			@NotBlank(message = "Transaction Id cannot be blank") String transactionId,
			@NotBlank(message = "Amount cannot be blank") String amount,
			@NotBlank(message = "Payment Mode cannot be blank") String paymentMode,
			@NotBlank(message = "Paid Date cannot be blank") Date paidDate, boolean status) {
		super();
		this.paymentId = paymentId;
		this.companyId = companyId;
		this.subscriptionId = subscriptionId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.paymentMode = paymentMode;
		this.paidDate = paidDate;
		this.status = status;
	}


	/** constructor with no arguments */
	
	public Payment() {
		super();
	}


	/** To String method */

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", companyId=" + companyId + ", subscriptionId=" + subscriptionId
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", paymentMode=" + paymentMode
				+ ", paidDate=" + paidDate + ", status=" + status + "]";
	}
	
}
