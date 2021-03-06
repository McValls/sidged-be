package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.database.dtos.CareerDTO;

@Mapper
public interface CareerMapper {

	@Select("SELECT * FROM career")
	List<CareerDTO> findAllCareers();

	@Select("SELECT * FROM career WHERE CODE = #{code}")
	Optional<CareerDTO> findByCode(String code);

	@Select("select * from career where id = #{careerId}")
	CareerDTO findCareerById(Long careerId);

	@Select("select c.* from career "
			+ "inner join subject s on c.id = s.career_id "
			+ "inner join course co on s.id = co.subject_id "
			+ "inner join course_student cs on co.id = cs.course_id "
			+ "inner join student st on cs.student_id = st.id "
			+ "where st.identification_number = #{studentIdentificationNumber}")
	List<CareerDTO> findByStudentIdentificationNumber(String studentIdentificationNumber);
	
	@Insert("insert into career (code, name) "
			+ "values (#{code}, #{name})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(CareerDTO dto);

	@Update("update career set "
			+ "code = #{code}, "
			+ "name = #{name} "
			+ "where id = #{id}")
	void update(CareerDTO dto);

	@Delete("delete from career where id = #{id}")
	int delete(Long id);


	

}
