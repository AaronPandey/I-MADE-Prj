package com.unifyed.attendance.domains;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "IMADE_CORE_STUDENT")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String firstName;
	private String lastName;
	@Indexed(name = "rollNo", unique = true)
	private String rollNo;
	@Indexed(name = "regNo", unique = true)
	private String regNo;
	private String courseId;
	private String specializationId;
	private String semester;

	@CreatedDate
	private Date createDate;

	@LastModifiedDate
	private Date updatedDate;

	@CreatedBy
	private String createdBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(String specializationId) {
		this.specializationId = specializationId;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", rollNo=" + rollNo
				+ ", regNo=" + regNo + ", courseId=" + courseId + ", specializationId=" + specializationId
				+ ", semester=" + semester + ", createDate=" + createDate + ", updatedDate=" + updatedDate
				+ ", createdBy=" + createdBy + ", getId()=" + getId() + ", getFirstName()=" + getFirstName()
				+ ", getLastName()=" + getLastName() + ", getRollNo()=" + getRollNo() + ", getRegNo()=" + getRegNo()
				+ ", getCourseId()=" + getCourseId() + ", getSpecializationId()=" + getSpecializationId()
				+ ", getSemester()=" + getSemester() + ", getCreateDate()=" + getCreateDate() + ", getUpdatedDate()="
				+ getUpdatedDate() + ", getCreatedBy()=" + getCreatedBy() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}