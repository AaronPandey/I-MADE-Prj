package com.unifyed.attendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unifyed.attendance.domains.ClassSchedule;
import com.unifyed.attendance.services.ClassService;

@RestController
public class ClassController {

	@Autowired
	private ClassService classService;


	@ResponseBody
	@RequestMapping(value="/admin/add/classschedule", method=RequestMethod.PUT)
	public String postClassSchedule(@RequestBody ClassSchedule classSchedule, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return classService.postClassSchedule(classSchedule);
	}

	@ResponseBody
	@RequestMapping(value="/admin/get/classschedule/{id}",method=RequestMethod.GET)
	public ClassSchedule getClassSchedulebyId(@PathVariable("id") String id, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return classService.getClassSchedulebyId(id);
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/add/classschedulelist",method=RequestMethod.GET)
	public List<ClassSchedule> getClassScheduleList(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return classService.listAllClassSchedule();
	}
}
