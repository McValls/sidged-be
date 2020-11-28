package com.mvalls.sidged.rest.mappers;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.rest.dtos.StudentAllDTO;

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
public class StudentAllMapper {
	
	private final ContactDataMapper contactDataMapper = new ContactDataMapper();
	
	public StudentAllDTO map(Student student) {
		return StudentAllDTO.builder()
				.id(student.getId())
				.names(student.getNames())
				.lastname(student.getLastname())
				.identificationNumber(student.getIdentificationNumber())
				.legacyNumber(student.getLegacyNumber())
				.contactData(contactDataMapper.map(student.getContactData()))
				.build();
	}
	
}
