package com.mvalls.sidged.database.mybatis.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.core.model.PeriodType;
import com.mvalls.sidged.database.dtos.PeriodDTO;

@Mapper
public interface PeriodMapper {

	@Select("select * from period where id = #{periodId}")
	PeriodDTO findPeriodById(Long periodId);
	
	@Select("select * from period where "
			+ "period_type = #{type} "
			+ "and number = #{number} ")
	PeriodDTO findByPeriodTypeAndNumber(PeriodType type, Integer number);
	
	@Select("select * from period where "
			+ "period_type = #{type} "
			+ "and number is null")
	PeriodDTO findByPeriodType(PeriodType type);
	
}
