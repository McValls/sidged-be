package com.mvalls.sidged.database.mappers;

import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.CourseDTO;

public class CourseRepositoryDTOMapper implements RepositoryDTOMapper<Course, CourseDTO> {

	private final PeriodRepositoryDTOMapper periodDTOMapper = new PeriodRepositoryDTOMapper();
	private final TimeRepositoryDTOMapper timeDTOMapper = new TimeRepositoryDTOMapper();
	private final TeacherRepositoryDTOMapper teacherDTOMapper = new TeacherRepositoryDTOMapper();
	private final StudentRepositoryDTOMapper studentDTOMapper = new StudentRepositoryDTOMapper();
	private final NoteRepositoryDTOMapper noteDTOMapper = new NoteRepositoryDTOMapper();
	private final CourseClassRepositoryDTOMapper courseClassDTOMapper = new CourseClassRepositoryDTOMapper();
	
	@Override
	public Course dtoToModel(CourseDTO dto) {
		return Course.builder()
				.id(dto.getId())
				.name(dto.getName())
				.shift(dto.getShift())
				.year(dto.getYear())
				.period(periodDTOMapper.dtoToModel(dto.getPeriod()))
				.timeStart(timeDTOMapper.dtoToModel(dto.getTimeStart()))
				.timeEnd(timeDTOMapper.dtoToModel(dto.getTimeEnd()))
				.chair(dto.getChair())
				.classes(dto.getClasses()
						.stream()
						.map(courseClassDTOMapper::dtoToModel)
						.collect(Collectors.toSet()))
				.teachers(dto.getTeachers()
						.stream()
						.map(teacherDTOMapper::dtoToModel)
						.collect(Collectors.toSet()))
				.students(dto.getStudents()
						.stream()
						.map(studentDTOMapper::dtoToModel)
						.collect(Collectors.toSet()))
				.notes(dto.getNote()
						.stream()
						.map(noteDTOMapper::dtoToModel)
						.collect(Collectors.toSet()))
				.build();
				
	}

	@Override
	public CourseDTO modelToDto(Course model) {
		return CourseDTO.builder()
				.id(model.getId())
				.name(model.getName())
				.shift(model.getShift())
				.year(model.getYear())
				.period(periodDTOMapper.modelToDto(model.getPeriod()))
				.timeStart(timeDTOMapper.modelToDto(model.getTimeStart()))
				.timeEnd(timeDTOMapper.modelToDto(model.getTimeEnd()))
				.chair(model.getChair())
				.classes(model.getClasses()
						.stream()
						.map(courseClassDTOMapper::modelToDto)
						.collect(Collectors.toSet()))
				.teachers(model.getTeachers()
						.stream()
						.map(teacherDTOMapper::modelToDto)
						.collect(Collectors.toSet()))
				.students(model.getStudents()
						.stream()
						.map(studentDTOMapper::modelToDto)
						.collect(Collectors.toSet()))
				.note(model.getNotes()
						.stream()
						.map(noteDTOMapper::modelToDto)
						.collect(Collectors.toSet()))
				.build();
	}

}
