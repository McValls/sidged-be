package com.mvalls.sidged.mappers;

import java.util.Base64;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.rest.dtos.StudentListDTO;

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
public class StudentListMapper extends GenericMapper<Student, StudentListDTO>{

	@Override
	public StudentListDTO map(Student student) {
		StudentListDTO.StudentListDTOBuilder builder = StudentListDTO.builder();
		builder.names(student.getNames())
				.lastname(student.getLastname());
		
		if(student.getPerfilPic() != null) {
			builder.base64EncodedPic(Base64.getEncoder().encodeToString(student.getPerfilPic()));
		}
				
		return builder.build();
	}
	
}
