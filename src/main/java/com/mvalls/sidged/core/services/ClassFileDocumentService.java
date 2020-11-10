package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.FileDocumentType;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.valueObjects.ClassFileDocumentVO;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class ClassFileDocumentService extends GenericService<ClassFileDocument, ClassFileDocumentRepository>{
	
	private final CourseClassRepository courseClassRepository;
	private final CourseRepository courseRepository;
	

	public ClassFileDocumentService(ClassFileDocumentRepository repository,
			CourseClassRepository courseClassRepository,
			CourseRepository courseRepository) {
		super(repository);
		this.courseClassRepository = courseClassRepository;
		this.courseRepository = courseRepository;
	}
	
	
	public Collection<ClassFileDocument> findByCourseClassId(Long classId) {
		return this.courseClassRepository.findById(classId).getClassFileDocuments();
	}

	public Collection<ClassFileDocument> findByCourseId(Long courseId) {
		return this.courseRepository.findById(courseId)
				.getClasses().stream()
					.map(courseClass -> courseClass.getClassFileDocuments())
					.flatMap(Collection::stream)
					.collect(Collectors.toList());
	}
	
	@Transactional
	public void saveFileDocument(Long classId, ClassFileDocumentVO valueObject) {
		CourseClass courseClass = this.courseClassRepository.findById(classId);
		log.info("Saving file " + valueObject.getName() + " for class with id: " + courseClass.getId());
		ClassFileDocument fileDocument = ClassFileDocument.builder()
				.name(valueObject.getName())
				.content(valueObject.getContent())
				.contentType(valueObject.getContentType())
				.fileDocumentType(FileDocumentType.BLOB)
				.build();
		
		courseClass.getClassFileDocuments().add(fileDocument);
		this.courseClassRepository.update(courseClass);
		
		log.info("File " + valueObject.getName() + " saved with id " + fileDocument.getId());
	}
	
	public void saveFileLink(ClassFileDocumentVO valueObject) {
		CourseClass courseClass = this.courseClassRepository.findById(valueObject.getClassId());
		log.info("Saving link file " + valueObject.getName() + " for class with id: " + courseClass.getId());
		ClassFileDocument fileDocument = ClassFileDocument.builder()
				.name(valueObject.getName())
				.content(valueObject.getLink().getBytes())
				.contentType("Link")
				.fileDocumentType(FileDocumentType.LINK)
				.build();
		
		courseClass.getClassFileDocuments().add(fileDocument);
		this.courseClassRepository.update(courseClass);

		log.info("File " + valueObject.getName() + " saved with id " + fileDocument.getId());
	}


}
