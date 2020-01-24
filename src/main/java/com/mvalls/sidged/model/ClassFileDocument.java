package com.mvalls.sidged.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_file_document")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassFileDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "file_document_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private FileDocumentType fileDocumentType;
	
	@Lob
	@Column(name = "content", nullable = false)
	private byte[] content;
	
	@Column(name = "contentType", nullable = true)
	private String contentType;
	
	@ManyToOne
	@JoinColumn(name = "class_id")
	private CourseClass courseClass;
	
}
