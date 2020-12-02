package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.repositories.SubjectRepository;
import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.mybatis.mappers.SubjectMapper;
import com.mvalls.sidged.database.repositories.mappers.SubjectRepositoryDTOMapper;

public class SubjectDatabaseRepository implements SubjectRepository {
	
	private final SubjectMapper subjectMapper;
	private final SubjectRepositoryDTOMapper dtoMapper = new SubjectRepositoryDTOMapper();
	
	public SubjectDatabaseRepository(SubjectMapper subjectMapper) {
		super();
		this.subjectMapper = subjectMapper;
	}

	@Override
	public List<Subject> findAll() {
		List<SubjectDTO> dtos = this.subjectMapper.findAll();
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public Subject findByCode(String code) {
		return dtoMapper.dtoToModel(subjectMapper.findByCode(code));
	}
	
	@Override
	public Subject create(Subject subject) {
		SubjectDTO dto = dtoMapper.modelToDto(subject);
		this.subjectMapper.create(dto);
		return dtoMapper.dtoToModel(dto);
	}

	@Override
	public Subject update(Subject subject) {
		SubjectDTO dto = dtoMapper.modelToDto(subject);
		this.subjectMapper.update(dto);
		return subject;
	}
	
	@Override
	public boolean delete(String code) {
		int deletedRows = this.subjectMapper.delete(code);
		return deletedRows > 0;
	}
}
