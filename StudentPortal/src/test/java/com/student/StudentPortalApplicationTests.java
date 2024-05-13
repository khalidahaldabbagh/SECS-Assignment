package com.student;


import com.student.repository.StudentRepository;
import com.student.repository.UserRepository;
import com.student.controller.AuthenticationController;
import com.student.model.Course;
import com.student.model.Student;
import com.student.model.User;
import com.student.repository.StudentRepository;
import com.student.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class StudentPortalApplicationTests {

	private static final String USERNAME = "qwerty1";
	private static final String EMAIL = "qwerty1@gmail.com";
	private static final String PASSWORD = "123456";
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentRepository studentRepository;




	@Test
	public void loginError_ReturnsCorrectViewName() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/login-error"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());

	}
	@Test
	public void registerForm_ReturnsCorrectViewName() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/register"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("register"));
	}


	@Test
	@Order(1)
	@WithMockUser(username = USERNAME, password = PASSWORD) // Simulate authenticated user
	public void register_ReturnsCorrectModelAndView() throws Exception {

		ModelAndView expectedModelAndView = new ModelAndView("register-success");
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("email", EMAIL);
		formData.add("userName", USERNAME);
		formData.add("password", PASSWORD);
		User user1 = new User();
		user1.setUserName(USERNAME);
		user1.setEmail(EMAIL);
		URI createUri = MvcUriComponentsBuilder.fromMethodCall(on(AuthenticationController.class)
						.register(user1))
				.buildAndExpand(1).toUri();

		mockMvc.perform(post(createUri).params(formData))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("register-success"));

		User user = userRepository.findByUserName(USERNAME);

		mockMvc.perform(post("/courses/1/enrol").with(request -> {
					request.setAttribute("user", user); // Add user to request attributes
					return request;
				}))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/courses/1").with(request -> {
					request.setAttribute("user", user); // Add user to request attributes
					return request;
				}))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("course"))
				.andExpect(model().attribute("isEnrolled", is(true)))
				.andExpect(model().attribute("message", is("You are enrolled in this course."))) ;// Check updated attribute value


		Student  student= studentRepository.findByUserId(user.getId());

		mockMvc.perform(MockMvcRequestBuilders.get("/enrolments").with(request -> {
					request.setAttribute("student", student); // Add user to request attributes
					return request;
				}))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("courses"));

		mockMvc.perform(MockMvcRequestBuilders.get("/graduation").with(request -> {
					request.setAttribute("student", student); // Add user to request attributes
					return request;
				}))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("graduation"));

		mockMvc.perform(MockMvcRequestBuilders.get("/home").with(request -> {
					request.setAttribute("user", user); // Add user to request attributes
					return request;
				}))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("home"));

		mockMvc.perform(MockMvcRequestBuilders.get("/profile").with(request -> {
					request.setAttribute("user", user); // Add user to request attributes
					return request;
				}))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("profile"));

		mockMvc.perform(MockMvcRequestBuilders.get("/editProfile/"+student.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("profile-edit"));

		MultiValueMap<String, String> formData2 = new LinkedMultiValueMap<>();
		formData2.add("id", student.getId().toString());
		formData2.add("forename", "RAJU");
		formData2.add("surname", "Rampal");

		mockMvc.perform(MockMvcRequestBuilders.post("/editProfile").params(formData2))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("profile"))
				.andExpect(model().attribute("isStudent", is(true)))
				.andExpect(model().attribute("updated", is(true))) // Check updated attribute value
				.andExpect(model().attribute("message", is("Profile updated"))); // Check message attribute value

	}


	@Test
	@Order(2)
	@WithMockUser(username = USERNAME, password = PASSWORD) // Simulate authenticated user
	public void testCourses() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("courses"));
	}



	@Test
	@Order(3)
	@WithMockUser(username = USERNAME, password = PASSWORD) // Simulate authenticated user
	public void testSearchCourses() throws Exception {
		List<Course> courses = Collections.emptyList();
		mockMvc.perform(MockMvcRequestBuilders.post("/courses/search").param("searchString", "boot"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("courses"));
	}


}