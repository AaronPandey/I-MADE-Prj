package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Course;
import com.unifyed.attendance.domains.Semester;
import com.unifyed.attendance.domains.Specialization;


/**
 * Created by Aniket on 16/08/2018.
 */
@RepositoryRestResource(collectionResourceRel = "semester", path = "semester")
public interface SemesterRepository extends MongoRepository<Semester, String> {
	public Page<Semester> findAll(Pageable pageable);
	public Semester findByCourseAndSpecialization(Course course, Specialization specialization);
}
