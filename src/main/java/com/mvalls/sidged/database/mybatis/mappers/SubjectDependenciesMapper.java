package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.dtos.SubjectDependenciesDTO;

@Mapper
public interface SubjectDependenciesMapper {

	List<SubjectDependenciesDTO> findByCareerCode(String careerCode);

	Optional<SubjectDependenciesDTO> findBySubjectId(SubjectDTO dto);

	Optional<SubjectDependenciesDTO> findBySubjectCode(String subjectCode);

	void update(SubjectDependenciesDTO updatedDTO);

}
