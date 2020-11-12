package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.Career;

public interface CareerRepository extends GenericRepository<Career, Long>{

	Career findByCode(String careerCode);

}
