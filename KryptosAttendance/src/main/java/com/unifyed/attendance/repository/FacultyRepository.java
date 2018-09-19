package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Faculty;

/**
 * Created by Aniket on 18/09/2018.
 */
@RepositoryRestResource(collectionResourceRel = "faculty", path = "faculty")
public interface FacultyRepository extends MongoRepository<Faculty, String> {
	public Page<Faculty> findAll(Pageable pageable);
}
