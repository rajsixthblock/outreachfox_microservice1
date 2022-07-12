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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Table(name="adminusers")
@Entity
public class AdminUser {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)",unique=true)
	@Id
	private String adminId;
	
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
	private boolean status=false;
	
	@Column(name = "`createdAt`")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Date updatedAt;

	/**setters and getters */
	
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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

	public AdminUser(String adminId, String name, long phone,
			@NotBlank(message = "email can't be blank") @Email(message = "invalid email") String email,
			@NotBlank(message = "password can't be blank") String password, boolean status, Date createdAt,
			Date updatedAt) {
		super();
		this.adminId = adminId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public AdminUser() {
		super();
	}

	@Override
	public String toString() {
		return "AdminUser [adminId=" + adminId + ", name=" + name + ", phone=" + phone + ", email=" + email
				+ ", password=" + password + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
	 

	
}
