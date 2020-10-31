package com.mvalls.sidged.core.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.StudentPresent;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
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
@Service
public class ClassStudentPresentService extends GenericService<ClassStudentPresent, ClassStudentPresentRepository>{

	private final PresentAnalysisCalculator presentAnalysisCalculator;
	
	public ClassStudentPresentService(ClassStudentPresentRepository repository,
			PresentAnalysisCalculator presentAnalysisCalculator) {
		super(repository);
		this.presentAnalysisCalculator = presentAnalysisCalculator;
	}

	@Transactional
	public void updatePresent(Teacher teacher, Long courseClassId, Long studentId, StudentPresent present) throws UnauthorizedUserException {
		ClassStudentPresent classStudentPresent = this.repository.findByCourseClassIdAndStudentId(courseClassId, studentId);
		if(classStudentPresent != null) {
			if(!classStudentPresent.getCourseClass().getCourse().getTeachers().contains(teacher)) throw new UnauthorizedUserException();
			
			classStudentPresent.setPresent(present);
			this.repository.update(classStudentPresent);
		}
	}
	
	public List<PresentismAnalysisData> getPresentismDataByStudentIdAndYear(Long studentId, int year) {
		List<ClassStudentPresent> classStudentPresents = this.repository.findByStudentId(studentId).stream()
				.filter(classStudentPresent -> classStudentPresent.getCourseClass().getDate().getYear() == year)
				.collect(Collectors.toList());
		return presentAnalysisCalculator.getPresentismByStudentGroupedByCourse(classStudentPresents, year);
	}
	
}
