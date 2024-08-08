package com.RamaIT.Models;

import com.RamaIT.Enums.Course;
import com.RamaIT.Enums.Mode;
import com.RamaIT.Enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	String name;
	String phoneNumber;
	@Enumerated(EnumType.STRING)
	Course course;
	@Enumerated(EnumType.STRING)
	Mode mode;
	@Enumerated(EnumType.STRING)
	Status status;
}
