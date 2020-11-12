package com.mvalls.sidged.mappers;

import com.mvalls.sidged.rest.dtos.CourseDTO;
import com.mvalls.sidged.valueObjects.CourseVO;

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
public class CourseDTOtoVOMapper extends GenericMapper<CourseDTO, CourseVO>{

	@Override
	public CourseVO map(CourseDTO dto) {
		CourseVO valueObject = CourseVO.builder()
				.name(dto.getName())
				.code(dto.getCourseCode())
				.shift(dto.getShift())
				.year(dto.getYear())
				.periodNumber(dto.getPeriodNumber())
				.periodType(dto.getPeriodType())
				.timeSinceId(dto.getTimeSinceId())
				.timeUntilId(dto.getTimeUntilId())
				.careerCode(dto.getCareerCode())
				.chair(dto.getChair())
				.build();
		return valueObject;
	}
	
}
