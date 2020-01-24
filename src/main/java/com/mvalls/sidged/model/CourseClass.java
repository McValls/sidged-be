package com.mvalls.sidged.model;

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
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class")
@Data
@Builder
@EqualsAndHashCode(exclude = {"course"})
@NoArgsConstructor
@AllArgsConstructor
public class CourseClass {

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
	
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	@Transient
	@Builder.Default
	private Collection<Student> inscribedStudents = new ArrayList<>();
	
	@OneToMany(mappedBy = "courseClass", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@Builder.Default
	private Collection<ClassStudentPresent> studentPresents = new ArrayList<>();
	
	@OneToMany(mappedBy = "courseClass")
	@Builder.Default
	private Collection<ClassFileDocument> classFileDocuments = new ArrayList<>();
	
	@Column(name = "comments", nullable = true)
	private String comments;

}
