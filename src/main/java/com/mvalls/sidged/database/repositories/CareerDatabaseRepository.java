package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.mybatis.mappers.CareerMapper;
import com.mvalls.sidged.database.repositories.mappers.CareerRepositoryDTOMapper;

public class CareerDatabaseRepository implements CareerRepository {
	
	private final CareerMapper careerMapper;
	private final CareerRepositoryDTOMapper dtoMapper = new CareerRepositoryDTOMapper();
	
	public CareerDatabaseRepository(CareerMapper careerMapper) {
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

	@Override
	public Career create(Career obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Career update(Career obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Career findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
