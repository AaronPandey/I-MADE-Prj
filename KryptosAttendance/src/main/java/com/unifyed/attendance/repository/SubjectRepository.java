package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Subject;

/**
 * Created by Aniket on 16/08/2018.
 */
@RepositoryRestResource(collectionResourceRel = "semester", path = "semester")
public interface SubjectRepository extends MongoRepository<Subject, String> {
	public Page<Subject> findAll(Pageable pageable);
}
