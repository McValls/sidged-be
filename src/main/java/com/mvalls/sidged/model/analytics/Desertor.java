package com.mvalls.sidged.model.analytics;

import java.time.LocalDate;

import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.model.Student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Desertor {
	
	private Student student;
	
	private Course course;
	
	private LocalDate localDate;

}
