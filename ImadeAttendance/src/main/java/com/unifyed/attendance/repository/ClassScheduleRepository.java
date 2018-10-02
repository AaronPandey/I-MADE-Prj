package com.unifyed.attendance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.ClassSchedule;

@RepositoryRestResource(collectionResourceRel="classSchedule",path="classSchedule")
public interface ClassScheduleRepository extends MongoRepository<ClassSchedule, String> {

}
