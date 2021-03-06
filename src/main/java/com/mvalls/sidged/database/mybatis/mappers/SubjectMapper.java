package com.mvalls.sidged.database.mybatis.mappers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.dtos.SubjectDTO;

@Mapper
public interface SubjectMapper {

	@Select("select * from subject where code = #{code}")
	@Results(value = {
			@Result(property = "career", javaType = CareerDTO.class, column = "career_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CareerMapper.findCareerById"))
	})
	SubjectDTO findByCode(String code);
	
	@Select("select * from subject where id = #{id}")
	@Results(value = {
			@Result(property = "career", javaType = CareerDTO.class, column = "career_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CareerMapper.findCareerById"))
	})
	SubjectDTO findSubjectById(Long id);

	@Select("select * from subject")
	@Results(value = {
			@Result(property = "career", javaType = CareerDTO.class, column = "career_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CareerMapper.findCareerById"))
	})
	List<SubjectDTO> findAll();
	
	
	default List<SubjectDTO> findAllByIds(long[] ids) {
		return Arrays.stream(ids).mapToObj(id -> findSubjectById(id)).collect(Collectors.toList());
	}

	@Select("select * from subject as s "
			+ "inner join career as c on s.career_id = c.id "
			+ "where c.code = #{careerCode}")
	@Results(value = {
			@Result(property = "career", javaType = CareerDTO.class, column = "career_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CareerMapper.findCareerById"))
	})
	List<SubjectDTO> findByCareerCode(String careerCode);

	@Insert("insert into subject (name, code, career_id) "
			+ "values (#{name}, #{code}, #{career.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void create(SubjectDTO dto);

	@Update("update subject set name = #{name} where id = #{id}")
	void update(SubjectDTO dto);

	@Delete("delete from subject where code = #{code}")
	int delete(String code);

	
}
