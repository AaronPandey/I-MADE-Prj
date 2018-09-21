package com.unifyed.attendance.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

	public ResponseEntity<String> saveFacultyDetails(MultipartFile file) {
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new File(UPLOAD_LOCATION + file.getOriginalFilename()));
			System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();

			Course tempCourse = null;

			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Faculty faculty = new Faculty();
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
							String[] courses = cellValue.split(",");
							List<String> courseList = new ArrayList<String>();
							for (String courseName : courses) {
								Course course = courseRepository.findOneByCourse(courseName);

								if (course == null) {
									course = new Course();
									course.setCourse(courseName);
									course.setStatus("active");
									tempCourse = courseRepository.save(course);
									courseList.add(tempCourse.getId());

								} else {
									courseList.add(course.getId());
								}
							}
							faculty.setCourseId(courseList);

							break;
						}
						case 4: {
							String[] specializations = cellValue.split(",");
							List<String> specializationList = new ArrayList<String>();
							for (String specializationName : specializations) {
								Specialization specialization = specializationRepository
										.findBySpecialization(specializationName);

								if (specialization == null) {
									specialization = new Specialization();
									specialization.setSpecialization(specializationName);
									specialization.setStatus("active");
									specialization.setCourse(tempCourse.getId());

									Specialization specializationObj = specializationRepository.save(specialization);
									specializationList.add(specializationObj.getId());

								} else {
									specializationList.add(specialization.getId());
								}
							}
							faculty.setSpecializationId(specializationList);

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

	public String addfacultyPUT(Faculty faculty) {
		facultyRepository.save(faculty);
		return faculty.getId();
	}
	
	public String addfacultyPOST(Map <String,String> faculty) {
		Faculty fclty=new Faculty();
		/*Course sourse= courseRepository.findOne(faculty.get("course").toString());
		Specialization specialization=specializationRepository.findOne(faculty.get("specialization").toString());
		fclty.setFirstName(faculty.get("firstName").toString());
		fclty.setLastName(faculty.get("lastName").toString());
		fclty.setCollege(faculty.get("college").toString());
		fclty.setCourse(sourse.getId());
		fclty.setSpecialization(specialization.getId());
		facultyRepository.save(fclty);*/
		return fclty.getId();
	}

	public List<Faculty> findAllFaculties() {
		return facultyRepository.findAll();
	}

	public Faculty findFacltiesByID(String id) {
		return facultyRepository.findOne(id);
	}

	public List<Faculty> findFacltiesByCourseId(String courseId) {
		List<Faculty> allFacultyList = facultyRepository.findAll();
		List<Faculty> listOfFacultiesByCourse = new ArrayList<>();
		//Course course = courseRepository.findByCourse(courseId);
		for (Faculty faculty : allFacultyList) {
			if (null !=faculty.getCourseId() &&  faculty.getCourseId().contains(courseId)) {
				listOfFacultiesByCourse.add(faculty);
			}
		}
		return allFacultyList;
	}
}