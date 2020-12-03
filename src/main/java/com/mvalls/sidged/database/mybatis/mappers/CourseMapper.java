package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.dtos.CourseDTO;
import com.mvalls.sidged.database.dtos.PeriodDTO;
import com.mvalls.sidged.database.dtos.TimeDTO;

@Mapper
public interface CourseMapper {

	@Insert("insert into course(code, name, shift, year, period_id, time_start_id, time_end_id, subject_id) "
			+ "values (#{code}, #{name}, #{shift}, #{year}, #{period.id}, #{timeStart.id}, #{timeEnd.id}, #{subject.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(CourseDTO dto);
	
	@Select("select * from course")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	List<CourseDTO> findAll();

	@Select("select c.* from course as c "
			+ "inner join course_teacher as ct on c.id = ct.course_id "
			+ "where ct.teacher_id = #{teacherId}")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	List<CourseDTO> findByTeacherId(Long teacherId);

	@Select("select c.* from course as c "
			+ "inner join course_student as cs on c.id = cs.course_id "
			+ "where cs.student_id = #{studentId}")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	List<CourseDTO> findByStudentId(Long studentId);
	
	
	@Select("select c.* from course as c "
			+ "inner join course_class as cc on c.id = cc.course_id "
			+ "where cc.id = #{courseClassId}")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	Optional<CourseDTO> findByCourseClassId(Long courseClassId);

	@Select("select c.* from course as c where c.year = #{year}")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	List<CourseDTO> findByYear(Integer year);

	@Select("select c.* from course as c where c.code = #{code}")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	Optional<CourseDTO> findByCode(String code);

	@Select("select c.* from course as c where c.id = #{id}")
	@Results(value = {
			@Result(property = "period", javaType = PeriodDTO.class, column = "period_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.PeriodMapper.findPeriodById")),
			@Result(property = "timeStart", javaType = TimeDTO.class, column = "time_start_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "timeEnd", javaType = TimeDTO.class, column = "time_end_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.TimeMapper.findTimeById")),
			@Result(property = "subject", javaType = CareerDTO.class, column = "subject_id", one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.SubjectMapper.findSubjectById"))
	})
	CourseDTO findById(Long id);
	
}
