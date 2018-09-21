package com.unifyed.attendance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.FacultyRoaster;

@RepositoryRestResource(collectionResourceRel = "facultyRoaster", path = "facultyRoaster")
public interface FacultyRoasterRepository extends MongoRepository<FacultyRoaster, String> {
}
