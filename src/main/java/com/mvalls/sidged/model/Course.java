package com.mvalls.sidged.model;

import java.util.HashSet;
import java.util.Set;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Shift shift;
	
	@Column(nullable = false)
	private Integer year;
	
	@ManyToOne
	@JoinColumn(name = "period_id", nullable = false)
	private Period period;
	
	@ManyToOne
	@JoinColumn(name = "time_start_id", nullable = false)
	private Time timeStart;
	
	@ManyToOne
	@JoinColumn(name = "time_end_id", nullable = false)
	private Time timeEnd;
	
	@ManyToOne
	@JoinColumn(name = "career_id", nullable = false)
	private Career career;
	
	@Column(nullable = true)
	private String chair;
	
	@Builder.Default
	@ManyToMany
	@JoinTable(name = "course_teacher",
		joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")},
		uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "teacher_id"})})
	private Set<Teacher> teachers = new HashSet<>();
	
	@Builder.Default
	@ManyToMany
	@JoinTable(name = "course_student",
		joinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
		uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "student_id"})})
	private Set<Student> students = new HashSet<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "course")
	private Set<CourseClass> classes = new HashSet<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "course")
	private Set<Note> note = new HashSet<>();

}
