package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.model.PeriodType;

public interface PeriodRepository {

	Period findByPeriodTypeAndNumber(PeriodType type, Integer number);

}
