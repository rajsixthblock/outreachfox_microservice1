package com.companyservice.companyservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;
@Table
@Entity
public class Campaign {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)",unique=true)
	@Id
	private String campaginId;
	
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	private Company companyId;
	
	@NotBlank(message = "name can't be blank")
	@Column(name = "`name`",columnDefinition = "VARCHAR(100)")
	private String name;
	
	@NotBlank(message = "title can't be blank")
	@Column(name = "`title`",columnDefinition = "VARCHAR(100)")
	private String title;
	
	@NotBlank(message = "email can't be blank")
	@Email(message = "invalid email")
	@Column(name = "`email`",columnDefinition = "VARCHAR(100)")
	private String email;
	
	@Column(name = "`delivery`")
	private String delivery;
	
	@Column(name = "`scheduleDate`")
	private Date scheduleDate ;
	
	@Column(name = "`followUp`")
	private boolean followUp;
	
	@Column(name = "`FollowupAfterDays`")
	private int FollowupAfterDays;
	
	
	@Column(name = "`status`")
	private boolean status;
	
	@Column(name = "`createdAt`")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Date updatedAt;

	/**setters and getters */
	
	
	public String getCampaginId() {
		return campaginId;
	}

	public void setCampaginId(String campaginId) {
		this.campaginId = campaginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isFollowUp() {
		return followUp;
	}

	public void setFollowUp(boolean followUp) {
		this.followUp = followUp;
	}

	public int getFollowupAfterDays() {
		return FollowupAfterDays;
	}

	public void setFollowupAfterDays(int followupAfterDays) {
		FollowupAfterDays = followupAfterDays;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
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

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	/** constructor with all arguments */
	
	public Campaign(String campaginId, Company companyId, @NotBlank(message = "name can't be blank") String name,
			@NotBlank(message = "title can't be blank") String title,
			@NotBlank(message = "email can't be blank") @Email(message = "invalid email") String email, String delivery,
			Date scheduleDate, boolean followUp, int followupAfterDays, boolean status, Date createdAt,
			Date updatedAt) {
		super();
		this.campaginId = campaginId;
		this.companyId = companyId;
		this.name = name;
		this.title = title;
		this.email = email;
		this.delivery = delivery;
		this.scheduleDate = scheduleDate;
		this.followUp = followUp;
		this.FollowupAfterDays = followupAfterDays;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	/** constructor with no arguments */
	
	public Campaign() {
		super();
	}
	
	

	/** To String method */
	
	@Override
	public String toString() {
		return "Campaign [campaginId=" + campaginId + ", companyId=" + companyId + ", name=" + name + ", title=" + title
				+ ", email=" + email + ", delivery=" + delivery + ", scheduleDate=" + scheduleDate + ", followUp="
				+ followUp + ", FollowupAfterDays=" + FollowupAfterDays + ", status=" + status + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

	
	
	
	
	
	
}
