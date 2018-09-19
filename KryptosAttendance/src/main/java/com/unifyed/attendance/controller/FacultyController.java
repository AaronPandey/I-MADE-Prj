package com.unifyed.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unifyed.attendance.services.FacultyService;

/**
 * Created by Aniket on 17/09/2018.
 */
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
        
        if(status)
        	return facultyService.saveFacultyDetails(file);

        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failed");
    }
}
