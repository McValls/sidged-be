package com.mvalls.sidged.database.repositories;


import com.mvalls.sidged.core.model.Desertor;
import com.mvalls.sidged.core.repositories.DesertorRepository;
import com.mvalls.sidged.database.mybatis.mappers.DesertorMapper;
import com.mvalls.sidged.database.repositories.mappers.DesertorRepositoryDTOMapper;

public class DesertorDataBaseRepository implements DesertorRepository{

    private final DesertorMapper desertorMapper;
	private final DesertorRepositoryDTOMapper dtoMapper = new DesertorRepositoryDTOMapper();

    public DesertorDataBaseRepository(DesertorMapper desertorMapper) {
		this.desertorMapper = desertorMapper;
	}

    @Override
    public Desertor findByCodeAndLanguage(String code, String language) {
		return dtoMapper.dtoToModel(this.desertorMapper.findByCodeAndLanguage(code, language));
	}
    
}
