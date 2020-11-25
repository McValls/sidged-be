package com.mvalls.sidged.database.mybatis.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.TeacherDTO;
import com.mvalls.sidged.database.dtos.UserDTO;
import com.mvalls.sidged.database.dtos.UserTeacherDTO;

@Mapper
public interface UserTeacherMapper {

	@Insert("insert into user_teacher (user_id, teacher_id) values (#{user.id}, #{teacher.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(UserTeacherDTO dto);

	@Select("select * from user_teacher where teacher_id = #{teacherId}")
	@Results(value = {
		@Result(property = "teacher", javaType = TeacherDTO.class, 
				column = "teacher_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TeacherMapper.findTeacherById")),
		@Result(property = "user", javaType = UserDTO.class, 
			column = "user_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.UserMapper.findUserById"))
	})
	UserTeacherDTO findByTeacherId(Long teacherId);

	@Select("select ut.* from user_teacher as ut "
			+ "inner join user as u on ut.user_id = u.id "
			+ "where u.username = #{username}")
	@Results(value = {
		@Result(property = "teacher", javaType = TeacherDTO.class, 
				column = "teacher_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TeacherMapper.findTeacherById")),
		@Result(property = "user", javaType = UserDTO.class, 
			column = "user_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.UserMapper.findUserById"))
	})
	UserTeacherDTO findByUsername(String username);

}
