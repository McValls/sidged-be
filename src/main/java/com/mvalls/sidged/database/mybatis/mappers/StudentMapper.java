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
import com.mvalls.sidged.database.mybatis.dtos.StudentMyBatisDTO;

@Mapper
public interface StudentMapper {

	@Select("select s.* from student as s "
			+ "inner join course_student as cs on s.id = cs.student_id "
			+ "inner join course as c on cs.course_id = c.id "
			+ "where c.code = #{courseCode}")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataMyBatisDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findContactDataById")),
	})
	List<StudentMyBatisDTO> findByCourseCode(String courseCode);

	@Delete("delete from course_student where student_id = #{studentId} "
			+ "and course_id = (select c.id from course as c where c.code = #{courseCode})")
	void removeCourseStudent(String courseCode, Long studentId);
	
	
	@Insert("insert into course_student (course_id, student_id) values "
			+ "((select c.id from course as c where c.code = #{courseCode}), #{studentId})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void addCourseStudent(String courseCode, Long studentId);
	
	@Select("select * from student")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataMyBatisDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findContactDataById")),
	})
	List<StudentMyBatisDTO> findAll();
	
	@Select("select * from student where id = #{id}")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataMyBatisDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findContactDataById")),
	})
	StudentMyBatisDTO findStudentById(Long id);

	@Insert("insert into student (names, lastname, identification_number, contact_data_id) "
			+ "values (#{names}, #{lastname}, #{identificationNumber}, #{contactData.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Long insert(StudentMyBatisDTO dto);

	@Update("update student set "
			+ "lastname = #{lastname}, "
			+ "names = #{names}, "
			+ "identification_number = #{identificationNumber} "
			+ "where id = #{id}")
	void update(StudentMyBatisDTO dto);

}
