package com.mvalls.sidged.database.mybatis.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.mybatis.dtos.StudentMyBatisDTO;
import com.mvalls.sidged.database.mybatis.dtos.UserMyBatisDTO;
import com.mvalls.sidged.database.mybatis.dtos.UserStudentMyBatisDTO;

@Mapper
public interface UserStudentMapper {

	@Insert("insert into user_student (user_id, student_id) values (#{user.id}, #{student.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(UserStudentMyBatisDTO dto);

	@Select("select us.* from user_student as us "
			+ "inner join user as u on us.user_id = u.id "
			+ "where u.username = #{username}")
	@Results(value = {
		@Result(column = "student_id", property = "student", javaType = StudentMyBatisDTO.class,
				one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.StudentMapper.findStudentById")),
		@Result(column = "user_id", property = "user", javaType = UserMyBatisDTO.class,
		one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.UserMapper.findUserById"))
	})
	Optional<UserStudentMyBatisDTO> findByUserUsername(String username);
	
	@Select("select * from user_student where student_id = #{studentId}")
	@Results(value = {
		@Result(column = "student_id", property = "student", javaType = StudentMyBatisDTO.class,
				one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.StudentMapper.findStudentById")),
		@Result(column = "user_id", property = "user", javaType = UserMyBatisDTO.class,
		one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.UserMapper.findUserById"))
	})
	Optional<UserStudentMyBatisDTO> findByStudentId(Long studentId);

}
