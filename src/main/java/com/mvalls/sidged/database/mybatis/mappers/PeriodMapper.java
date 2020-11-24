package com.mvalls.sidged.database.mybatis.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.PeriodDTO;

@Mapper
public interface PeriodMapper {

	@Select("select * from period where id = #{periodId}")
	PeriodDTO findPeriodById(Long periodId);
	
}
