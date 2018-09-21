package com.unifyed.attendance.domains;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UNIFYED_FACULTY_ROASTER")
public class FacultyRoaster {

	@Id
	private String id;
	
	private String facultyId;
	private Date roasterDate;	
	private String roasterData;		

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

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	public Date getRoasterDate() {
		return roasterDate;
	}

	public void setRoasterDate(Date roasterDate) {
		this.roasterDate = roasterDate;
	}

	public String getRoasterData() {
		return roasterData;
	}

	public void setRoasterData(String roasterData) {
		this.roasterData = roasterData;
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

	@Override
	public String toString() {
		return "FacultyRoaster [id=" + id + ", facultyId=" + facultyId + ", roasterDate=" + roasterDate
				+ ", roasterData=" + roasterData + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", createdBy=" + createdBy + "]";
	}
	
	
	
	
}

