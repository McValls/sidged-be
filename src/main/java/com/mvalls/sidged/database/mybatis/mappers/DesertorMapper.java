package com.mvalls.sidged.database.mybatis.mappers;

import com.mvalls.sidged.database.dtos.DesertorDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DesertorMapper {

    @Select("select * from DESERTOR_BATCH_CONFIG where TEXT = #{TEXT} and CODE = #{CODE}" )
	DesertorDTO findByCodeAndLanguage(String CODE, String TEXT);
    
}
