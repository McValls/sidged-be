package com.mvalls.sidged.rest.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mvalls.sidged.mappers.*;
import com.mvalls.sidged.model.ClassFileDocument;
import com.mvalls.sidged.model.FileDocumentType;
import com.mvalls.sidged.rest.dtos.ClassFileDocumentDTO;
import com.mvalls.sidged.rest.dtos.ClassFileDocumentLinkDTO;
import com.mvalls.sidged.services.ClassFileDocumentService;
import com.mvalls.sidged.valueObjects.ClassFileDocumentVO;

@RestController
@RequestMapping("/file-documents")
public class ClassFileDocumentRestController {

	@Autowired private MultipartFileToClassFileDocumentVOMapper fileToFileDocumentMapper;
	@Autowired private ClassFileDocumentDTOToVOMapper classFileDocumentDTOToVOMapper;
	@Autowired private ClassFileDocumentService classFileDocumentService;
	@Autowired private ClassFileDocumentModelToDTOMapper classFileDocumentModelToDTOMapper;
	
	@GetMapping("/class/{classId}")
	public Collection<ClassFileDocumentDTO> getFileDocuments(@PathVariable("classId") Long classId){
		Collection<ClassFileDocument> fileDocuments = classFileDocumentService.findByCourseClassId(classId);
		Collection<ClassFileDocumentDTO> dtos = fileDocuments.stream()
				.map(fd -> classFileDocumentModelToDTOMapper.map(fd))
				.collect(Collectors.toList());
		return dtos;
	}
	
	@GetMapping("/course/{courseId}")
	public Collection<ClassFileDocumentDTO> getFileDocumentsByCourse(@PathVariable("courseId") Long courseId){
		Collection<ClassFileDocument> fileDocuments = classFileDocumentService.findByCourseId(courseId);
		Collection<ClassFileDocumentDTO> dtos = fileDocuments.stream()
				.map(fd -> classFileDocumentModelToDTOMapper.map(fd))
				.collect(Collectors.toList());
		return dtos;
	}
	
	@PostMapping(value = {"/class/{classId}"}, consumes = {"multipart/form-data"})
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
		if(fileDocument.getFileDocumentType() == FileDocumentType.BLOB) {
			ByteArrayResource resource = new ByteArrayResource(fileDocument.getContent());
			return ResponseEntity.ok()
					.contentLength(fileDocument.getContent().length)
					.contentType(getMediaType(fileDocument.getContentType()))
					.body(resource);
		} else {
			throw new IllegalStateException("Cannot download a non-blob file");
		}
		
	}
	
	private MediaType getMediaType(String contentType) {
		try{
			return MediaType.valueOf(contentType);
		}catch(InvalidMediaTypeException e) {
			return MediaType.ALL;
		}
	}

}
