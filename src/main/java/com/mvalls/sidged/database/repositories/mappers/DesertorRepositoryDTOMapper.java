package com.mvalls.sidged.database.repositories.mappers;

import com.mvalls.sidged.core.model.Desertor;
import com.mvalls.sidged.core.repositories.RepositoryDTOMapper;
import com.mvalls.sidged.database.dtos.DesertorDTO;



public class DesertorRepositoryDTOMapper implements RepositoryDTOMapper<Desertor, DesertorDTO>{

    @Override
    public Desertor dtoToModel(DesertorDTO dto) {
		return Desertor.builder()
				.id(dto.getId())
				.text(dto.getText())
				.language(dto.getLanguage())
				.build();
    }

    @Override
    public DesertorDTO modelToDto(Desertor model) {
        return DesertorDTO.builder()
        .id(model.getId())
        .text(model.getText())
        .language(model.getLanguage())
        .build();
    }
    
}
