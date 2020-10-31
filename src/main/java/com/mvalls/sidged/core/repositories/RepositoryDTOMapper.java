package com.mvalls.sidged.core.repositories;

public interface RepositoryDTOMapper <T, S extends RepositoryDTO>{

	T dtoToModel(S dto);
	S modelToDto(T model);
	
}
