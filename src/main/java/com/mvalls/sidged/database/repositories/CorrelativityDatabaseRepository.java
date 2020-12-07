package com.mvalls.sidged.database.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.model.correlativity.Correlativity;
import com.mvalls.sidged.core.repositories.CorrelativityRepository;
import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.dtos.SubjectDependenciesDTO;
import com.mvalls.sidged.database.mybatis.mappers.SubjectDependenciesMapper;
import com.mvalls.sidged.database.repositories.mappers.SubjectRepositoryDTOMapper;

public class CorrelativityDatabaseRepository implements CorrelativityRepository {

	private final SubjectDependenciesMapper subjectDependenciesMapper;
	private final SubjectRepositoryDTOMapper dtoMapper = new SubjectRepositoryDTOMapper();

	public CorrelativityDatabaseRepository(SubjectDependenciesMapper subjectDependenciesMapper) {
		this.subjectDependenciesMapper = subjectDependenciesMapper;
	}

	@Override
	public List<Correlativity> findAllByCareerCode(String careerCode) {
		List<SubjectDependenciesDTO> subjectDependenciesList = 
				this.subjectDependenciesMapper.findByCareerCode(careerCode);
		
		return subjectDependenciesList.stream()
			.map(this::mapCorrelativity)
			.collect(Collectors.toList());
	}
	
	@Override
	public Optional<Correlativity> findBySubjectCode(String subjectCode) {
		return this.subjectDependenciesMapper.findBySubjectCode(subjectCode)
				.map(this::mapCorrelativity);
	}
	
	@Override
	public Correlativity addCorrelativity(Subject subject, Subject newCorrelativeSubject) {
		SubjectDTO subjectDTO = dtoMapper.modelToDto(subject);
		SubjectDTO newCorrelativeSubjectDTO = dtoMapper.modelToDto(newCorrelativeSubject);
		
		Optional<SubjectDependenciesDTO> subjectDependenciesDTO = 
				this.subjectDependenciesMapper.findBySubjectId(subjectDTO);
		
		return subjectDependenciesDTO
			.map(dto -> updateDependencies(dto, newCorrelativeSubjectDTO))
			.orElseGet(() -> createDependency(subjectDTO, newCorrelativeSubjectDTO));
	}
	
	private Correlativity updateDependencies(SubjectDependenciesDTO dto, SubjectDTO newCorrelativeSubjectDTO) {
		var updatedDTO = new SubjectDependenciesDTO(dto.getSubject(), 
				Stream
				.of(dto.getDependencies(), List.of(newCorrelativeSubjectDTO))
				.flatMap(Collection::stream)
				.distinct()
				.collect(Collectors.toList()));
		this.subjectDependenciesMapper.update(updatedDTO);
		return mapCorrelativity(updatedDTO);
	}
	
	private Correlativity createDependency(SubjectDTO subjectDTO, SubjectDTO newCorrelativeSubjectDTO) {
		var newDTO = new SubjectDependenciesDTO(subjectDTO, List.of(newCorrelativeSubjectDTO));
		this.subjectDependenciesMapper.insert(newDTO);
		return mapCorrelativity(newDTO);
	}
	
	@Override
	public Correlativity deleteCorrelativity(Subject subject, Subject correlativeToDelete) {
		SubjectDTO subjectDTO = dtoMapper.modelToDto(subject);
		SubjectDTO correlativeToDeleteDTO = dtoMapper.modelToDto(correlativeToDelete);
		
		Optional<SubjectDependenciesDTO> subjectDependenciesDTO = 
				this.subjectDependenciesMapper.findBySubjectId(subjectDTO);
		
		SubjectDependenciesDTO updatedDTO = subjectDependenciesDTO
				.map(dto -> new SubjectDependenciesDTO(dto.getSubject(), 
						dto.getDependencies().stream()
						.filter(dep -> !dep.equals(correlativeToDeleteDTO))
						.collect(Collectors.toList())))
				.orElseGet(() -> new SubjectDependenciesDTO(subjectDTO, List.of()));
		
		this.subjectDependenciesMapper.update(updatedDTO);
		return mapCorrelativity(updatedDTO);
	}
	
	private Correlativity mapCorrelativity(SubjectDependenciesDTO subjectDependencies) {
		return new Correlativity(
				mapSubject(subjectDependencies.getSubject()),
				mapDependencies(subjectDependencies.getDependencies()));
	}

	
	private List<Correlativity> mapDependencies(List<SubjectDTO> dependencies) {
		if (dependencies == null || dependencies.isEmpty()) {
			return List.of();
		}
		return dependencies.stream()
			.map(this::findBySubjectIdOrDefault)
			.map(this::mapCorrelativity)
			.collect(Collectors.toList());
	}

	private Subject mapSubject(SubjectDTO subjectDTO) {
		return dtoMapper.dtoToModel(subjectDTO);
	}
	
	private SubjectDependenciesDTO findBySubjectIdOrDefault(SubjectDTO subjectDTO) {
		return this.subjectDependenciesMapper.findBySubjectId(subjectDTO)
				.orElseGet(() -> new SubjectDependenciesDTO(subjectDTO, List.of()));
	}
	
}
