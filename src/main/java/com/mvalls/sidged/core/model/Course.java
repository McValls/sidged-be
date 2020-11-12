package com.mvalls.sidged.core.model;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Model {
	
	private Long id;
	private String code;
	private String name;
	private Shift shift;
	private Integer year;
	private Period period;
	private Time timeStart;
	private Time timeEnd;
	private String chair;
	private Career career;
	@Builder.Default private Set<Teacher> teachers = new HashSet<>();
	@Builder.Default private Set<Student> students = new HashSet<>();
	@Builder.Default private Set<CourseClass> classes = new HashSet<>();
	@Builder.Default private Set<Note> notes = new HashSet<>();

}
