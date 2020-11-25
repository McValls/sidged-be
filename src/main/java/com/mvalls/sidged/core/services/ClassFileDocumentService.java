package com.mvalls.sidged.core.services;

import java.util.Collection;

import javax.transaction.Transactional;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.FileDocumentType;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
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
public class ClassFileDocumentService extends GenericService<ClassFileDocument, ClassFileDocumentRepository>{
	
	private final CourseClassRepository courseClassRepository;
	

	public ClassFileDocumentService(ClassFileDocumentRepository repository,
			CourseClassRepository courseClassRepository) {
		super(repository);
		this.courseClassRepository = courseClassRepository;
	}
	
	
	public Collection<ClassFileDocument> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber) {
		return this.repository.findByCourseCodeAndClassNumber(courseCode, classNumber);
	}
	
	public Collection<ClassFileDocument> findByCourseCode(String courseCode) {
		return this.repository.findByCourseCode(courseCode);
	}
	
	@Transactional
	public void saveFileDocument(String courseCode, Integer classNumber, ClassFileDocumentVO valueObject) {
		CourseClass courseClass = this.courseClassRepository.findByCourseCodeAndClassNumber(courseCode, classNumber).orElseThrow();
		ClassFileDocument fileDocument = ClassFileDocument.builder()
				.name(valueObject.getName())
				.content(valueObject.getContent())
				.contentType(valueObject.getContentType())
				.fileDocumentType(FileDocumentType.BLOB)
				.courseClass(courseClass)
				.build();
		
		this.repository.create(fileDocument);
	}
	
	public void saveFileLink(String courseCode, Integer classNumber, String link, String name) {
		CourseClass courseClass = this.courseClassRepository.findByCourseCodeAndClassNumber(courseCode, classNumber).orElseThrow();
		ClassFileDocument fileDocument = ClassFileDocument.builder()
				.name(name)
				.content(link.getBytes())
				.contentType("Link")
				.fileDocumentType(FileDocumentType.LINK)
				.courseClass(courseClass)
				.build();
		
		this.repository.create(fileDocument);
	}


}
