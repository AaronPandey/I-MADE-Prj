package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Specialization;

/**
 * Created by Aniket on 16/08/2018.
 */

@RepositoryRestResource(collectionResourceRel = "specialization", path = "specialization")
public interface SpecializationRepository extends MongoRepository<Specialization, String> {
	public Page<Specialization> findAll(Pageable pageable);
	public Specialization findBySpecialization(String specialization);
}

