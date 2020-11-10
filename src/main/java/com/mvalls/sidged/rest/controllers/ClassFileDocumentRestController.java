package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mvalls.sidged.core.model.ClassFileDocument;
import com.mvalls.sidged.core.model.FileDocumentType;
import com.mvalls.sidged.core.services.ClassFileDocumentService;
import com.mvalls.sidged.mappers.ClassFileDocumentDTOToVOMapper;
import com.mvalls.sidged.mappers.ClassFileDocumentModelToDTOMapper;
import com.mvalls.sidged.mappers.MultipartFileToClassFileDocumentVOMapper;
import com.mvalls.sidged.rest.dtos.ClassFileDocumentDTO;
import com.mvalls.sidged.rest.dtos.ClassFileDocumentLinkDTO;
import com.mvalls.sidged.valueObjects.ClassFileDocumentVO;

/**
 * 
 * @author Marcelo Valls
 * 
 *         This file is part of SIDGED-Backend.
 * 
 *         SIDGED-Backend is free software: you can redistribute it and/or
 *         modify it under the terms of the GNU General Public License as
 *         published by the Free Software Foundation, either version 3 of the
 *         License, or (at your option) any later version.
 * 
 *         SIDGED-Backend is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with SIDGED-Backend. If not, see
 *         <https://www.gnu.org/licenses/>.
 *
 */
@RestController
@RequestMapping("/file-documents")
public class ClassFileDocumentRestController {

	private final MultipartFileToClassFileDocumentVOMapper fileToFileDocumentMapper;
	private final ClassFileDocumentDTOToVOMapper classFileDocumentDTOToVOMapper;
	private final ClassFileDocumentService classFileDocumentService;
	private final ClassFileDocumentModelToDTOMapper classFileDocumentModelToDTOMapper;

	@Autowired
	public ClassFileDocumentRestController(ClassFileDocumentService classFileDocumentService,
			@Value("${server.host}") String serverHost) {
		super();
		this.classFileDocumentService = classFileDocumentService;
		this.fileToFileDocumentMapper = new MultipartFileToClassFileDocumentVOMapper();
		this.classFileDocumentDTOToVOMapper = new ClassFileDocumentDTOToVOMapper();
		this.classFileDocumentModelToDTOMapper = new ClassFileDocumentModelToDTOMapper(serverHost);
	}

	@GetMapping("/class/{classId}")
	public Collection<ClassFileDocumentDTO> getFileDocuments(@PathVariable("classId") Long classId) {
		Collection<ClassFileDocument> fileDocuments = classFileDocumentService.findByCourseClassId(classId);
		Collection<ClassFileDocumentDTO> dtos = fileDocuments.stream()
				.map(fd -> classFileDocumentModelToDTOMapper.map(fd)).collect(Collectors.toList());
		return dtos;
	}

	@GetMapping("/course/{courseId}")
	public Collection<ClassFileDocumentDTO> getFileDocumentsByCourse(@PathVariable("courseId") Long courseId) {
		Collection<ClassFileDocument> fileDocuments = classFileDocumentService.findByCourseId(courseId);
		Collection<ClassFileDocumentDTO> dtos = fileDocuments.stream()
				.map(fd -> classFileDocumentModelToDTOMapper.map(fd)).collect(Collectors.toList());
		return dtos;
	}

	@PostMapping(value = { "/class/{classId}" }, consumes = { "multipart/form-data" })
	public void uploadFile(@PathVariable("classId") Long classId, @RequestParam("file0") MultipartFile file) {
		ClassFileDocumentVO vo = fileToFileDocumentMapper.map(file);
		classFileDocumentService.saveFileDocument(classId, vo);
	}

	@PostMapping("/link")
	public void uploadLink(@RequestBody ClassFileDocumentLinkDTO fileDTO) {
		ClassFileDocumentVO vo = classFileDocumentDTOToVOMapper.map(fileDTO);
		classFileDocumentService.saveFileLink(vo);
	}

	@GetMapping("/file/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) {
		ClassFileDocument fileDocument = classFileDocumentService.findById(fileId);
		if (fileDocument.getFileDocumentType() == FileDocumentType.BLOB) {
			ByteArrayResource resource = new ByteArrayResource(fileDocument.getContent());
			return ResponseEntity.ok().contentLength(fileDocument.getContent().length)
					.contentType(getMediaType(fileDocument.getContentType())).body(resource);
		} else {
			throw new IllegalStateException("Cannot download a non-blob file");
		}

	}

	private MediaType getMediaType(String contentType) {
		try {
			return MediaType.valueOf(contentType);
		} catch (InvalidMediaTypeException e) {
			return MediaType.ALL;
		}
	}

}
