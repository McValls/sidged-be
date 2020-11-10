package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.Identifiable;

public interface RepositoryDTOMapper <T extends Identifiable, S extends RepositoryDTO>{

	T dtoToModel(S dto);
	S modelToDto(T model);
	
}
