package com.mvalls.sidged.database.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	
	@ManyToOne
	@JoinColumn(name = "period_id", nullable = false)
	private PeriodDTO period;
	
	@ManyToOne
	@JoinColumn(name = "time_start_id", nullable = false)
	private TimeDTO timeStart;
	
	@ManyToOne
	@JoinColumn(name = "time_end_id", nullable = false)
	private TimeDTO timeEnd;
	
	@Column(nullable = true)
	private String chair;
	
	@Column(name = "career_code", nullable = false)
	private String careerCode;
	
	@Builder.Default
	@ManyToMany
	@JoinTable(name = "course_teacher",
		joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")},
		uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "teacher_id"})})
	private Set<TeacherDTO> teachers = new HashSet<>();
	
	@Builder.Default
	@ManyToMany
	@JoinTable(name = "course_student",
		joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
		uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "student_id"})})
	private Set<StudentDTO> students = new HashSet<>();
	
	@Builder.Default
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "course_classes", 
			joinColumns = {@JoinColumn(name = "coursedto_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "classes_id", referencedColumnName = "id", insertable = false, updatable = true)})
	private Set<CourseClassDTO> classes = new HashSet<>();
	
	@Builder.Default
	@OneToMany
	private Set<NoteDTO> note = new HashSet<>();

}
