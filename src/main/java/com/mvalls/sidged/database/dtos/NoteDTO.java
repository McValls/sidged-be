package com.mvalls.sidged.database.dtos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mvalls.sidged.core.model.Calification;
import com.mvalls.sidged.core.model.NoteType;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

import lombok.Builder;
import lombok.Data;

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
@Entity
@Table(name = "note")
@Data
@Builder
public class NoteDTO implements RepositoryDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "student_id", nullable = false)
	private StudentDTO student;

	@OneToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private TeacherDTO evaluatorTeacher;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private CourseDTO course;
	
	@Column
	private String value;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Calification calification;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private NoteType noteType;
	
	@Column(columnDefinition = "VARCHAR(500) NULL DEFAULT NULL")
	private String observations;
}
