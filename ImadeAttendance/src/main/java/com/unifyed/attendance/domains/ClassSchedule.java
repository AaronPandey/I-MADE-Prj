package com.unifyed.attendance.domains;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "IMADE_CORE_CLASS_SCHEDULE")
public class ClassSchedule implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String className;
	
	private String status;
	
	private String faculty;
	private String course;
	private String semester;
	private String section;
	private String subject;
	private String specialization;
	
	private String room;
    
	private Date startDateAndTime;
	private Date endDateAndTime;
	
	@CreatedDate
	private Date createdDate = new Date();

	@LastModifiedDate
	private Date updatedDate = new Date();

	@CreatedBy
	private String createdBy;

		
	public Date getStartDateAndTime() {
		return startDateAndTime;
	}

	public void setStartDateAndTime(Date startDateAndTime) {
		this.startDateAndTime = startDateAndTime;
	}

	public Date getEndDateAndTime() {
		return endDateAndTime;
	}

	public void setEndDateAndTime(Date endDateAndTime) {
		this.endDateAndTime = endDateAndTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSpecialisation() {
		return specialization;
	}

	public void setSpecialisation(String specialisation) {
		this.specialization = specialisation;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
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
		return "ClassSchedule [id=" + id + ", className=" + className + ", status=" + status + ", faculty=" + faculty
				+ ", course=" + course + ", semester=" + semester + ", section=" + section + ", subject=" + subject
				+ ", specialisation=" + specialization + ", room=" + room + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + "]";
	}

	
	
	
	
}
