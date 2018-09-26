package com.unifyed.attendance.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unifyed.attendance.domains.Course;
import com.unifyed.attendance.domains.Room;
import com.unifyed.attendance.domains.Semester;
import com.unifyed.attendance.domains.Specialization;
import com.unifyed.attendance.domains.Subject;
import com.unifyed.attendance.services.AdminService;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping("/page")
	public String hellopage() {
		return "hello";
	}
	
	@ResponseBody
	@RequestMapping("/admin/add/course")
	public String postCourse(@RequestBody Course course, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.postCourse(course);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/add/course", method = RequestMethod.PUT)
	public String putCourse(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.putCourse(requestBody);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/courselist", method = RequestMethod.GET)
	public List<Course> getCourseList(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getAllCourses();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/course/{id}", method = RequestMethod.GET)
	public Course getCourseById(@PathVariable("id") String courseId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getCoursebyID(courseId);
	}

	@ResponseBody
	@RequestMapping("/admin/add/specialization")
	public String postSpecialization(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.postSpecialization(requestBody);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/specializationlist", method = RequestMethod.GET)
	public List<Specialization> getSpecializations(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getAllSpecialization();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/add/specialization/{id}", method = RequestMethod.GET)
	public Specialization getSpecializationbyId(@PathVariable("id") String specId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getSpecbyID(specId);
	}

	@ResponseBody
	@RequestMapping("/admin/add/semester")
	public String postSemester(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.postSemester(requestBody);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/semesterlist", method = RequestMethod.GET)
	public List<Semester> getSemesterList(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.findAllSemesters();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/add/semester/{id}", method = RequestMethod.GET)
	public Semester getSemesterById(@PathVariable("id") String semId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getSembyId(semId);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/add/semester", method = RequestMethod.PUT)
	public String putSemester(@RequestBody Map<String, Object> requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.putSemester(requestBody);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/add/room", method = RequestMethod.POST)
	public Room postRoom(@RequestBody Room room, @RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.postRoom(room);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/roomlist", method = RequestMethod.GET)
	public List<Room> getRoom(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.findAllRooms();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/room/{roomId}", method = RequestMethod.GET)
	public Room postRoom(@PathVariable("roomId") String roomId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getRoombyId(roomId);
	}

	@ResponseBody
	@RequestMapping(value="/admin/add/subject",method=RequestMethod.PUT)
	public Subject putSubject(@RequestBody Subject requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) throws JSONException {
		return adminService.postSubject(requestBody);
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/add/subject",method=RequestMethod.POST)
	public Subject postSubject(@RequestBody Map<String, Object>  requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) throws JSONException {
		return adminService.postSubjectBody(requestBody);
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/subjectlist", method = RequestMethod.GET)
	public List<Subject> getsubjectList(@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.findAllSubjects();
	}

	@ResponseBody
	@RequestMapping(value = "/admin/get/subject/{subId}", method = RequestMethod.GET)
	public Subject getSubjectById(@PathVariable("subId") String subId,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getSubById(subId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/get/specializationsByCourse", method = RequestMethod.POST)
	public List<Specialization> getSpecializationsByCourse(@RequestBody Map<String, Object>  requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getSpecializationsByCourseId(requestBody);
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/get/semestersBySpecId", method = RequestMethod.POST)
	public List<Semester> getSemestersBySpecializationId(@RequestBody Map<String, Object>  requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getSemestersBySpecId(requestBody);
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/get/subjectsBySemesterAndSpecId", method = RequestMethod.POST)
	public List<Subject> getSubjectsBySemesterAndSpecialization(@RequestBody Map<String, Object>  requestBody,
			@RequestHeader(value = "principal-user") String principalUser,
			@RequestHeader(value = "X-TENANT-ID") String tenantId) {
		return adminService.getSubjectsBySemesterAndSpec(requestBody);
	}

}
