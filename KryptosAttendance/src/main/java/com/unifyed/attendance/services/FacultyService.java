package com.unifyed.attendance.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unifyed.attendance.domains.Course;
import com.unifyed.attendance.domains.Faculty;
import com.unifyed.attendance.domains.Specialization;
import com.unifyed.attendance.repository.CourseRepository;
import com.unifyed.attendance.repository.FacultyRepository;
import com.unifyed.attendance.repository.SpecializationRepository;

/**
 * Created by Aniket on 18/09/2018.
 */
@Service
public class FacultyService {

	@Autowired
	private FacultyRepository facultyRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private SpecializationRepository specializationRepository;

	// Save the uploaded file to this folder
	private static String UPLOAD_LOCATION = "uploads/";

	public boolean uploadExcel(MultipartFile file) {
		try {
			System.out.println("File Name: " + file.getOriginalFilename());

			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_LOCATION + file.getOriginalFilename());
			Files.write(path, bytes);
			System.out.println("File uploaded successfully !!!");
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("null")
	public ResponseEntity<String> saveFacultyDetails(MultipartFile file) {
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new File(UPLOAD_LOCATION + file.getOriginalFilename()));
			System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			Faculty faculty = new Faculty();
			Course tempCourse = null;
			System.out.println("=====");
			
			Iterator<Row> rowIterator = sheet.rowIterator();
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            System.out.println("row: "+row.getRowNum());
	           
	            Iterator<Cell> cellIterator = row.cellIterator();
	            if (row.getRowNum() > 0) {
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                String cellValue = dataFormatter.formatCellValue(cell);
		                
		                switch (cell.getColumnIndex()) {
							case 0:
								faculty.setFirstName(cellValue);
								break;
							case 1:
								faculty.setLastName(cellValue);
								break;
							case 2:
								faculty.setCollege(cellValue);
								break;
							case 3: {
								Course course = courseRepository.findOneByCourse(cellValue);
								
								if (course == null) {
									course = new Course();
									course.setCourse(cellValue);
									course.setStatus("active");
									tempCourse = courseRepository.save(course);
	
									faculty.setCourse(course);
								} else {
									faculty.setCourse(course);
								}
								
								System.out.println("cellValue===" +course);
								break;
							}
							case 4: {
								Specialization specialization = specializationRepository.findBySpecialization(cellValue);
								if (specialization == null) {
									specialization = new Specialization();
									specialization.setSpecialization(cellValue);
									specialization.setStatus("active");
									specialization.setCourse(tempCourse);
	
									specializationRepository.save(specialization);
									
									faculty.setSpecialization(specialization);
								} else {
									faculty.setSpecialization(specialization);
								}
								break;
							}
						}
		            }
		            facultyRepository.save(faculty);
	            }
	        }

			return ResponseEntity.status(HttpStatus.OK).body("Faculty details saved successfully!!!");
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to save faculty details !!!");
		}
	}
}
