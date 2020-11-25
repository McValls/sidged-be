package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.database.dtos.ClassStudentPresentDTO;
import com.mvalls.sidged.database.dtos.StudentDTO;

@Mapper
public interface ClassStudentPresentMapper {

	@Select("select csp.* from class_student_present as csp "
			+ "inner join course_class as cc on cc.id = csp.course_class_id "
			+ "inner join course as c on cc.course_id = c.id "
			+ "where cc.class_number = #{classNumber} and c.code = #{courseCode}")
	@Results(value = {
			@Result(column = "student_id", property = "student", 
					javaType = StudentDTO.class,
					one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.StudentMapper.findStudentById"))			
	})
	List<ClassStudentPresentDTO> findByCourseAndClassNumber(String courseCode, Integer classNumber);
	
	
	@Select("select * from class_student_present where course_class_id = #{courseClassId}")
	@Results(value = {
			@Result(column = "student_id", property = "student", 
					javaType = StudentDTO.class,
					one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.StudentMapper.findStudentById"))			
	})
	List<ClassStudentPresentDTO> findByCourseClassId(Long courseClassId);	
	
	@Select("select id, student_id, present from class_student_present where id = #{id}")
	@Results(value = {
			@Result(column = "student_id", property = "student", 
					javaType = StudentDTO.class,
					one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.StudentMapper.findStudentById"))			
	})
	ClassStudentPresentDTO findClassStudentPresentById(Long id);

	@Select("select csp.id, csp.student_id, csp.present from class_student_present as csp "
			+ "inner join course_class cc on csp.course_class_id = cc.id "
			+ "inner join course c on cc.course_id = c.id "
			+ "where csp.student_id = #{studentId} "
			+ "and cc.class_number = #{classNumber} "
			+ "and c.code = #{courseCode}")
	@Results(value = {
			@Result(column = "student_id", property = "student", 
					javaType = StudentDTO.class,
					one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.StudentMapper.findStudentById"))			
	})
	Optional<ClassStudentPresentDTO> findByCourseCodeClassNumberAndStudentId(String courseCode, Integer classNumber, Long studentId);

	@Insert("insert into class_student_present (present, student_id, course_class_id) "
			+ "values (#{present}, #{student.id}, #{courseClass.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(ClassStudentPresentDTO dto);
	
	@Update("update class_student_present set "
			+ "present = #{present} "
			+ "where id = #{id}")
	void update(Long id, StudentPresent present);



}
