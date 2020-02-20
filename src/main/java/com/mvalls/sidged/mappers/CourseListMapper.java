package com.mvalls.sidged.mappers;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Course;
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
@Component
public class CourseListMapper extends GenericMapper<Course, CourseListDTO>{
	
	@Override
	public CourseListDTO map(Course course) {
		return CourseListDTO.builder()
				.career(course.getCareer().getName())
				.id(course.getId())
				.name(course.getName())
				.period(PeriodDTO
						.builder()
						.periodType(course.getPeriod().getPeriodType())
						.number(course.getPeriod().getNumber())
						.build())
				.shift(course.getShift())
				.timeSince(course.getTimeStart().getSince().toString())
				.timeUntil(course.getTimeEnd().getUntil().toString())
				.year(course.getYear())
				.build();
	}

}
