package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.Period;
import com.mvalls.sidged.model.PeriodType;

public interface PeriodRepository extends JpaRepository<Period, Long>{

	Period findByPeriodTypeAndNumber(PeriodType periodType, Integer number);
	
}
