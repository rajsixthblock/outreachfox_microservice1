package com.OutReachCompany.OutReachCompanyDetails.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Table
@Entity
public class Schedule {
	
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "CHAR(32)",unique=true)
	@Id
	private String scheduleId;
	
	@NotBlank(message = "Time Zone cannot be blank")
	@Column(name = "`timeZone`",columnDefinition = "VARCHAR(30)", unique=true)
	private String timeZone;
	
	@NotNull(message = "Time cannot be blank")
	@DateTimeFormat(style = "HH:mm:ss", pattern="HH:mm:ss")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
	@Column(name = "`time`")
	private Date time;
	
	@Column(name = "`status`")
	private boolean status;
	
	@CreationTimestamp
	@Column(name = "`createdAt`")
	private java.sql.Timestamp createdAt;
	
	@UpdateTimestamp
	@Column(name = "`updatedAt`")
	private java.sql.Timestamp updatedAt;
	

	 /**setters and getters */
	 
	public String getScheduleId() {
		return scheduleId;
	}


	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}


	public String getTimeZone() {
		return timeZone;
	}


	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
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
	
	public Schedule(String scheduleId, @NotBlank(message = "Time Zone cannot be blank") String timeZone,
			Date time, boolean status, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.scheduleId = scheduleId;
		this.timeZone = timeZone;
		this.time = time;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	/** constructor with no arguments */
	
	public Schedule() {
		super();
	}

	/** To String method */

	@Override
	public String toString() {
		return "ScheduleTimeZone [scheduleId=" + scheduleId + ", timeZone=" + timeZone + ", time=" + time + ", status="
				+ status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	
}
