package com.hasedin.hu.services;


import com.hasedin.hu.dto.LoginDto;
import com.hasedin.hu.exceptions.ApplicationException;
import com.hasedin.hu.exceptions.ResourceAlreadyExistsException;
import com.hasedin.hu.exceptions.ResourceNotFoundException;
import com.hasedin.hu.models.User;
import com.hasedin.hu.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserService {


	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() throws ResourceNotFoundException {
		List<User> users = userRepository.findAll();
		if(!users.isEmpty()) {
			logger.info("getAllUsers {}", this.getClass().getName());
			return users;
		} else {
			logger.error("getAllUsers {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public User getUser(Long id) throws ResourceNotFoundException{
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			logger.info("getUser {}", this.getClass().getName());
			return user.get();
		} else {
			logger.error("getUser {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	public User logInUser(LoginDto userInfo) throws ResourceNotFoundException{
		Optional<User> user = userRepository.findByEmailId(userInfo.getEmailId());
		if(user.isPresent() && user.get().getPassword().equals(userInfo.getPassword())) {
			logger.info("valid User {}", this.getClass().getName());
			return user.get();
		} else {
			logger.error("Login Failed {} User not Found!", this.getClass().getName());
			throw new ApplicationException("Invalid Login Credientials");
		}
	}

	public User saveUser(User user) throws ResourceAlreadyExistsException {

		if(user.getEmailId()==null || user.getId() != null) {
			throw new ApplicationException("Invalid Input");

		}
		Optional<User> u = userRepository.findByEmailId(user.getEmailId());
		if(u.isPresent()) {
				logger.error("createUser {} User already exists!", this.getClass().getName());
				throw new ResourceAlreadyExistsException();
		}
		logger.info("createUser {}", this.getClass().getName());
		return userRepository.save(user);	
	}

	@Transactional
	public void deleteUser(Long id) throws ResourceNotFoundException{
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			userRepository.deleteById(id);
		} else {
			logger.error("deleteUser {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

	@Transactional
	public User updateUser(User user) throws ResourceNotFoundException{
		Optional<User> u = userRepository.findById(user.getId());
		if(u.isPresent()) {
			logger.info("updateUser {}", this.getClass().getName());
			return userRepository.save(user);
		} else {
			logger.error("updateUser {} User not Found!", this.getClass().getName());
			throw new ResourceNotFoundException();
		}
	}

}
