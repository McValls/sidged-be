package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.database.dtos.CourseDTO;
import com.mvalls.sidged.database.mappers.CourseRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.CourseJpaRepository;

public class CourseDatabaseRepository extends CommonDatabaseRepository<Course, com.mvalls.sidged.database.dtos.CourseDTO, CourseJpaRepository>
	implements CourseRepository {

	public CourseDatabaseRepository(CourseJpaRepository jpaRepository, 
			CourseRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
	@Override
	public List<Course> findByTeachersId(Long teacherId) {
		return this.jpaRepository.findByTeachersId(teacherId)
				.stream()
				.map(this.dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Course> findByStudentsId(Long studentId) {
		return this.jpaRepository.findByStudentsId(studentId)
				.stream()
				.map(this.dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	@Override
	public Course findByCourseClassId(Long courseClassId) {
		CourseDTO courseDTO = this.jpaRepository.findByClassesId(courseClassId);
		if (courseDTO == null) {
			throw new IllegalStateException();
		}
		return dtoMapper.dtoToModel(courseDTO);
	}
	
	@Override
	public List<Course> findByYear(Integer year) {
		return this.jpaRepository.findByYear(year)
				.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

}
