package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.users.UserTeacher;

public interface UserTeacherRepository {

	UserTeacher findByTeacherId(Long id);
	UserTeacher findByUserUsername(String username);
	UserTeacher create(UserTeacher userTeacher);
	UserTeacher update(UserTeacher userTeacher);

}
