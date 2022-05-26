package com.companyservice.companyservice.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Entity
public class Company {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)",unique=true)
	@Id
	private String companyId;
	
	@Column(name = "`name`",columnDefinition = "VARCHAR(100)")
	private String name;
	
	@Column(name = "`phone`",columnDefinition = "VARCHAR(15)",unique=true)
	private long phone;
	
	@NotBlank(message = "email can't be blank")
	@Email(message = "invalid email")
	@Column(name = "`email`", columnDefinition = "VARCHAR(100)",unique=true)
	private String email;
	
	@NotBlank(message = "password can't be blank")
	@Column(name = "`password`")
	private String password;
	
	@Column(name = "`status`")
	private boolean status;
	
	@Column(name = "`address`", columnDefinition = "VARCHAR(150)")
	private String address;
	
	@Column(name = "`createdAt`")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Date updatedAt;

	 /**setters and getters */
	 
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	public Company(String companyId, String name, long phone,
			@NotBlank(message = "email can't be blank") @Email(message = "invalid email") String email,
			@NotBlank(message = "password can't be blank") String password, boolean status, String address,
			Date createdAt, Date updatedAt) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.status = status;
		this.address = address;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	/** constructor with no arguments */
	
	public Company() {
		super();
	}
	

	/** To String method */
	
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", phone=" + phone + ", email=" + email
				+ ", password=" + password + ", status=" + status + ", address=" + address + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
}
