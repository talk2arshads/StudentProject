package com.student2course.springboot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.student2course.springboot.controller.StudentController;
import com.student2course.springboot.model.Course;
import com.student2course.springboot.service.StudentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    Course mockCourse = new Course("Course1", "Spring", "10Steps",
            Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));

    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

    @Test
    public void retrieveDetailsForCourse() throws Exception {

        Mockito.when(this.studentService.retrieveCourse(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(this.mockCourse.toString());

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students/Student1/courses/Course1")
                .accept(MediaType.APPLICATION_XML_VALUE);

        final MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        System.out.println("Actually the Output result is : " + result.getResponse().getContentAsString());
        final String expected = "Course [id=Course1, name=Spring, description=10Steps";

        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import
        // Project","First Example","Second Example"]}

        Assert.assertNotEquals(expected.toString(), result.getResponse().getContentAsString());
        
        final List<String> strList = new ArrayList<>();
        final int[] intArray = new int[3];
        final Map<String, Integer> objMap = new HashMap<>();
        objMap.put("Key:2", 85);
        objMap.put("Key:4", 52);
        objMap.put("Key:3", 41);
        objMap.put("Key:1", 22);
        objMap.put("Key:2", 22);
        final Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("3", 55);
        linkedMap.put("1", 34);
        linkedMap.put("4", 67);
        linkedMap.put("2", 77);
        System.out.println(linkedMap);
        final Set newSet = objMap.entrySet();
        System.out.println(newSet);
        System.out.println(objMap);
        /*
         * final Map<String, Integer> sorted =
         * objMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()) .collect(Entry::getKey,
         * Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
         */
        System.out.println(objMap.values());
        System.out.println(objMap.keySet());

       
    }

    @Test
    public void createStudentCourse() throws Exception {

        final Course mockCourse = new Course("1", "Smallest Number", "1", Arrays.asList("1", "2", "3", "4"));

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(this.studentService.addCourse(ArgumentMatchers.anyString(), ArgumentMatchers.any(Course.class)))
                .thenReturn(mockCourse);

        // Send course as body to /students/Student1/courses
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students/Student1/courses")
                .accept(MediaType.APPLICATION_JSON).content(this.exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

        final MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        final MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        Assert.assertEquals("http://localhost/students/Student1/courses/1", response.getHeader(HttpHeaders.LOCATION));

    }

}
