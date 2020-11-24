package com.mvalls.sidged.database.mybatis.dtos;

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
public class ClassStudentPresentMyBatisDTO implements RepositoryDTO {

	private Long id;
	private StudentMyBatisDTO student;
	private StudentPresent present;
	private CourseClassDTO courseClass;
	
}
