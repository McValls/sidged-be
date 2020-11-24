package com.mvalls.sidged.database.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mvalls.sidged.core.model.Shift;
import com.mvalls.sidged.core.repositories.RepositoryDTO;

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
@Entity
@Table(name = "course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class CourseDTO implements RepositoryDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Shift shift;
	
	@Column(nullable = false)
	private Integer year;
	
	private Long periodId;
	
	private Long timeStartId;
	
	private Long timeEndId;
	
	@Transient 
	private String careerCode;
	
	@Transient
	@Builder.Default private Set<Long> teachersIds = new HashSet<>();
	
	@Transient
	@Builder.Default private Set<Long> studentsIds = new HashSet<>();
	
	@Transient
	@Builder.Default private Set<Long> coursesClassesIds = new HashSet<>();
	
	private Long careerId;
	
	@Builder.Default
	@OneToMany(mappedBy = "course")
	private Set<NoteDTO> note = new HashSet<>();

}
