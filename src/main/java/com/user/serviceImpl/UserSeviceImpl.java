package com.user.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.entity.Appointment;
import com.user.entity.AppointmentResponse;
import com.user.entity.User;
import com.user.feignclient.AppointmentClient;
import com.user.repository.UserRepository;
import com.user.response.UserResponse;
import com.user.service.UserService;

@Service
public class UserSeviceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppointmentClient appointmentClient;

	private static final Logger logger = LoggerFactory.getLogger(UserSeviceImpl.class);
	private static final String ERROR_MESSAGE = "Something went wrong";

	@Override
	public UserResponse<User> createUser(User user) {
		List<User> userList = new ArrayList<User>();

		try {
			if (!Objects.equals(user.getEmail(), "") && !Objects.equals(user.getName(), "")) {
				if (user.getEmail() != null && user.getName() != null) {
					Optional<User> existUser = userRepository.findByEmail(user.getEmail());

					if (existUser.isPresent()) {
						userList.add(existUser.get());
						return new UserResponse<>("User already exists", userList, false);
					} else {
						if (user.getPassword().matches("[a-zA-Z0-9@$]{8,}+")) {
							String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
							user.setPassword(encoded);
							userRepository.save(user);
							return new UserResponse<>("User saved successfully", userList, true);
						} else {
							return new UserResponse<>(
									"Password should contain uppercase, lowercase, digit, and special character and length must be 8 characters",
									userList, false);
						}
					}
				} else {
					return new UserResponse<>("User cannot be null", userList, false);
				}
			} else {
				return new UserResponse<>("User cannot be empty", userList, false);
			}
		} catch (Exception e) {
			return new UserResponse<>(ERROR_MESSAGE, userList, false);
		}
	}

	@Override
	public UserResponse<List<User>> getUserById(Long id) {
		List<User> userList = new ArrayList<User>();
		try {
			Optional<User> getUser = userRepository.findById(id);
			if (getUser.isPresent()) {
				User user = getUser.get();

				// fetch task by userId
				ResponseEntity<AppointmentResponse<List<Appointment>>> appointmentByUserId = appointmentClient
						.getAppointmentByUserId(getUser.get().getId());
				AppointmentResponse<List<Appointment>> body = appointmentByUserId.getBody();
				List<Appointment> appointmentList = body.getData();
				System.out.println(appointmentList);
				user.setAppointment(appointmentList);
				userList.add(user);

				return new UserResponse<List<User>>("Users fetched successfully", userList, true);
			}
		} catch (Exception e) {
			return new UserResponse<List<User>>(ERROR_MESSAGE, userList, false);
		}
		return new UserResponse<List<User>>("Users fetched successfully", userList, true);

	}

	@Override
	public UserResponse<List<User>> updateUser(Long id, User updateUser) {

		List<User> userList = new ArrayList<User>();
		try {
			if (id == null || updateUser == null) {
				throw new IllegalArgumentException("User id and updated user cannot be null");
			}

			User user = userRepository.findById(id).orElse(null);
			if (user == null) {
				return (new UserResponse<>("User not found with id: " + id, userList, false));
			}

			if (updateUser.getName() != null && !updateUser.getName().isEmpty()) {
				user.setName(updateUser.getName());
			}
			if (updateUser.getEmail() != null && !updateUser.getEmail().isEmpty()) {
				user.setEmail(updateUser.getEmail());
			}
			userList.add(user);
			userRepository.save(user);
			return new UserResponse<>("User updated successfully", userList, false);
		} catch (Exception e) {
			return (new UserResponse<>(ERROR_MESSAGE, userList, false));
		}
	}

	@Override
	public String deleteUser(Long id) {
		logger.info("finding the id of the User for getUserById");
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			logger.error("User ID is  not present=" + id);
			return "user is not present";
		} else {
			userRepository.deleteById(id);
			logger.info("Uesr with ID " + id + " deleted successfully.");
			return "user deleted sucessfully";
		}
	}

	@Override
	public UserResponse<List<User>> getAllUsers(Pageable paging) {
		List<User> newUserList = new ArrayList<User>();
		try {
			Page<User> userPage = userRepository.findAll(paging);
			List<User> userList = userPage.getContent();

			for (User user : userList) {
				ResponseEntity<AppointmentResponse<List<Appointment>>> appointmentByUserId = appointmentClient
						.getAppointmentByUserId(user.getId());
				System.out.println(appointmentByUserId);
				AppointmentResponse<List<Appointment>> body = appointmentByUserId.getBody();
				List<Appointment> appointmentList = body.getData();
				System.out.println(appointmentList);
				user.setAppointment(appointmentList);
				newUserList.add(user);

			}
			System.out.println(newUserList);
			return new UserResponse<List<User>>("Users fetched successfully", newUserList, true);
		} catch (Exception e) {
			return new UserResponse<List<User>>(ERROR_MESSAGE, newUserList, false);
		}
	}

}
