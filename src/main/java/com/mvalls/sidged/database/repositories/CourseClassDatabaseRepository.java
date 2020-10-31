package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.database.mappers.CourseClassRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.CourseClassJpaRepository;

public class CourseClassDatabaseRepository extends CommonDatabaseRepository<CourseClass, com.mvalls.sidged.database.dtos.CourseClass, CourseClassJpaRepository>
	implements CourseClassRepository {
	
	public CourseClassDatabaseRepository(CourseClassJpaRepository jpaRepository, CourseClassRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

	@Override
	public List<CourseClass> findByCourseYearAndClassState(int year, ClassState classState) {
		return this.jpaRepository.findByCourseYearAndClassStateOrderByCourseIdAscClassNumberDesc(year, classState)
				.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
				
	}

	@Override
	public CourseClass findByCourseIdAndId(Long courseId, Long classId) {
		return dtoMapper.dtoToModel(this.jpaRepository.findByCourseIdAndId(courseId, classId));
				
	}
	
}
