package com.mvalls.sidged.strategies.actions.teachers;

import com.mvalls.sidged.enums.UpdateAction;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

public class TeacherUpdateActionStrategyFactory {

	private TeacherUpdateActionStrategyFactory() {}
	
	public static UpdateActionStrategy<Teacher> getInstance(UpdateAction updateAction) throws IllegalArgumentException {
		switch(updateAction) {
		case ADD:
			return new AddTeacherUpdateActionStrategy();
		case REMOVE:
			return new RemoveTeacherUpdateActionStrategy();
		default:
			throw new IllegalArgumentException("No strategy for teacher fouded for action: " + updateAction);
		}
	}
	
}
