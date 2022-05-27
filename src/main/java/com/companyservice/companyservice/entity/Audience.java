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
public class Audience {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)",unique=true)
	@Id
	private String id;
	
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	private Company companyId;
	
	@JoinColumn(name = "campaginId", referencedColumnName = "campaginId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	private Campaign campaginId;
	
	@Column(name = "`name`",columnDefinition = "VARCHAR(100)")
	private String name;
	
	@Column(name = "`phone`",columnDefinition = "VARCHAR(15)")
	private long phone;
	
	@NotBlank(message = "email can't be blank")
	@Email(message = "invalid email")
	@Column(name = "`email`", columnDefinition = "VARCHAR(100)")
	private String email;
	
	@Column(name = "`address`",columnDefinition = "VARCHAR(100)")
	private String address;
	
	@Column(name = "`isLead`")
	private boolean isLead = false;

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

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public Campaign getCampaginId() {
		return campaginId;
	}

	public void setCampaginId(Campaign campaginId) {
		this.campaginId = campaginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isLead() {
		return isLead;
	}

	public void setLead(boolean isLead) {
		this.isLead = isLead;
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
	
	public Audience(String id, Company companyId, Campaign campaginId, String name, long phone,
			@NotBlank(message = "email can't be blank") @Email(message = "invalid email") String email, String address,
			boolean isLead, boolean status, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.campaginId = campaginId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.isLead = isLead;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	/** constructor with no arguments */
	
	public Audience() {
		super();
	}
	
	/** To String method */
	@Override
	public String toString() {
		return "Audience [id=" + id + ", companyId=" + companyId + ", campaginId=" + campaginId + ", name=" + name
				+ ", phone=" + phone + ", email=" + email + ", address=" + address + ", isLead=" + isLead + ", status="
				+ status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
	
}
