package com.mvalls.sidged.services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.ClassFileDocument;
import com.mvalls.sidged.model.CourseClass;
import com.mvalls.sidged.model.FileDocumentType;
import com.mvalls.sidged.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.valueObjects.ClassFileDocumentVO;

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
@Service
public class ClassFileDocumentService extends GenericService<ClassFileDocument, ClassFileDocumentRepository>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassFileDocumentService.class);
	
	@Autowired
	private CourseClassService courseClassService;
	
	@Autowired
	private ClassFileDocumentRepository classFileDocumentRepository;
	
	public Collection<ClassFileDocument> findByCourseClassId(Long classId) {
		return classFileDocumentRepository.findByCourseClassId(classId);
	}

	public Collection<ClassFileDocument> findByCourseId(Long courseId) {
		return classFileDocumentRepository.findByCourseClassCourseId(courseId);
	}
	
	@Transactional
	public void saveFileDocument(Long classId, ClassFileDocumentVO valueObject) {
		CourseClass courseClass = courseClassService.findById(classId);
		LOGGER.info("Saving file " + valueObject.getName() + " for class with id: " + courseClass.getId());
		ClassFileDocument fileDocument = ClassFileDocument.builder()
				.name(valueObject.getName())
				.courseClass(courseClass)
				.content(valueObject.getContent())
				.contentType(valueObject.getContentType())
				.fileDocumentType(FileDocumentType.BLOB)
				.build();
		
		classFileDocumentRepository.save(fileDocument);
		LOGGER.info("File " + valueObject.getName() + " saved with id " + fileDocument.getId());
	}
	
	public void saveFileLink(ClassFileDocumentVO valueObject) {
		CourseClass courseClass = courseClassService.findById(valueObject.getClassId());
		LOGGER.info("Saving link file " + valueObject.getName() + " for class with id: " + courseClass.getId());
		ClassFileDocument fileDocument = ClassFileDocument.builder()
				.name(valueObject.getName())
				.courseClass(courseClass)
				.content(valueObject.getLink().getBytes())
				.contentType("Link")
				.fileDocumentType(FileDocumentType.LINK)
				.build();
		classFileDocumentRepository.save(fileDocument);
		LOGGER.info("File " + valueObject.getName() + " saved with id " + fileDocument.getId());
	}

	@Override
	protected ClassFileDocumentRepository getRepository() {
		return classFileDocumentRepository;
	}


}
