package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.StudentLinkDTO;

@Mapper
public interface StudentLinkMapper {
	
	@Select("select * from student_link")
	List<StudentLinkDTO> findAll();

}
