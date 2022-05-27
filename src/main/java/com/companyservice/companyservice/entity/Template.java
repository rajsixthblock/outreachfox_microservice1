package com.companyservice.companyservice.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import net.minidev.json.JSONObject;

@Entity
@Table
public class Template {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)", unique=true)
	@Id
	private String templateId;
	
	@NotBlank(message = "name can't be blank")
	@Column(name = "`name`",columnDefinition = "VARCHAR(100)", unique=true)
	private String name;
	
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

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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
	
	/** constructor with all arguments */
	
	public Template(String templateId, @NotBlank(message = "name can't be blank") String name, JSONObject templateData,
			boolean status, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.templateId = templateId;
		this.name = name;
		this.templateData = templateData;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	/** constructor with no arguments */
	
	public Template() {
		super();
	}
	
	/** To String method */
	@Override
	public String toString() {
		return "Template [templateId=" + templateId + ", name=" + name + ", templateData=" + templateData + ", status="
				+ status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	 
}
