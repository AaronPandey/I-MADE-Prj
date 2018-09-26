package com.unifyed.attendance.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unifyed.attendance.domains.Course;
import com.unifyed.attendance.domains.Specialization;
import com.unifyed.attendance.domains.Student;
import com.unifyed.attendance.repository.CourseRepository;
import com.unifyed.attendance.repository.SpecializationRepository;
import com.unifyed.attendance.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private SpecializationRepository specializationRepository;
	
	@Autowired
	 MongoOperations mongoOperations;
	
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

	public ResponseEntity<String> saveStudentDetails(MultipartFile file) {
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
				Student student = new Student();

				Iterator<Cell> cellIterator = row.cellIterator();
				if (row.getRowNum() > 0) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						switch (cell.getColumnIndex()) {
							case 0:
								student.setFirstName(cellValue);
								break;
							case 1:
								student.setLastName(cellValue);
								break;
							case 2:
								student.setRollNo(cellValue);
								break;
							case 3: {
								student.setRegNo(cellValue);
								break;
							}
							case 4: {
								String courseName = cellValue;
								Course course = courseRepository.findOneByCourse(courseName);
								if (course == null) {
									course = new Course();
									course.setCourse(courseName);
									course.setStatus("active");
									tempCourse = courseRepository.save(course);
									student.setCourseId(tempCourse.getId());
	
								} else {
									student.setCourseId(tempCourse.getId());
								}
								break;
							}
							case 5: {
								String specializationName = cellValue;
								
								Specialization specialization = specializationRepository
										.findBySpecialization(specializationName);
	
								if (specialization == null) {
									specialization = new Specialization();
									specialization.setSpecialization(specializationName);
									specialization.setStatus("active");
									specialization.setCourse(tempCourse.getId());
	
									Specialization specializationObj = specializationRepository.save(specialization);
									student.setSpecializationId(specializationObj.getId());
	
								} else {
									student.setSpecializationId(specialization.getId());
								}
								break;
							}
							case 6: {
								student.setSemester(cellValue);
								break;
							}
						}
					}
					studentRepository.save(student);
				}
			}

			return ResponseEntity.status(HttpStatus.OK).body("Faculty details saved successfully!!!");
		} catch (EncryptedDocumentException | InvalidFormatException |

				IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to save faculty details !!!");
		}
	}

	public ResponseEntity<List<Student>> getStudentListForAttendance(String course, String specialization, String semester){
		Course courseObj = courseRepository.findByCourse(course);
		Specialization specializationObj = specializationRepository.findBySpecialization(specialization);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("courseId").is(courseObj.getId()).and("specializationId").is(specializationObj.getId())
				.and("semester").is(semester));
		
		List<Student> studentList = mongoOperations.find(query, Student.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(studentList);
	}
	
	@SuppressWarnings("null")
	public ResponseEntity<String> addStudent(Map<String, Object> requestBody) {
		Student student = new Student();

		student.setFirstName(requestBody.get("firstName").toString());
		student.setLastName(requestBody.get("lastName").toString());
		student.setRollNo(requestBody.get("rollNo").toString());
		student.setRegNo(requestBody.get("regNo").toString());
		
		Course tempCourse = null;
		String courseName = requestBody.get("course").toString();
		Course course = courseRepository.findOneByCourse(courseName);
		if (course == null) {
			course = new Course();
			course.setCourse(courseName);
			course.setStatus("active");
			tempCourse = courseRepository.save(course);
			student.setCourseId(tempCourse.getId());

		} else {
			student.setCourseId(tempCourse.getId());
		}

		String specializationName = requestBody.get("specialization").toString();;
		Specialization specialization = specializationRepository.findBySpecialization(specializationName);

		if (specialization == null) {
			specialization = new Specialization();
			specialization.setSpecialization(specializationName);
			specialization.setStatus("active");
			specialization.setCourse(tempCourse.getId());

			Specialization specializationObj = specializationRepository.save(specialization);
			student.setSpecializationId(specializationObj.getId());

		} else {
			student.setSpecializationId(specialization.getId());
		}

		student.setSemester(requestBody.get("semester").toString());
		
		studentRepository.save(student);
		
		return ResponseEntity.status(HttpStatus.OK).body("Student details saved sucessfully !!!");
	}
}
