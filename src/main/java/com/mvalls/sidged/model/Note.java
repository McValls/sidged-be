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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "note")
@Data
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	@OneToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@OneToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private Teacher evaluatorTeacher;
	
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
