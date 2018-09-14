package com.unifyed.attendance.domains;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Aniket on 16/08/2018.
 */
@Document(collection = "UNIFYED_SUBJECT")
public class Subject {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	 @Id
	 private String id;
	 
	 private String subject;
	 
	 @Indexed(name="subject_code", unique=true)
	 private String subject_code;
	 
	 private String status;
	 
	 @CreatedDate
	 private Date createdDate = new Date();

	 @LastModifiedDate
	 private Date updatedDate = new Date();

	 @CreatedBy
	 private String createdBy;
	 
	 public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Subject [id=" + id + ", subject=" + subject + ", subject_code=" + subject_code + ", status=" + status
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy
				+ ", getId()=" + getId() + ", getSubject()=" + getSubject() + ", getSubject_code()=" + getSubject_code()
				+ ", getStatus()=" + getStatus() + ", getCreatedDate()=" + getCreatedDate() + ", getUpdatedDate()="
				+ getUpdatedDate() + ", getCreatedBy()=" + getCreatedBy() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	 
}
