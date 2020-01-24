package com.mvalls.sidged.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "time")
@Data
public class Time {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name = "since", nullable = false)
	private LocalTime since;
	
	@Column(name = "until", nullable = false)
	private LocalTime until;
	
}
