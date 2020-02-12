<!---
Current Directory : /in28Minutes/git/spring-boot-examples/spring-boot-rest-services-with-unit-and-integration-tests
-->
sample spring boot project with test cases and URL

http://localhost:8080/welcome
http://localhost:8080/students/Student1/courses/Course1
http://localhost:8080/students/Student1/courses/Course2
http://localhost:8080/allStudents
http://localhost:8080/Welcome.html

INNER JOIN gets all records that are common between both tables based on the supplied ON clause.

LEFT JOIN gets all records from the LEFT linked and the related record from the right table ,but if you have selected some columns from the RIGHT table, if there is no related records, these columns will contain NULL.

RIGHT JOIN is like the above but gets all records in the RIGHT table.

FULL JOIN gets all records from both tables and puts NULL in the columns where related records do not exist in the opposite table.

Oracle SQL queries;
	select * from UITAX.ui_defaults where seq_code = 'DOCUMENT_NAME' and seq_short_desc = '55-0307';
	select ENTITY_NA from UITAX.UI_Employer where STATUS_CD between 2710 and 2712;
	select count(*) from UITAX.UI_Employer order by create_dt desc;
	select ENTITY_NA, count(*) from UITAX.UI_Employer group by ENTITY_NA having count(*) > 1 order by ENTITY_NA asc;
	select  * from (select status_cd,ENTITY_NA, empr_acct_id from UITAX.UI_Employer order by empr_acct_id desc) where rownum < 5;
	select max(empr_acct_id) from UITAX.UI_Employer;
	
	SELECT bc.firstname,
       bc.lastname,
       b.title,
       TO_CHAR(bo.orderdate, 'MM/DD/YYYY') "Order Date",
       p.publishername
FROM BOOK_CUSTOMER bc
INNER JOIN books b
  ON b.BOOK_ID = bc.BOOK_ID
INNER JOIN  book_order bo
  ON bo.BOOK_ID = b.BOOK_ID
INNER JOIN publisher p
  ON p.PUBLISHER_ID = b.PUBLISHER_ID
WHERE p.publishername = 'PRINTING IS US';

## Complete Code Example


### /pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.in28minutes.springboot</groupId>
	<artifactId>student-services</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>student-services</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>
</project>
```

### /src/test/java/com/in28minutes/springboot/controller/StudentControllerTest.java

```java
package com.in28minutes.springboot.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
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

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.service.StudentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	Course mockCourse = new Course("Course1", "Spring", "10Steps",
			Arrays.asList("Learn Maven", "Import Project", "First Example",
					"Second Example"));

	String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

	@Test
	public void retrieveDetailsForCourse() throws Exception {

		Mockito.when(
				studentService.retrieveCourse(Mockito.anyString(),
						Mockito.anyString())).thenReturn(mockCourse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/students/Student1/courses/Course1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{id:Course1,name:Spring,description:10Steps}";

		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	public void createStudentCourse() throws Exception {
		Course mockCourse = new Course("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		// studentService.addCourse to respond back with mockCourse
		Mockito.when(
				studentService.addCourse(Mockito.anyString(),
						Mockito.any(Course.class))).thenReturn(mockCourse);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/students/Student1/courses")
				.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/students/Student1/courses/1",
				response.getHeader(HttpHeaders.LOCATION));

	}

}
```
---

### /src/test/java/com/in28minutes/springboot/StudentServicesApplicationTests.java

```java
package com.in28minutes.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServicesApplicationTests {

	@Test
	public void contextLoads() {
	}

}
```
---
