package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.model.StudentLink;
import com.mvalls.sidged.repositories.StudentLinkRepository;

@Service
public class StudentLinkService extends GenericService<StudentLink, StudentLinkRepository>{
	
	private final StudentLinkRepository studentLinkRepository;
	
	@Autowired
	public StudentLinkService(StudentLinkRepository studentLinkRepository) {
		super();
		this.studentLinkRepository = studentLinkRepository;
	}

	@Override
	protected StudentLinkRepository getRepository() {
		return this.studentLinkRepository;
	}

}
