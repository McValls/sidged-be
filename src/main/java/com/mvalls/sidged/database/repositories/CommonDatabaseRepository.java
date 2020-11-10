package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.core.model.Identifiable;
import com.mvalls.sidged.core.repositories.GenericRepository;
import com.mvalls.sidged.core.repositories.RepositoryDTO;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;

public abstract class CommonDatabaseRepository<T extends Identifiable, S extends RepositoryDTO, U extends JpaRepository<S, Long>> implements GenericRepository<T, Long> {

	protected final U jpaRepository;
	protected final RepositoryDTOMapper<T, S> dtoMapper;

	public CommonDatabaseRepository(U jpaRepository, RepositoryDTOMapper<T, S> dtoMapper) {
		super();
		this.jpaRepository = jpaRepository;
		this.dtoMapper = dtoMapper;
	}

	@Override
	public T create(T obj) {
		S repositoryDTO = this.dtoMapper.modelToDto(obj);
		this.jpaRepository.save(repositoryDTO);
		
		obj.setId(repositoryDTO.getId());
		return obj;
	}

	@Override
	public T update(T obj) {
		S repositoryDTO = this.dtoMapper.modelToDto(obj);
		this.jpaRepository.save(repositoryDTO);
		
		return this.dtoMapper.dtoToModel(repositoryDTO);
	}
	
	@Override
	public void delete(Long id) {
		this.jpaRepository.deleteById(id);;
	}

	@Override
	public T findById(Long id) {
		return jpaRepository.findById(id).map(dtoMapper::dtoToModel).orElseThrow();
	}

	@Override
	public List<T> findAll() {
		return jpaRepository.findAll().stream().map(dtoMapper::dtoToModel).collect(Collectors.toList());
	}

}
