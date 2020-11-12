package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.Model;

public interface RepositoryDTOMapper <T extends Model, S extends RepositoryDTO>{

	T dtoToModel(S dto);
	S modelToDto(T model);
	
}
