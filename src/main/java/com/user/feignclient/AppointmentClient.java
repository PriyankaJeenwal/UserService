package com.user.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.entity.Appointment;
import com.user.entity.AppointmentResponse;




@FeignClient(url = "http://localhost:9091", value = "AppointmentClient")

public interface AppointmentClient {
	@GetMapping("/appointment/user/{userId}")
	public ResponseEntity<AppointmentResponse<List<Appointment>>> getAppointmentByUserId(@PathVariable Long userId);

}
