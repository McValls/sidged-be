package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.database.dtos.ClassStudentPresentDTO;
import com.mvalls.sidged.database.mappers.ClassStudentPresentRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.ClassStudentPresentJpaRepository;

public class ClassStudentPresentDatabaseRepository 
	extends CommonDatabaseRepository<ClassStudentPresent, ClassStudentPresentDTO, ClassStudentPresentJpaRepository> 
	implements ClassStudentPresentRepository {

	public ClassStudentPresentDatabaseRepository(ClassStudentPresentJpaRepository jpaRepository,
			ClassStudentPresentRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
	@Override
	public List<ClassStudentPresent> findByStudentId(Long studentId) {
		return this.jpaRepository.findByStudentId(studentId)
				.stream()
				.map(this.dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

}
