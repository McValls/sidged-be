package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.model.PeriodType;
import com.mvalls.sidged.core.repositories.PeriodRepository;
import com.mvalls.sidged.database.mappers.PeriodRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.PeriodJpaRepository;

public class PeriodDatabaseRepository extends CommonDatabaseRepository<Period, com.mvalls.sidged.database.dtos.PeriodDTO, PeriodJpaRepository>
	implements PeriodRepository {

	public PeriodDatabaseRepository(PeriodJpaRepository jpaRepository, PeriodRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
	@Override
	public Period findByPeriodTypeAndNumber(PeriodType type, Integer number) {
		return this.dtoMapper.dtoToModel(this.jpaRepository.findByPeriodTypeAndNumber(type, number));
	}
}
