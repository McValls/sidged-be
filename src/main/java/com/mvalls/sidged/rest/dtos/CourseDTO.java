package com.mvalls.sidged.rest.dtos;

import com.mvalls.sidged.core.model.PeriodType;
import com.mvalls.sidged.core.model.Shift;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO extends DataTransferObject {
	
	private String name;
	private String courseCode;
	private Shift shift;
	private Integer year;
	private PeriodType periodType;
	private Integer periodNumber;
	private Long timeSinceId;
	private Long timeUntilId;
	private String careerCode;
	private String chair;

}
	