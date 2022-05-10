package com.hasedin.hu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasedin.hu.dto.LoginDto;
import com.hasedin.hu.models.User;
import com.hasedin.hu.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	UserRepository userRepository;

	private User user1;
	//private LoginDto loginDto;

	@BeforeEach
	void setUp() throws Exception {
		user1 = new User("test@email.com","testName","password",23243,"testAddress");
		//loginDto=new LoginDto("test@email.com","password");
	}

	@Test
	void testSignUp() throws Exception {

		User user2 = new User("test2@email.com","testName","password",23243,"testAddress");

		mockMvc.perform(post("/user/signup")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(user2)))
				        .andExpect(status().isCreated());

	}

	@Test
	void testLoginwithSuccess() throws Exception {


		User user=userRepository.save(user1);

		LoginDto login=new LoginDto("test@email.com","password");

		mockMvc.perform(post("/user/login")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(login)))
				.andExpect(status().isOk());

	}

	@Test
	void testLogoutwithSuccess() throws Exception {

		User user3 = new User("test3@email.com","testName","password",23243,"testAddress");

		User user=userRepository.save(user3);

		LoginDto userinfo=new LoginDto("test3@email.com","password");

		mockMvc.perform(post("/user/logout")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(userinfo)))
				.andExpect(status().isOk());

	}

	@Test
	void testLogoutwithApplicationException() throws Exception {


		LoginDto userinfo=new LoginDto("test3@email.com","pasword");

		mockMvc.perform(get("/user/logout")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(userinfo)))
				.andExpect(status().isBadRequest());

	}

	@Test
	void testGetAllUserSuccess() throws Exception {

		User user5 = new User("test5@email.com","testName","password",23243,"testAddress");

		User user=userRepository.save(user5);

		mockMvc.perform(get("/user/all")
						.contentType("application/json"))
				.andExpect(status().isOk());

	}

	@Test
	void testGetAllUserResourceNotFoundException() throws Exception {

		userRepository.deleteAll();
		mockMvc.perform(get("/user/all")
						.contentType("application/json"))
				.andExpect(status().is4xxClientError());

	}

	@Test
	void testfindByIdSuccess() throws Exception {

		User user=userRepository.save(user1);

		mockMvc.perform(get("/user/"+user.getId())
						.contentType("application/json"))
				.andExpect(status().isOk());

	}

}
