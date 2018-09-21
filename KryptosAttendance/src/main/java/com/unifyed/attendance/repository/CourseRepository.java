package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Course;


@RepositoryRestResource(collectionResourceRel = "course", path = "course")
public interface CourseRepository extends MongoRepository<Course, String> {
	public Page<Course> findAll(Pageable pageable);
	public Course findByCourse(String course);
	public Course findOneByCourse(String courseName);
}
