package com.mvalls.sidged.core.services;

import java.util.List;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;

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
public class CareerService {

	private final CareerRepository careerRepository;
	
	public CareerService(CareerRepository careerRepository) {
		this.careerRepository = careerRepository;
	}
	
	public List<Career> findAll() {
		return this.careerRepository.findAll();
	}

	public Career create(Career career) {
		return this.careerRepository.create(career);
	}

	public Career update(Long id, String newName) {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null");
		}
		Career careerToUpdate = this.findById(id);
		
		return this.careerRepository.update(Career.builder()
				.id(id)
				.name(newName)
				.code(careerToUpdate.getCode())
				.build());
	}

	public boolean delete(Career career) {
		if (career == null || career.getId() == null) {
			throw new IllegalArgumentException("Nor career or id can be null");
		}
		return this.careerRepository.delete(career);
	}

	public Career findById(Long id) {
		return this.careerRepository.findById(id);
	}

	public List<Career> findByStudentIdentificationNumber(String studentIdentificationNumber) {
		return this.careerRepository.findByStudentIdentificationNumber(studentIdentificationNumber);
	}
	
}
