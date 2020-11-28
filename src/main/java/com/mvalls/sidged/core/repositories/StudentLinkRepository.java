package com.mvalls.sidged.core.repositories;

import java.util.List;

import com.mvalls.sidged.core.model.StudentLink;

public interface StudentLinkRepository {

	List<StudentLink> findAll();
	
}
