package com.mvalls.sidged.mappers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentDTO;

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
@Component
public class StudentModelMapper extends GenericMapper<StudentDTO, Student>{

	@Autowired
	private ContactDataModelMapper contactDataModelMapper;
	
	@Override
	public Student map(StudentDTO dto) {
		Student.StudentBuilder builder = Student.builder();
		builder.legacyNumber(dto.getLegacyNumber())
			.id(dto.getId())
			.names(dto.getNames())
			.lastname(dto.getLastname())
			.identificationNumber(dto.getIdentificationNumber())
			.contactData(contactDataModelMapper.map(dto.getContactData()));
		
		if(dto.getBase64EncodedPic() != null) {
			builder.perfilPic(Base64.getDecoder().decode(dto.getBase64EncodedPic()));
		}
		
		return builder.build();
	}
	
}
