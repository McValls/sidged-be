package com.mvalls.sidged.database.repositories;

import com.mvalls.sidged.core.model.Time;
import com.mvalls.sidged.core.repositories.TimeRepository;
import com.mvalls.sidged.database.mappers.TimeRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.jpa.TimeJpaRepository;

public class TimeDatabaseRepository extends CommonDatabaseRepository<Time, com.mvalls.sidged.database.dtos.TimeDTO, TimeJpaRepository>
	implements TimeRepository {

	public TimeDatabaseRepository(TimeJpaRepository jpaRepository, TimeRepositoryDTOMapper dtoMapper) {
		super(jpaRepository, dtoMapper);
	}
	
}
