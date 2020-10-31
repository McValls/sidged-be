package com.mvalls.sidged.core.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Data
@Builder
@EqualsAndHashCode(exclude = {"course"})
@NoArgsConstructor
@AllArgsConstructor
public class CourseClass {

	private Long id;
	private Integer classNumber;
	private LocalDate date;
	private ClassState classState;
	private Course course;
	@Builder.Default
	private Collection<Student> inscribedStudents = new ArrayList<>();
	@Builder.Default
	private Collection<ClassStudentPresent> studentPresents = new ArrayList<>();
	@Builder.Default
	private Collection<ClassFileDocument> classFileDocuments = new ArrayList<>();
	
	@Column(name = "comments", nullable = true)
	private String comments;

}
