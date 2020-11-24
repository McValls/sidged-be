package com.mvalls.sidged.database.mybatis.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.TimeDTO;

@Mapper
public interface TimeMapper {

	@Select("select * from time where id = #{timeId}")
	TimeDTO findTimeById(Long timeId);
	
}
