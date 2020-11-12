package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.database.mappers.CareerRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.CareerJpaRepository;

public class CareerDatabaseRepository extends CommonDatabaseRepository<Career, com.mvalls.sidged.database.dtos.CareerDTO, CareerJpaRepository> implements CareerRepository {
	
	public CareerDatabaseRepository(CareerJpaRepository jpaRepository, CareerRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

	@Override
	public Career findByCode(String careerCode) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.findByCode(careerCode));
	}
	
}
