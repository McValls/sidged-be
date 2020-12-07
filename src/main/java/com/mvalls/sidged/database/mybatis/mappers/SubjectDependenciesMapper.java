package com.mvalls.sidged.database.mybatis.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.dtos.SubjectDependenciesDTO;

@Mapper
public interface SubjectDependenciesMapper {

	@Select("select sd.* from subject_dependencies as sd "
			+ "inner join subject as s on sd.subject_id = s.id "
			+ "inner join career as c on s.career_id = c.id "
			+ "where c.code = #{careerCode}")
	@Results(value = {
		@Result(property = "subject", column = "subject_id", 
				javaType = SubjectDTO.class, one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById")),
		@Result(property = "dependencies", column = "dependencies_subject_ids", 
				javaType = ArrayList.class, many = @Many(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findAllByIds"))
	})
	List<SubjectDependenciesDTO> findByCareerCode(String careerCode);

	@Select("select sd.* from subject_dependencies as sd "
			+ " where sd.subject_id = #{id}")
	@Results(value = {
		@Result(property = "subject", column = "subject_id", 
				javaType = SubjectDTO.class, one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById")),
		@Result(property = "dependencies", column = "dependencies_subject_ids", jdbcType = JdbcType.VARCHAR,
				javaType = ArrayList.class, many = @Many(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findAllByIds"))
	})
	Optional<SubjectDependenciesDTO> findBySubjectId(SubjectDTO dto);

	@Select("select sd.* from subject_dependencies as sd "
			+ "inner join subject as s on sd.subject_id = s.id "
			+ "where s.code = #{subjectCode}")
	@Results(value = {
		@Result(property = "subject", column = "subject_id",  
				javaType = SubjectDTO.class, one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById")),
		@Result(property = "dependencies", column = "dependencies_subject_ids", 
				javaType = ArrayList.class, many = @Many(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findAllByIds"))
	})
	Optional<SubjectDependenciesDTO> findBySubjectCode(String subjectCode);

	@Update("update subject_dependencies "
			+ "set dependencies_subject_ids = #{dependenciesIds} "
			+ "where subject_id = #{subject.id}")
	void update(SubjectDependenciesDTO updatedDTO);

	@Insert("insert into subject_dependencies (subject_id, dependencies_subject_ids) "
			+ "values (#{subject.id}, #{dependenciesIds})")
	void insert(SubjectDependenciesDTO newDTO);

}
