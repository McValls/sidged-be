package com.mvalls.sidged.database.mybatis.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.database.mybatis.dtos.UserMyBatisDTO;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM user WHERE username = #{username}")
	Optional<UserMyBatisDTO> findByUserName(String username);

	@Insert("insert into user (email, password, user_status, user_type, username) values "
			+ "(#{email}, #{password}, #{userStatus}, #{userType}, #{username})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(UserMyBatisDTO userDTO);
	
	@Select("select * from user where id = #{id}")
	UserMyBatisDTO findUserById(Long id);

	@Update("update user set "
			+ "email = #{email}, "
			+ "password = #{password} "
			+ "where id = #{id}")
	void update(UserMyBatisDTO dto);
	
}
