package com.mvalls.sidged.database.mybatis.mappers;

import com.mvalls.sidged.database.dtos.ContactDataDTO;
import com.mvalls.sidged.database.dtos.DesertorDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DesertorMapper {

    @Select("select * from DESERTOR_BATCH_CONFIG where TEXT = #{TEXT}")
	@Results(value = {
			@Result(property = "contactData", javaType = ContactDataDTO.class, column = "contact_data_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper.findByLanguage")),
	})
	DesertorDTO findByLanguage(String TEXT);
    
}
