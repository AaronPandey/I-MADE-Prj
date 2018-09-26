package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Student;

@RepositoryRestResource(collectionResourceRel = "student_details", path = "student_details")
public interface StudentRepository extends MongoRepository<Student, String>  {
	public Page<Student> findAll(Pageable pageable);
	public Student[] findAllByCourseIdAndSpecializationIdAndSemester(String course, String specialization, String semester);
}
