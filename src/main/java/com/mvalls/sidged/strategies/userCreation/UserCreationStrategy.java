package com.mvalls.sidged.strategies.userCreation;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.valueObjects.SignUpVO;

public interface UserCreationStrategy {

	void execute(User user, SignUpVO valueObject);
	
}
