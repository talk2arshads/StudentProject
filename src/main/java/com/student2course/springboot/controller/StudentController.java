package com.student2course.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.student2course.springboot.model.Course;
import com.student2course.springboot.model.Student;
import com.student2course.springboot.service.StudentService;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students/{studentId}/courses")
    public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {

        return this.studentService.retrieveCourses(studentId);
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    public String retrieveDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId) {

        return this.studentService.retrieveCourse(studentId, courseId);
    }

    //@RequestMapping(method = RequestMethod.GET, consumes = "application/json")
    @GetMapping("/students/{studentId}")
    public Student retrieveDetailsOfStudent(@PathVariable String studentId) {

        return this.studentService.retrieveStudent(studentId);
    }
    
    @RequestMapping("/welcome")
    public String welcomePage () {
        ModelAndView modelAndView = new ModelAndView(); // we can return this modeland view as well but need to add some dependency
        modelAndView.setViewName("Welcome to Student Service");
        return "Welcome to Student Service";
    }

    @GetMapping("/allStudents")
    public List<Student> retrieveDetailsOfAllStudents() {

        return this.studentService.retrieveAllStudents();
    }

    @PostMapping("/students/{studentId}/courses")
    public ResponseEntity<Void> registerStudentForCourse(@PathVariable String studentId, @RequestBody Course newCourse) {

        final Course course = this.studentService.addCourse(studentId, newCourse);

        if (course == null) {
            return ResponseEntity.noContent().build();
        }

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
