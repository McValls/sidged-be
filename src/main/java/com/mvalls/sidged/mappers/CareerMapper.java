package com.mvalls.sidged.mappers;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.rest.dtos.CareerDTO;

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
public class CareerMapper extends GenericMapper<Career, CareerDTO>{

	@Override
	public CareerDTO map(Career career) {
		CareerDTO dto = CareerDTO.builder()
				.code(career.getCode())
				.name(career.getName())
				.build();
		return dto;
	}
	
}
