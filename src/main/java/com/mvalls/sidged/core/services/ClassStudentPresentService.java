package com.mvalls.sidged.core.services;

import java.util.List;
import java.util.Optional;

import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;

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
public class ClassStudentPresentService {

	private final TeacherRepository teacherRepository;
	private final ClassStudentPresentRepository classStudentPresentRepository;
	
	public ClassStudentPresentService(ClassStudentPresentRepository classStudentPresentRepository,
			TeacherRepository teacherRepository) {
		this.classStudentPresentRepository = classStudentPresentRepository;
		this.teacherRepository = teacherRepository;
	}

	public void updatePresent(Teacher teacher, String courseCode, Integer classNumber, 
			Long studentId, StudentPresent present) throws UnauthorizedUserException {
		List<Teacher> teachersByCourse = this.teacherRepository.findByCourseCode(courseCode);
		if (!teachersByCourse.contains(teacher)) throw new UnauthorizedUserException();
		
		Optional<ClassStudentPresent> classStudentPresent = 
				this.classStudentPresentRepository.findByCourseCodeAndClassNumberAndStudentId(courseCode, classNumber, studentId);
		
		classStudentPresent.ifPresent(studentPresent -> {
			studentPresent.setPresent(present);
			this.classStudentPresentRepository.update(studentPresent);
		});
	}
	
}
