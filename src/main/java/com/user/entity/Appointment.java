package com.user.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Appointment {

	private Long id;

	private String disease;

	private String doctorName;

	private LocalDate appintmentDate;

	private Long userId;

	private Long doctorId;

	private String description;

	private String previousMedicalDocument;

}
