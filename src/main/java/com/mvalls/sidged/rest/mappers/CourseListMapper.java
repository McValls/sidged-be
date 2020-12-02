package com.mvalls.sidged.rest.mappers;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.rest.dtos.CourseListDTO;
import com.mvalls.sidged.rest.dtos.PeriodDTO;

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
public class CourseListMapper {
	
	public CourseListDTO map(Course course) {
		return CourseListDTO.builder()
				.code(course.getCode())
				.name(course.getName())
				.period(PeriodDTO
						.builder()
						.periodType(course.getPeriod().getPeriodType())
						.number(course.getPeriod().getNumber())
						.build())
				.shift(course.getShift())
				.timeSince(course.getTimeStart().getSince().toString())
				.timeUntil(course.getTimeEnd().getUntil().toString())
				.career(course.getSubject().getCareer().getName())
				.year(course.getYear())
				.build();
	}

}
