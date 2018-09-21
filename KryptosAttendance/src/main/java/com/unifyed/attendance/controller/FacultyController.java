package com.unifyed.attendance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unifyed.attendance.domains.Faculty;
import com.unifyed.attendance.services.FacultyService;
import com.unifyed.attendance.util.Util;

@RestController
public class FacultyController {

	@Autowired
	private FacultyService facultyService;

	@PostMapping("/uploadFacultyDetails")
	public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
		}

		boolean status = facultyService.uploadExcel(file);

		if (status)
			return facultyService.saveFacultyDetails(file);

		return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failed");
	}

	@ResponseBody
	@RequestMapping(value = "/admin/add/faculty", method = RequestMethod.PUT)
	public String addFaculty(@RequestBody Faculty faculty,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return facultyService.addfacultyPUT(faculty);
	}

	
	@ResponseBody
	@RequestMapping(value = "/admin/add/faculty", method = RequestMethod.POST)
	public String postFaculty(@RequestBody Map<String,String> faculty,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return facultyService.addfacultyPOST(faculty);
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
		Map<String, String> params=Util.getParamsAsMap(courseId);
		return facultyService.findFacltiesByCourseId(params.get("courseId"));
	}

}