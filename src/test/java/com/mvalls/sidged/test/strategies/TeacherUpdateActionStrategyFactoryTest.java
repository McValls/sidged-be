package com.mvalls.sidged.test.strategies;

import org.springframework.test.annotation.DirtiesContext;

import com.mvalls.sidged.enums.UpdateAction;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;
import com.mvalls.sidged.strategies.actions.teachers.AddTeacherUpdateActionStrategy;
import com.mvalls.sidged.strategies.actions.teachers.RemoveTeacherUpdateActionStrategy;
import com.mvalls.sidged.strategies.actions.teachers.TeacherUpdateActionStrategyFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@DirtiesContext
public class TeacherUpdateActionStrategyFactoryTest {
	
	private UpdateAction updateAction;
	private UpdateActionStrategy<Teacher> updateActionStrategy;
	
	@Given("UpdateAction is ADD")
	public void updateaction_is_ADD() {
	    this.updateAction = UpdateAction.ADD;
	}

	@When("I ask for the UpdateActionStrategy")
	public void i_ask_for_the_UpdateActionStrategy() {
		this.updateActionStrategy = TeacherUpdateActionStrategyFactory.getInstance(this.updateAction);
	}

	@Then("I get an AddTeacherUpdateActionStrategy")
	public void i_get_an_AddTeacherUpdateActionStrategy() {
	    assert this.updateActionStrategy instanceof AddTeacherUpdateActionStrategy;
	}

	@Given("UpdateAction is REMOVE")
	public void updateaction_is_REMOVE() {
	    this.updateAction = UpdateAction.REMOVE;
	}

	@Then("I get an RemoveTeacherUpdateActionStrategy")
	public void i_get_an_RemoveTeacherUpdateActionStrategy() {
		assert this.updateActionStrategy instanceof RemoveTeacherUpdateActionStrategy;
	}

}
