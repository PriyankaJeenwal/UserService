package com.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.response.UserResponse;

@Service
public interface UserService {

	public UserResponse<User> createUser(User user);

	public UserResponse<List<User>> getAllUsers(Pageable paging);

	public UserResponse<List<User>> getUserById(Long id);

	public UserResponse<List<User>> updateUser(Long id, User updateUser) throws Exception;

	public String deleteUser(Long id);

}
