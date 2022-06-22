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
@Table(name="images")
@Entity
public class Images {
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)",unique=true)
	@Id
	private String imageId;
	
	@JoinColumn(name = "companyId", referencedColumnName = "companyId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"subscriptionId","password","status","address","createdAt","updatedAt"})
	private Company companyId;
	
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonIgnoreProperties({"companyId","password","status","createdAt","updatedAt"}) 
	private User userId;
	
	@NotBlank(message = "imageName can't be blank")
	@Column(name = "`imageName`",columnDefinition = "VARCHAR(100)",unique=true)
	private String imageName;
	
	@NotBlank(message = "originalName can't be blank")
	@Column(name = "`originalName`",columnDefinition = "VARCHAR(100)")
	private String originalName;
	
	@Column(name = "`type`", columnDefinition = "VARCHAR(100)")
	private String type;
	
	@NotBlank(message = "path can't be blank")
	@Column(name = "`path`")
	private String path;
	
	@Column(name = "`createdAt`")
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name = "updatedAt")
	@UpdateTimestamp
	private Date updatedAt;

	
	/**setters and getters */
	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
	
	public Images(String imageId, Company companyId, User userId,
			@NotBlank(message = "imageName can't be blank") String imageName,
			@NotBlank(message = "originalName can't be blank") String originalName, String type,
			@NotBlank(message = "path can't be blank") String path, Date createdAt, Date updatedAt) {
		super();
		this.imageId = imageId;
		this.companyId = companyId;
		this.userId = userId;
		this.imageName = imageName;
		this.originalName = originalName;
		this.type = type;
		this.path = path;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	/** constructor with no arguments */
	
	public Images() {
		super();
	}

	/** To String method */
	
	@Override
	public String toString() {
		return "Images [imageId=" + imageId + ", companyId=" + companyId + ", userId=" + userId + ", imageName="
				+ imageName + ", originalName=" + originalName + ", type=" + type + ", path=" + path + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
