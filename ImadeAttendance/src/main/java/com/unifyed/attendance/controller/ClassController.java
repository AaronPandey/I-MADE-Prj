package com.unifyed.attendance.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
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

import com.unifyed.attendance.domains.ClassSchedule;
import com.unifyed.attendance.domains.Course;
import com.unifyed.attendance.domains.Faculty;
import com.unifyed.attendance.domains.Room;
import com.unifyed.attendance.domains.Semester;
import com.unifyed.attendance.domains.Specialization;
import com.unifyed.attendance.domains.Subject;
import com.unifyed.attendance.services.AdminService;
import com.unifyed.attendance.services.ClassService;
import com.unifyed.attendance.services.FacultyService;
import com.unifyed.attendance.util.Util;

@RestController
public class ClassController {

	@Autowired
	private ClassService classService;

	@Autowired
	private FacultyService facultyService;

	@Autowired
	private AdminService adminService;
	private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

	@ResponseBody
	@RequestMapping(value = "/admin/add/classschedule", method = RequestMethod.POST)
	public String postClassSchedule(@RequestBody ClassSchedule classSchedule,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		logger.debug("Class Schedule details :" + classSchedule.toString());
		return classService.postClassSchedule(classSchedule);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/classschedule/{id}", method = RequestMethod.GET)
	public String getClassSchedulebyId(@PathVariable("id") String id,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {

		JSONObject classInformation = new JSONObject();
		ClassSchedule cs = classService.getClassSchedulebyId(id);
		try {
			Faculty faculty = facultyService.findFacltiesByID(cs.getFaculty());
			logger.debug("Faculty infromation : " + faculty.toString());
			Course course = adminService.getCoursebyID(cs.getCourse());
			logger.debug("Course Information :" + course.toString());
			Specialization spec = adminService.getSpecbyID(cs.getSpecialisation());
			logger.debug("Specilization information :" + spec.toString());
			Semester semester = adminService.getSembyId(cs.getSemester());
			logger.debug("Semester info :" + semester.toString());
			Subject subject = adminService.getSubById(cs.getSubject());
			logger.debug("Subject information :" + subject.toString());
			Room room = adminService.getRoombyId(cs.getRoom());
			logger.debug("Room Info :" + room.toString());
			classInformation.put("className", cs.getClassName());
			classInformation.put("facultyName", faculty.getFirstName() + " " + faculty.getLastName());
			classInformation.put("classStartTime", cs.getStartDateAndTime());
			classInformation.put("classStartTime", cs.getEndDateAndTime());
			classInformation.put("courseName", course.getCourse());
			classInformation.put("specilization", spec.getSpecialization());
			classInformation.put("semester", semester.getSemester());
			classInformation.put("subject", subject.getSubject());
			classInformation.put("subjectCode", subject.getSubject_code());
			classInformation.put("roomInfo", room.getRoom_no());
		} catch (Exception e) {
			logger.debug("Class Schedule retrival Exception :" + e);
			e.printStackTrace();
		}
		logger.debug("Class Information :"+classInformation.toString());
		return classInformation.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/classschedulelist", method = RequestMethod.GET)
	public List<ClassSchedule> getClassScheduleList(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return classService.listAllClassSchedule();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/specializationbycourse", method = RequestMethod.POST)
	public List<Specialization> getFacultyByCourse(@RequestBody String courseId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		Map<String, String> params = Util.getParamsAsMap(courseId);
		return adminService.getSpecByCourse(params.get("courseId").toString());
	}

}
