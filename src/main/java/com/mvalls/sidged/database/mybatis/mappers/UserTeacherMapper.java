package com.mvalls.sidged.database.mybatis.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.mybatis.dtos.TeacherMyBatisDTO;
import com.mvalls.sidged.database.mybatis.dtos.UserMyBatisDTO;
import com.mvalls.sidged.database.mybatis.dtos.UserTeacherMyBatisDTO;

@Mapper
public interface UserTeacherMapper {

	@Insert("insert into user_teacher (user_id, teacher_id) values (#{user.id}, #{teacher.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(UserTeacherMyBatisDTO dto);

	@Select("select * from user_teacher where teacher_id = #{teacherId}")
	@Results(value = {
		@Result(property = "teacher", javaType = TeacherMyBatisDTO.class, 
				column = "teacher_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TeacherMapper.findTeacherById")),
		@Result(property = "user", javaType = UserMyBatisDTO.class, 
			column = "user_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.UserMapper.findUserById"))
	})
	UserTeacherMyBatisDTO findByTeacherId(Long teacherId);

	@Select("select ut.* from user_teacher as ut "
			+ "inner join user as u on ut.user_id = u.id "
			+ "where u.username = #{username}")
	@Results(value = {
		@Result(property = "teacher", javaType = TeacherMyBatisDTO.class, 
				column = "teacher_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TeacherMapper.findTeacherById")),
		@Result(property = "user", javaType = UserMyBatisDTO.class, 
			column = "user_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.UserMapper.findUserById"))
	})
	UserTeacherMyBatisDTO findByUsername(String username);

}
