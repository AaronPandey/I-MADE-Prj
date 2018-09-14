package com.unifyed.attendance.controller;

import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unifyed.attendance.domains.Course;
import com.unifyed.attendance.domains.Room;
import com.unifyed.attendance.domains.Subject;
import com.unifyed.attendance.services.AttendanceService;

/**
 * Created by Aniket on 16/08/2018.
 */
@RestController
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@RequestMapping("/course")
	public String postCourse(@RequestBody Course course, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return attendanceService.postCourse(course);
	}
	
	@RequestMapping(value = "/course", method = RequestMethod.PUT)
	public String putCourse(@RequestBody Map<String, Object> requestBody, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return attendanceService.putCourse(requestBody);
	}
	
	@RequestMapping("/specialization")
	public String postSpecialization(@RequestBody Map<String, Object> requestBody, 
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return attendanceService.postSpecialization(requestBody);
	}

	@RequestMapping("/semester")
	public String postSemester(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return attendanceService.postSemester(requestBody);
	}

	@RequestMapping(value = "/semester", method = RequestMethod.PUT)
	public String putSemester(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return attendanceService.putSemester(requestBody);
	}

	@RequestMapping("/room")
	public Room postRoom(@RequestBody Room room, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return attendanceService.postRoom(room);
	}

	@RequestMapping("/subject")
	public Subject postSubject(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) throws JSONException {
		return attendanceService.postSubject(requestBody);
	}
	
}
