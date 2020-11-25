package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.database.dtos.CourseClassDTO;
import com.mvalls.sidged.database.mybatis.mappers.CourseClassMapper;
import com.mvalls.sidged.database.repositories.mappers.CourseClassRepositoryDTOMapper;

public class CourseClassDatabaseRepository implements CourseClassRepository {
	
	private final CourseClassMapper courseClassMapper;
	private final ClassStudentPresentRepository classStudentPresentRepository;
	private final CourseClassRepositoryDTOMapper dtoMapper = new CourseClassRepositoryDTOMapper();

	public CourseClassDatabaseRepository(CourseClassMapper courseClassMapper, 
			ClassStudentPresentRepository classStudentPresentRepository) {
		this.courseClassMapper = courseClassMapper;
		this.classStudentPresentRepository = classStudentPresentRepository;
	}
	
	@Override
	public List<CourseClass> findByCourseCode(String courseCode) {
		List<CourseClass> classes = this.courseClassMapper.findByCourseCode(courseCode)
			.stream()
			.map(dtoMapper::dtoToModel)
			.collect(Collectors.toList());
		
		for (CourseClass courseClass: classes) {
			List<ClassStudentPresent> studentPresents = this.classStudentPresentRepository.findByCourseAndClassNumber(courseCode, courseClass.getClassNumber());
			courseClass.setStudentPresents(studentPresents);
		}
		
		return classes;
	}
	
	@Override
	public Optional<CourseClass> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber) {
		Optional<CourseClassDTO> dto = this.courseClassMapper.findByCourseCodeAndClassNumber(courseCode, classNumber);
		Optional<CourseClass> optionalCourseClass = dto.map(dtoMapper::dtoToModel);
		
		optionalCourseClass.ifPresent(courseClass -> {
			courseClass.setStudentPresents(this.classStudentPresentRepository.findByCourseAndClassNumber(courseCode, classNumber));
		});
		return optionalCourseClass;
	}

	@Override
	@Transactional
	public CourseClass create(CourseClass courseClass) {
		CourseClassDTO dto = this.dtoMapper.modelToDto(courseClass);
		this.courseClassMapper.insert(dto);
		courseClass.setId(dto.getId());
		
		courseClass.getStudentPresents()
			.stream()
			.forEach(studentPresent -> {
				studentPresent.setCourseClass(courseClass);
				this.classStudentPresentRepository.create(studentPresent);				
			});
		
		return courseClass;
	}

	@Override
	public CourseClass update(CourseClass courseClass) {
		this.courseClassMapper.update(courseClass.getId(), courseClass.getClassState());
		return courseClass;
	}

	@Override
	public List<CourseClass> findAllByStudentId(Long studentId) {
		List<CourseClassDTO> dtos = this.courseClassMapper.findByStudentId(studentId);
		List<CourseClass> classes = dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
		
		for (CourseClass courseClass : classes) {
			List<ClassStudentPresent> studentPresents = 
					this.classStudentPresentRepository.findByCourseAndClassNumber(courseClass.getCourse().getCode(), courseClass.getClassNumber());
			courseClass.setStudentPresents(studentPresents);
		}
		
		return classes;
	}

}
