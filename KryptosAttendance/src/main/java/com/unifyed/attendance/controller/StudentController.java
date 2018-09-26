package com.unifyed.attendance.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unifyed.attendance.services.StudentService;

/**
 * Created by Aniket on 24/09/2018.
 */

@RestController
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/uploadStudentDetails")
	public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload");
		}

		boolean status = studentService.uploadExcel(file);

		if (status)
			return studentService.saveStudentDetails(file);
		else
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failed");
	}
	
	@PostMapping("/add/student")
	public ResponseEntity<String> postStudent(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		
		return studentService.addStudent(requestBody);
	}
	
}
