package com.companyservice.companyservice.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.minidev.json.JSONObject;

@Entity
@Table
public class Template {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique=true)
	@Id
	private String templateId;
	
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"subscriptionId","password","status","address","createdAt","updatedAt"})
	private Company companyId;
	
	@JoinColumn(name = "adminId", referencedColumnName = "adminId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"password","status","address","createdAt","updatedAt"})
	private AdminUser adminId;
	
	@NotBlank(message = "name can't be blank")
	@Column(name = "`name`",columnDefinition = "VARCHAR(100)", unique=true)
	private String name;
	
	@Lob
	@Column(name = "`templateData`")
	private JSONObject templateData;
	
	@Column(name = "`status`")
	private boolean status;

	@CreationTimestamp
	@Column(name = "`createdAt`")
	private java.sql.Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name = "`updatedAt`")
	private java.sql.Timestamp updatedAt;

	/**setters and getters */
	
	public String getTemplateId() {
		return templateId;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public AdminUser getAdminId() {
		return adminId;
	}

	public void setAdminId(AdminUser adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getTemplateData() {
		return templateData;
	}

	public void setTemplateData(JSONObject templateData) {
		this.templateData = templateData;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public java.sql.Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(java.sql.Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	/** constructor with no arguments */
	
	public Template() {
		super();
	}
	
	@Override
	public String toString() {
		return "AdminTemplate [templateId=" + templateId + ", companyId=" + companyId + ", adminId=" + adminId
				+ ", name=" + name + ", templateData=" + templateData + ", status=" + status + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	/** constructor with all arguments */
	public Template(String templateId, Company companyId, AdminUser adminId,
			@NotBlank(message = "name can't be blank") String name, JSONObject templateData, boolean status,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.templateId = templateId;
		this.companyId = companyId;
		this.adminId = adminId;
		this.name = name;
		this.templateData = templateData;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
}
