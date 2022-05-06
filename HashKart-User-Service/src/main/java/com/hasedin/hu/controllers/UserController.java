package com.hasedin.hu.controllers;


import com.hasedin.hu.dto.LoginDto;
import com.hasedin.hu.models.User;

import com.hasedin.hu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;


	//Sign up Controller
	@PostMapping("/signup")
	public ResponseEntity<User> signUpUser(@RequestBody User user) {

		logger.info("Request to save user signup controller...!");
		User savedUser = userService.saveUser(user);
		return new ResponseEntity<User>(savedUser, new HttpHeaders(), HttpStatus.CREATED);
	}

	//Sign up Controller
	@PostMapping("/login")
	public ResponseEntity<User> logInUser(@RequestBody LoginDto userInfo) {

		logger.info("Request to check user login controller...!");
		User savedUser = userService.logInUser(userInfo);
		return new ResponseEntity<User>(savedUser, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> retrieveAllUsers() {
		logger.info("retrieve all users controller...!");
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> retrieveUserById(@PathVariable long id) {

		logger.info("retrieve user controller...!");
		User user = userService.getUser(id);
		return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
	}


	@DeleteMapping("/{id}")
	public HttpStatus deleteUser(@PathVariable long id) throws Exception {

		logger.info("delete user controller...!");
		userService.deleteUser(id);
		return HttpStatus.ACCEPTED;
	}

	@PutMapping("/change")
	public ResponseEntity<User> updateUser(@RequestBody User user) {

		logger.info("update user controller...!");
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<User>(updatedUser, new HttpHeaders(), HttpStatus.ACCEPTED);
	}
}
