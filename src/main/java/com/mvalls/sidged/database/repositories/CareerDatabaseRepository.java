package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.mappers.CareerRepositoryDTOMapper;
import com.mvalls.sidged.database.mybatis.mappers.CareerMapper;
import com.mvalls.sidged.database.repositories.jpa.CareerJpaRepository;

public class CareerDatabaseRepository extends CommonDatabaseRepository<Career, com.mvalls.sidged.database.dtos.CareerDTO, CareerJpaRepository> implements CareerRepository {
	
	private final CareerMapper careerMapper;
	
	public CareerDatabaseRepository(CareerJpaRepository jpaRepository, CareerMapper careerMapper, CareerRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
		this.careerMapper = careerMapper;
	}
	
	@Override
	public List<Career> findAll() {
		List<CareerDTO> dtos = this.careerMapper.findAllCareers();
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}

	@Override
	public Career findByCode(String careerCode) {
		return this.careerMapper.findByCode(careerCode)
				.map(dtoMapper::dtoToModel)
				.orElseThrow(() -> new IllegalArgumentException());
	}
	
}
