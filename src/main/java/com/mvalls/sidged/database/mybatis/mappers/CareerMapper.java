package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.CareerDTO;

@Mapper
public interface CareerMapper {
	
	@Select("SELECT * FROM career")
	List<CareerDTO> findAllCareers();
	
	@Select("SELECT * FROM career WHERE CODE = #{code}")
	Optional<CareerDTO> findByCode(String code);

	@Select("select * from career where id = #{careerId}")
	CareerDTO findCareerById(Long careerId);

}
