package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.database.mybatis.dtos.ContactDataMyBatisDTO;
import com.mvalls.sidged.database.mybatis.dtos.TeacherMyBatisDTO;

@Mapper
public interface TeacherMapper {

	@Select("select t.* from teacher as t "
			+ "inner join course_teacher as ct on t.id = ct.teacher_id "
			+ "inner join course as c on ct.course_id = c.id "
			+ "where c.code = #{courseCode}")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataMyBatisDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findContactDataById")),
	})
	List<TeacherMyBatisDTO> findByCourseCode(String courseCode);

	@Delete("delete from course_teacher where teacher_id = #{teacherId} "
			+ "and course_id = (select c.id from course as c where c.code = #{courseCode})")
	void removeCourseTeacher(String courseCode, Long teacherId);

	@Insert("insert into course_teacher (course_id, teacher_id) values "
			+ "((select c.id from course as c where c.code = #{courseCode}), #{teacherId})")
	void addCourseTeacher(String courseCode, Long teacherId);
	

	@Select("select * from teacher")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataMyBatisDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findContactDataById")),
	})
	List<TeacherMyBatisDTO> findAll();
	
	@Select("select * from teacher where id = #{teacherId}")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataMyBatisDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findContactDataById")),
	})
	TeacherMyBatisDTO findTeacherById(Long teacherId);

	@Insert("insert into teacher (names, lastname, contact_data_id) "
			+ "values "
			+ "(#{names}, #{lastname}, #{contactData.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(TeacherMyBatisDTO dto);

	@Update("update teacher set lastname = #{lastname}, "
			+ "names = #{names} "
			+ "where id = #{id}")
	void update(TeacherMyBatisDTO dto);
}
