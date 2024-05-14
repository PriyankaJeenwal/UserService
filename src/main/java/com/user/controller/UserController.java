package com.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.entity.User;
import com.user.response.UserResponse;

public interface UserController {

	@PostMapping("/createUser")
	public ResponseEntity<UserResponse<User>> createUser(@RequestBody User user);

	@GetMapping("/getUsers")
	public ResponseEntity<UserResponse<List<User>>> getAllUsers(
			@RequestParam(defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(defaultValue = "0", required = false) Integer page);

	@GetMapping("/getUsersById")
	public ResponseEntity<UserResponse<List<User>>> getUserById(@RequestParam Long id);

	@PostMapping("/updateUser")
	public ResponseEntity<UserResponse<List<User>>> updateUser(@RequestParam Long id, @RequestBody User updateUser)
			throws Exception;

	@PostMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam Long id);

}