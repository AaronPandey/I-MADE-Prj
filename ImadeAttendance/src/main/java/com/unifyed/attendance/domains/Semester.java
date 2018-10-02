package com.unifyed.attendance.domains;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Aniket on 16/08/2018.
 */
@Document(collection = "IMADE_CORE_SEMESTER")
public class Semester implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String semester;
	private String course;
	private String status;
	private String specialization;

	@CreatedDate
	private Date createdDate = new Date();

	@LastModifiedDate
	private Date updatedDate = new Date();

	@CreatedBy
	private String createdBy;

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
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
	
	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@Override
	public String toString() {
		return "Semester [id=" + id + ", semester=" + semester + ", course=" + course + ", status=" + status
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", createdBy=" + createdBy
				+ ", getCourse()=" + getCourse() + ", getId()=" + getId() + ", getSemester()=" + getSemester()
				+ ", getStatus()=" + getStatus() + ", getCreatedDate()=" + getCreatedDate() + ", getUpdatedDate()="
				+ getUpdatedDate() + ", getCreatedBy()=" + getCreatedBy() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
