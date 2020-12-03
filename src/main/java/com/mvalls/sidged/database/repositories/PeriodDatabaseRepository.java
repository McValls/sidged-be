package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.model.PeriodType;
import com.mvalls.sidged.core.repositories.PeriodRepository;
import com.mvalls.sidged.database.dtos.PeriodDTO;
import com.mvalls.sidged.database.mybatis.mappers.PeriodMapper;

public class PeriodDatabaseRepository implements PeriodRepository {

	private final PeriodMapper periodMapper;
	
	public PeriodDatabaseRepository(PeriodMapper periodMapper) {
		super();
		this.periodMapper = periodMapper;
	}

	@Override
	public Period findByPeriodTypeAndNumber(PeriodType type, Integer number) {
		PeriodDTO dto;
		if (number == null) {
			dto = this.periodMapper.findByPeriodType(type);
		} else {
			dto = this.periodMapper.findByPeriodTypeAndNumber(type, number);
		}
		return Period.builder()
				.id(dto.getId())
				.number(dto.getNumber())
				.periodType(dto.getPeriodType())
				.build();
	}

}
