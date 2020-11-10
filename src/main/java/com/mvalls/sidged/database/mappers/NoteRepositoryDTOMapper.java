package com.mvalls.sidged.database.mappers;

import com.mvalls.sidged.core.model.Note;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.NoteDTO;

public class NoteRepositoryDTOMapper implements RepositoryDTOMapper<Note, NoteDTO>{

	private final StudentRepositoryDTOMapper studentDTOMapper = new StudentRepositoryDTOMapper();
	private final TeacherRepositoryDTOMapper teacherDTOMapper = new TeacherRepositoryDTOMapper();
	
	@Override
	public Note dtoToModel(NoteDTO dto) {
		return Note.builder()
				.id(dto.getId())
				.student(studentDTOMapper.dtoToModel(dto.getStudent()))
				.evaluatorTeacher(teacherDTOMapper.dtoToModel(dto.getEvaluatorTeacher()))
				.value(dto.getValue())
				.calification(dto.getCalification())
				.noteType(dto.getNoteType())
				.observations(dto.getObservations())
				.build();
	}

	@Override
	public NoteDTO modelToDto(Note model) {
		return NoteDTO.builder()
				.id(model.getId())
				.student(studentDTOMapper.modelToDto(model.getStudent()))
				.evaluatorTeacher(teacherDTOMapper.modelToDto(model.getEvaluatorTeacher()))
				.value(model.getValue())
				.calification(model.getCalification())
				.noteType(model.getNoteType())
				.observations(model.getObservations())
				.build();
	}

}
