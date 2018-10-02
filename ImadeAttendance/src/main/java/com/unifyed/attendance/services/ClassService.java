package com.unifyed.attendance.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unifyed.attendance.domains.ClassSchedule;
import com.unifyed.attendance.repository.ClassScheduleRepository;
import com.unifyed.attendance.repository.FacultyRoasterRepository;

@Service
public class ClassService {

	@Autowired
	ClassScheduleRepository classScheduleRepository;
	
	@Autowired
	FacultyRoasterRepository facultyRoasterRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ClassService.class);
	
	public String postClassSchedule(ClassSchedule classSchedule) {
		logger.debug(classSchedule.toString());
		classScheduleRepository.save(classSchedule);
		return classSchedule.getId();
	}

	public ClassSchedule getClassSchedulebyId(String id) {
		return classScheduleRepository.findOne(id);
	}

	public List<ClassSchedule> listAllClassSchedule() {
		return classScheduleRepository.findAll();
	}
	
	
	
	

}
