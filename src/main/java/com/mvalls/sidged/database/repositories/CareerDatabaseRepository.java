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
	public Career findById(Long id) {
		return dtoMapper.dtoToModel(this.careerMapper.findCareerById(id));
	}

	@Override
	public Career create(Career career) {
		CareerDTO dto = this.dtoMapper.modelToDto(career);
		this.careerMapper.insert(dto);
		career.setId(dto.getId());
		return career;
	}
	
	@Override
	public Career update(Career career) {
		CareerDTO dto = this.dtoMapper.modelToDto(career);
		this.careerMapper.update(dto);
		return career;
	}
	
	@Override
	public boolean delete(Career career) {
		int deletedRows = this.careerMapper.delete(career.getId());
		return deletedRows == 1;
	}

	@Override
	public List<Career> findByStudentIdentificationNumber(String studentIdentificationNumber) {
		List<CareerDTO> dtos = this.careerMapper.findByStudentIdentificationNumber(studentIdentificationNumber);
		return dtos.stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
	}
	
}
