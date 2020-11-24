package com.mvalls.sidged.database.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mvalls.sidged.core.model.ClassState;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

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
@Entity
@Table(name = "class")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CourseClassDTO implements RepositoryDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name = "class_number", nullable = false)
	private Integer classNumber;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(name = "class_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private ClassState classState;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "courseClass")
	@Builder.Default
	private Collection<ClassStudentPresentDTO> studentPresents = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "courseClass")
	@Builder.Default
	private Collection<ClassFileDocumentDTO> classFileDocuments = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private CourseDTO course;
	
	@Column(name = "comments", nullable = true)
	private String comments;

}
