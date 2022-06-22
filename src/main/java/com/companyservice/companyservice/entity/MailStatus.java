package com.companyservice.companyservice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class MailStatus {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique=true)
	@Id
	private String mailStatusId;
	
	@JoinColumn(name = "id", referencedColumnName = "id")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"campaignId","address","isLead","status","createdAt","updatedAt"}) 
	private Audience audienceId;
	
	@JoinColumn(name = "campaignId", referencedColumnName = "campaignId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"companyId","status","createdAt","updatedAt"})
	private Campaign campaignId;
	
	@Column(name = "`sent`")
	private Boolean sent;
	
	@Column(name = "`read`")
	private Boolean read;
	
	@Column(name = "`bounced`")
	private Boolean bounced;
	
	@Column(name = "`replied`")
	private Boolean replied;
	
	@Column(name = "`pending`")
	private Boolean pending;
	
	@Column(name = "`unsubscribed`")
	private Boolean unsubscribed;
	
	@Column(name = "`error`")
	private Boolean error;
	
	@Column(name = "`sentDate`")
	private Date sentDate;
	
	
	
	 /**setters and getters */
	 
	public String getMailStatusId() {
		return mailStatusId;
	}

	public void setMailStatusId(String mailStatusId) {
		this.mailStatusId = mailStatusId;
	}

	public Audience getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Audience audienceId) {
		this.audienceId = audienceId;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Boolean getBounced() {
		return bounced;
	}

	public void setBounced(Boolean bounced) {
		this.bounced = bounced;
	}

	public Boolean getReplied() {
		return replied;
	}

	public void setReplied(Boolean replied) {
		this.replied = replied;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

	public Boolean getUnsubscribed() {
		return unsubscribed;
	}

	public void setUnsubscribed(Boolean unsubscribed) {
		this.unsubscribed = unsubscribed;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
	
	public Campaign getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Campaign campaignId) {
		this.campaignId = campaignId;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	/** constructor with all arguments */
	
	public MailStatus(String mailStatusId, Audience audienceId, Campaign campaignId, Boolean sent, Boolean read, Boolean bounced,
			Boolean replied, Boolean pending, Boolean unsubscribed, Boolean error, Date sentDate) {
		super();
		this.mailStatusId = mailStatusId;
		this.audienceId = audienceId;
		this.campaignId = campaignId;
		this.sent = sent;
		this.read = read;
		this.bounced = bounced;
		this.replied = replied;
		this.pending = pending;
		this.unsubscribed = unsubscribed;
		this.error = error;
		this.sentDate = sentDate;
	}
	
	/** constructor with no arguments */
	
	public MailStatus() {
		super();
	}
	
	
	/** To String method */

	@Override
	public String toString() {
		return "MailStatus [mailStatusId=" + mailStatusId + ", audienceId=" + audienceId + ", campaignId=" + campaignId + ", sent=" + sent
				+ ", read=" + read + ", bounced=" + bounced + ", replied=" + replied + ", pending=" + pending
				+ ", unsubscribed=" + unsubscribed + ", error=" + error + ", sentDate=" + sentDate + "]";
	}

	
}
