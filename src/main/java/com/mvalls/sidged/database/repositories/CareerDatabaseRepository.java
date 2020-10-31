package com.mvalls.sidged.database.repositories;

import org.springframework.stereotype.Repository;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.database.mappers.CareerRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.CareerJpaRepository;

@Repository
public class CareerDatabaseRepository extends CommonDatabaseRepository<Career, com.mvalls.sidged.database.dtos.CareerDTO, CareerJpaRepository> implements CareerRepository {
	
	
	public CareerDatabaseRepository(CareerJpaRepository jpaRepository, CareerRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}

}
