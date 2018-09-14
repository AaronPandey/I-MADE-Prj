package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Course;
/**
 * Created by Aniket on 16/08/2018.
 */
@RepositoryRestResource(collectionResourceRel = "course", path = "course")
public interface CourseRepository extends MongoRepository<Course, String> {
	public Page<Course> findAll(Pageable pageable);
	public Course findByCourse(String course);
}
