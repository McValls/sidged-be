package com.mvalls.sidged.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "legacy_number", unique = true, nullable = true)
	private String legacyNumber;

	@Column(nullable = false)
	private String names;

	@Column(nullable = false)
	private String lastname;

	@Column(name = "identification_number", nullable = false, unique = true)
	private String identificationNumber;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "contact_data_id", nullable = false)
	private ContactData contactData;

	@Column(name = "perfil_pic", nullable = true)
	private byte[] perfilPic;

}
