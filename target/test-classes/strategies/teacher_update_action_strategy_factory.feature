Feature: Get UpdateActionStrategy by factory
	
	Scenario: The UpdateAction is ADD
		Given UpdateAction is ADD
		When I ask for the UpdateActionStrategy
		Then I get an AddTeacherUpdateActionStrategy
		
	Scenario: The UpdateAction is REMOVE
		Given UpdateAction is REMOVE
		When I ask for the UpdateActionStrategy
		Then I get an RemoveTeacherUpdateActionStrategy	