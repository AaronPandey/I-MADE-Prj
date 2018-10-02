package com.unifyed.attendance.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unifyed.attendance.domains.Faculty;
import com.unifyed.attendance.domains.Semester;
import com.unifyed.attendance.domains.Specialization;
import com.unifyed.attendance.domains.Subject;
import com.unifyed.attendance.services.AdminService;
import com.unifyed.attendance.services.FacultyService;
import com.unifyed.attendance.util.Util;

@RestController
public class FacultyController {

	@Autowired
	private FacultyService facultyService;

	@Autowired
	private AdminService adminService;
	private static final Logger logger = LoggerFactory.getLogger(FacultyController.class);

	@ResponseBody
	@RequestMapping(value = "/admin/add/faculty", method = RequestMethod.PUT)
	public String addFaculty(@RequestBody Faculty faculty,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		logger.debug("Faculty Detials : " + faculty.toString());
		return facultyService.addfacultyPUT(faculty);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/facultyList", method = RequestMethod.GET)
	public List<Faculty> getFacultyList(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return facultyService.findAllFaculties();
	}

	@ResponseBody
	@RequestMapping("/admin/get/facultybyid/{id}")
	public Faculty getFacultyById(@PathVariable("id") String id,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return facultyService.findFacltiesByID(id);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/facultybycourse", method = RequestMethod.POST)
	public List<Faculty> getFacultyByCourse(@RequestBody String courseId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		Map<String, String> params = Util.getParamsAsMap(courseId);
		return facultyService.findFacltiesByCourseId(params.get("courseId"));
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/specilizationbycourse", method = RequestMethod.POST)
	public List<Specialization> getSpecilizationByCourse(@RequestBody String courseId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		Map<String, String> params = Util.getParamsAsMap(courseId);
		return adminService.getSpecByCourse(params.get("courseId").toString());
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/semesterbyspecilization", method = RequestMethod.POST)
	public List<Semester> getSemesterBySecilization(@RequestBody String specilizationId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		Map<String, String> params = Util.getParamsAsMap(specilizationId);
		return adminService.getSemesterBySpecilization(params.get("specilizationId").toString());
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/subjectbysemester", method = RequestMethod.POST)
	public List<Subject> getSubjectBySemester(@RequestBody String semesterId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		Map<String, String> params = Util.getParamsAsMap(semesterId);
		return adminService.getSubjectBySemester(params.get("semesterId").toString());
	}
}