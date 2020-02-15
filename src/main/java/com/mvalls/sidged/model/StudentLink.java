package com.mvalls.sidged.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "student_link")
@Data
public class StudentLink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column	
	private Long id;
	
	@Column
	private String link;
	
	@Column
	private String title;
	

}
