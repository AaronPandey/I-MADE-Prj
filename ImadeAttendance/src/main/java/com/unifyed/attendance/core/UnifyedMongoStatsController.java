package com.unifyed.attendance.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnifyedMongoStatsController {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping("/open/stats")
	Object stats() {
		return mongoTemplate.getDb().getStats();
	}
}
