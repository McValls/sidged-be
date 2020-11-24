package com.mvalls.sidged.database.mybatis.dtos;

import java.time.LocalDate;
import java.util.List;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassDTO implements RepositoryDTO {

	private Long id;
	private Integer classNumber;
	private LocalDate date;
	private ClassState classState;
	private List<ClassStudentPresentMyBatisDTO> studentPresents;
	private CourseDTO course;
	
	
}
