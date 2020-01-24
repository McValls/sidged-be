package com.mvalls.sidged.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_student_present")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassStudentPresent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private CourseClass courseClass;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	@Column(name = "present")
	@Enumerated(EnumType.STRING)
	private StudentPresent present;

}
