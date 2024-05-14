package com.user.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AppointmentResponse<T> {
//	private Object data;

	private List<Appointment> data;

	private String message;

	private boolean status;

	public  AppointmentResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}
}
