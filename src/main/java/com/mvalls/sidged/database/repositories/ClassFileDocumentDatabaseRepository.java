package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.database.dtos.ClassFileDocumentDTO;
import com.mvalls.sidged.database.mybatis.mappers.ClassFileDocumentMapper;
import com.mvalls.sidged.database.repositories.mappers.ClassFileDocumentRepositoryDTOMapper;

public class ClassFileDocumentDatabaseRepository implements ClassFileDocumentRepository {
	
	private final ClassFileDocumentMapper classFileDocumentMapper;
	private final ClassFileDocumentRepositoryDTOMapper dtoMapper = new ClassFileDocumentRepositoryDTOMapper();

	public ClassFileDocumentDatabaseRepository(ClassFileDocumentMapper classFileDocumentMapper) {
		super();
		this.classFileDocumentMapper = classFileDocumentMapper;
	}

	@Override
	public ClassFileDocument create(ClassFileDocument classFileDocument) {
		ClassFileDocumentDTO dto = dtoMapper.modelToDto(classFileDocument);
		this.classFileDocumentMapper.insert(dto);
		classFileDocument.setId(dto.getId());
		
		return classFileDocument;
	}
	
	@Override
	public List<ClassFileDocument> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber) {
		List<ClassFileDocumentDTO> dtos = this.classFileDocumentMapper.findByCourseCodeAndClassNumber(courseCode, classNumber);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ClassFileDocument> findByCourseCode(String courseCode) {
		List<ClassFileDocumentDTO> dtos = this.classFileDocumentMapper.findByCourseCode(courseCode);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	@Override
	public ClassFileDocument findById(Long id) {
		Optional<ClassFileDocumentDTO> dto = this.classFileDocumentMapper.findById(id);
		return dto.map(dtoMapper::dtoToModel).orElseThrow();
	}

}
