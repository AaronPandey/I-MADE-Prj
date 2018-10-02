package com.unifyed.attendance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.unifyed.attendance.domains.Room;
/**
 * Created by Aniket on 16/08/2018.
 */
@RepositoryRestResource(collectionResourceRel = "room", path = "room")
public interface RoomRepository extends MongoRepository<Room, String> {
	public Page<Room> findAll(Pageable pageable);
}