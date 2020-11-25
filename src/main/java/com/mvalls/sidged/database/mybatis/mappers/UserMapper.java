package com.mvalls.sidged.database.mybatis.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.database.dtos.UserDTO;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM user WHERE username = #{username}")
	Optional<UserDTO> findByUserName(String username);

	@Insert("insert into user (email, password, user_status, user_type, username) values "
			+ "(#{email}, #{password}, #{userStatus}, #{userType}, #{username})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(UserDTO userDTO);
	
	@Select("select * from user where id = #{id}")
	UserDTO findUserById(Long id);

	@Update("update user set "
			+ "email = #{email}, "
			+ "password = #{password} "
			+ "where id = #{id}")
	void update(UserDTO dto);
	
}
