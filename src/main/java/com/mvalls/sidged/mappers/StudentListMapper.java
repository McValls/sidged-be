package com.mvalls.sidged.mappers;

import java.util.Base64;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.rest.dtos.StudentListDTO;

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
