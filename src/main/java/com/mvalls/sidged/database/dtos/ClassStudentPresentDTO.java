package com.mvalls.sidged.database.dtos;

import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassStudentPresentDTO implements RepositoryDTO {

	private Long id;
	private StudentDTO student;
	private StudentPresent present;
	private CourseClassDTO courseClass;
	
}
