package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.database.dtos.CourseDTO;
import com.mvalls.sidged.database.mappers.CourseRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.CourseJpaRepository;

public class CourseDatabaseRepository extends CommonDatabaseRepository<Course, com.mvalls.sidged.database.dtos.CourseDTO, CourseJpaRepository>
	implements CourseRepository {

	private final CareerRepository careerRepository;
	
	public CourseDatabaseRepository(CourseJpaRepository jpaRepository,
			CareerRepository careerRepository,
			CourseRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
		this.careerRepository = careerRepository;
	}
	
	@Override
	public List<Course> findByTeachersId(Long teacherId) {
		return this.jpaRepository.findByTeachersId(teacherId)
				.stream()
				.map(this::getCourseWithCareer)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Course> findByStudentsId(Long studentId) {
		return this.jpaRepository.findByStudentsId(studentId)
				.stream()
				.map(this::getCourseWithCareer)
				.collect(Collectors.toList());
	}

	@Override
	public Course findByCourseClassId(Long courseClassId) {
		CourseDTO courseDTO = this.jpaRepository.findByClassesId(courseClassId);
		if (courseDTO == null) {
			throw new IllegalStateException();
		}
		return this.getCourseWithCareer(courseDTO);
	}
	
	@Override
	public List<Course> findByYear(Integer year) {
		return this.jpaRepository.findByYear(year)
				.stream()
				.map(this::getCourseWithCareer)
				.collect(Collectors.toList());
	}
	
	@Override
	public Course findByCode(String code) {
		CourseDTO dto = this.jpaRepository.findByCode(code);
		return this.getCourseWithCareer(dto);
	}
	
	@Override
	public List<Course> findAll() {
		List<CourseDTO> dtos = this.jpaRepository.findAll();
		return dtos.stream()
			.map(this::getCourseWithCareer)
			.collect(Collectors.toList());
	}
	
	private Course getCourseWithCareer(CourseDTO dto) {
		Course course = dtoMapper.dtoToModel(dto);
		Career career = this.careerRepository.findByCode(dto.getCareerCode());
		course.setCareer(career);
		return course;
	}

}
