package com.mvalls.sidged.database.mybatis.dtos;

import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTeacherMyBatisDTO implements RepositoryDTO {
	
	private Long id;
	private UserMyBatisDTO user;
	private TeacherMyBatisDTO teacher;

}
