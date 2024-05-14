package com.user.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse<T> {
	private String message;
	private Object data;
	private boolean Status;
	public UserResponse(String message,Object data, boolean status) {
		super();
		this.message = message;
		this.data = data;
		Status = status;
	}
	

	
}
