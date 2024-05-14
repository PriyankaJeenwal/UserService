package com.user.controllerImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.controller.UserController;
import com.user.entity.User;
import com.user.response.UserResponse;
import com.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

	static final Logger logger = LogManager.getLogger(UserControllerImpl.class.getName());

	@Autowired
	private UserService userService;


	private static final String ERROR_MESSAGE = "Something went wrong";

	@Override
	public ResponseEntity<UserResponse<User>> createUser(User user) {
		UserResponse<User> userResponse = new UserResponse<User>();
		try {
			userResponse = userService.createUser(user);
			if (userResponse.isStatus()) {
				return new ResponseEntity<UserResponse<User>>(userResponse, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<UserResponse<User>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<UserResponse<User>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<UserResponse<List<User>>> getAllUsers(Integer pageSize, Integer page) {
		UserResponse<List<User>> userResponse = new UserResponse<List<User>>();
		try {
			Pageable paging = PageRequest.of(page, pageSize);
			userResponse = userService.getAllUsers(paging);
			if (userResponse.isStatus()) {
				return new ResponseEntity<UserResponse<List<User>>>(userResponse, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<UserResponse<List<User>>>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<UserResponse<List<User>>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<UserResponse<List<User>>> getUserById(Long id) {
		UserResponse<List<User>> userResponse = new UserResponse<List<User>>();
		try {
			userResponse = userService.getUserById(id);
			if (userResponse.isStatus()) {
				return new ResponseEntity<UserResponse<List<User>>>(userResponse, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<UserResponse<List<User>>>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<UserResponse<List<User>>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<UserResponse<List<User>>> updateUser(Long id, User updateUser) {
		UserResponse<List<User>> userResponse = new UserResponse<List<User>>();
		try {
			userResponse = userService.updateUser(id, updateUser);
			if (userResponse.isStatus()) {
				return new ResponseEntity<UserResponse<List<User>>>(userResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<UserResponse<List<User>>>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<UserResponse<List<User>>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<String> deleteUser(Long id) {
		try {
			if (id != 0) {
				logger.info("Deleting User with id: {}", id);
				String existEmployee = userService.deleteUser(id);
				return ResponseEntity.status(HttpStatus.OK).body(existEmployee);
			} else {
				logger.warn("please enter the id");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return new ResponseEntity<String>("user not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
