package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.users.UserTeacher;

public interface UserTeacherRepository extends GenericRepository<UserTeacher, Long> {

	UserTeacher findByTeacherId(Long id);

	UserTeacher findByUserUsername(String username);

}
