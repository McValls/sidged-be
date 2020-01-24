package com.mvalls.sidged.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teacher")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name = "legacy_number", nullable = true, unique = true)
	private String legacyNumber;
	
	@Column(nullable = false)
	private String names;
	
	@Column(nullable = false)
	private String lastname;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "contact_data_id", nullable = false)
	private ContactData contactData;
	
	@ManyToMany(mappedBy = "teachers")
	private Collection<Course> courses;
	
	public Teacher(String names, String lastname, ContactData contactData) {
		super();
		this.names = names;
		this.lastname = lastname;
		this.contactData = contactData;
	}

}
