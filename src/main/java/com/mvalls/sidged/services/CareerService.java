package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.Career;
import com.mvalls.sidged.repositories.CareerRepository;

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
public class CareerService extends GenericService<Career, CareerRepository>{

	@Autowired
	private CareerRepository careerRepository;
	
	@Override
	protected CareerRepository getRepository() {
		return careerRepository;
	}
	
}
