package com.mvalls.sidged.test.strategies;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.strategies.userCreation.StudentCreationStrategy;
import com.mvalls.sidged.strategies.userCreation.TeacherCreationStrategy;
import com.mvalls.sidged.strategies.userCreation.UserCreationStrategy;
import com.mvalls.sidged.strategies.userCreation.UserCreationStrategyService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserCreationStrategyServiceTest  {
	
	@Autowired
	private UserCreationStrategyService userCreationStrategyService;
	
	private UserType userType;
	private UserCreationStrategy strategy;

	@Given("user type is teacher")
	public void user_type_is_teacher() {
	    userType = UserType.TEACHER;
	}

	@When("I ask for the user creation strategy")
	public void i_ask_for_the_user_creation_strategy() {
	    strategy = userCreationStrategyService.getStrategy(userType);
	}

	@Then("I get a TeacherUserCreationStrategy")
	public void i_get_a_TeacherUserCreationStrategy() {
	    assert strategy instanceof TeacherCreationStrategy;
	}

	@Given("user type is student")
	public void user_type_is_student() {
	    userType = UserType.STUDENT;
	}

	@Then("I get a StudentUserCreationStrategy")
	public void i_get_a_StudentUserCreationStrategy() {
		assert strategy instanceof StudentCreationStrategy;
	}
	
}
