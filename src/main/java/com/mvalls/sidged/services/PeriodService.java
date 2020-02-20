package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.Period;
import com.mvalls.sidged.model.PeriodType;
import com.mvalls.sidged.repositories.PeriodRepository;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
@Service
public class PeriodService extends GenericService<Period, PeriodRepository>{

	@Autowired
	private PeriodRepository periodRepository;
	
	public Period findByTypeAndNumber(PeriodType type, Integer number) {
		return periodRepository.findByPeriodTypeAndNumber(type, number);
	}
	
	@Override
	protected PeriodRepository getRepository() {
		return periodRepository;
	}
	
}
