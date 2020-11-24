package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.database.mappers.CourseRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.dtos.CourseDTO;
import com.mvalls.sidged.database.mybatis.mappers.CourseMapper;

public class CourseDatabaseRepository implements CourseRepository {

	private final CourseMapper courseMapper;
	private final CourseRepositoryDTOMapper dtoMapper;
	
	public CourseDatabaseRepository(
			CourseMapper courseMapper,
			CourseRepositoryDTOMapper dtoMapper) {
		this.courseMapper = courseMapper;
		this.dtoMapper = dtoMapper;
	}
	
	@Override
	public Course create(Course course) {
		CourseDTO dto = dtoMapper.modelToDto(course);
		this.courseMapper.insert(dto);
		return course;
	}
	
	@Override
	public List<Course> findByTeacherId(Long teacherId) {
		List<CourseDTO> dtos = this.courseMapper.findByTeacherId(teacherId);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Course> findByStudentsId(Long studentId) {
		List<CourseDTO> dtos = this.courseMapper.findByStudentId(studentId);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Course> findByCourseClassId(Long courseClassId) {
		Optional<CourseDTO> dto = this.courseMapper.findByCourseClassId(courseClassId);
		return dto.map(dtoMapper::dtoToModel);
	}
	
	@Override
	public List<Course> findByYear(Integer year) {
		List<CourseDTO> dtos = this.courseMapper.findByYear(year);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public Optional<Course> findByCode(String code) {
		Optional<CourseDTO> dto = this.courseMapper.findByCode(code);
		return dto.map(dtoMapper::dtoToModel);
	}
	
	@Override
	public List<Course> findAll() {
		List<CourseDTO> dtos = this.courseMapper.findAll();
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	//TODO: Liskov principle
	@Override
	public Course update(Course obj) {
		throw new UnsupportedOperationException();
	}

	//TODO: Liskov principle
	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	
	@Override
	public Course findById(Long id) {
		CourseDTO dto = this.courseMapper.findById(id);
		return dtoMapper.dtoToModel(dto);
	}

}
