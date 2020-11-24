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
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.database.mybatis.dtos.CourseClassDTO;
import com.mvalls.sidged.database.mybatis.dtos.CourseDTO;

@Mapper
public interface CourseClassMapper {

	@Select("select cc.class_number, cc.class_state, cc.date, cc.course_id "
			+ "from course_class as cc "
			+ "inner join course as c on cc.course_id = c.id "
			+ "WHERE c.code = #{courseCode}")
	@Results(value = {
		@Result(column = "course_id", property = "course",
				javaType = CourseDTO.class, 
				one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CourseMapper.findById"))
	})
	List<CourseClassDTO> findByCourseCode(String courseCode);


	@Select("select cc.id, cc.class_number, cc.class_state, cc.date, cc.course_id "
			+ "from course_class as cc "
			+ "inner join course as c on cc.course_id = c.id "
			+ "WHERE c.code = #{courseCode} and cc.class_number = #{classNumber}")
	@Results(value = {
			@Result(column = "course_id", property = "course",
					javaType = CourseDTO.class, 
					one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CourseMapper.findById"))
		})
	Optional<CourseClassDTO> findByCourseCodeAndClassNumber(String courseCode,
			Integer classNumber);

	@Select("select cc.id, cc.class_number, cc.class_state, cc.date, cc.course_id "
			+ "from course_class as cc "
			+ "inner join course as c on cc.course_id = c.id "
			+ "inner join course_student as cs on c.id = cs.course_id "
			+ "where cs.student_id = #{studentId}")
	@Results(value = {
			@Result(column = "course_id", property = "course",
					javaType = CourseDTO.class, 
					one = @One(select = "com.mvalls.sidged.database.mybatis.mappers.CourseMapper.findById"))
		})
	List<CourseClassDTO> findByStudentId(Long studentId);
	
	@Insert("insert into course_class (class_number, class_state, date, course_id) "
			+ "values (#{classNumber}, #{classState}, #{date}, #{course.id})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	public void insert(CourseClassDTO dto);

	@Update("update course_class "
			+ "set class_state = #{classState} "
			+ "where id = #{courseClassId}")
	void update(Long courseClassId, ClassState classState);


	
}
