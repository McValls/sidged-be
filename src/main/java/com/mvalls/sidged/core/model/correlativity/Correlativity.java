package com.mvalls.sidged.core.model.correlativity;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Subject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = {"subject", "dependencies"})
@ToString(of = {"subject", "level", "dependencies"})
public final class Correlativity implements Comparable<Correlativity> {

	private final Subject subject;
	private final List<Correlativity> correlativities;
	private final List<Subject> dependencies;
	private final List<Subject> dependenciesTree;
	private final int level;

	public Correlativity(Subject subject, List<Correlativity> correlativities) {
		validateArgumentsOrThrow(subject, correlativities);
		
		this.subject = subject;
		this.correlativities = this.getSortedCorrelativities(correlativities);
		
		this.level = this.calculateLevelRecursively();
		this.dependencies = this.mapCorrelativitiesToDependenciesFiltered();
		this.dependenciesTree = this.mapCorrelativitiesToDependencies();
	}
	
	/**
	 * 
	 * @param subject
	 * @return true if the subject is part of the dependencies tree
	 */
	public boolean dependsOn(Subject subject) {
		return this.dependsOn(subject, this.correlativities);
	}

	@Override
	public int compareTo(Correlativity other) {
		int result = other.level - this.level;
		if (result == 0) {
			result = this.subject.getName().compareTo(other.subject.getName());
		}
		return result;
	}
	
	/**
	 * 
	 * @param subject
	 * @param correlativities
	 * @return true if any correlativity has the subject as a dependency
	 */
	private boolean dependsOn(Subject subject, List<Correlativity> correlativities) {
		if (correlativities.isEmpty()) {
			return false;
		} else {
			return correlativities.stream()
					.anyMatch(dep -> dep.subject.equals(subject) || dep.dependsOn(subject));
		}
	}

	/**
	 * 
	 * @return 0 if it has no dependencies, or 
	 * max value between all of its correlativities plus 1
	 */
	private int calculateLevelRecursively() {
		if (correlativities.isEmpty()) {
			return 0;
		} else {
			return correlativities.stream()
					.mapToInt(dep -> dep.getLevel())
					.max()
					.getAsInt() + 1;
		}
	}
	
	/**
	 * 
	 * @param subject
	 * @param correlativities
	 * @throws IllegalArgumentException if a circular dependency is found
	 * or a dependency doesn't belong to the same career that the subject.
	 */
	private void validateArgumentsOrThrow(Subject subject, List<Correlativity> correlativities) {
		correlativities.stream().filter(c -> c.dependsOn(subject, correlativities))
			.findAny()
			.ifPresent(c -> {
				throw new IllegalStateException("Circular dependency between " + subject + " and " + c.subject);
			});

		if (hasAnotherCareerInCorrelativities(subject.getCareer(), correlativities)) {
			throw new IllegalStateException("All dependencies must be of the same career");
		}
	}
	
	/**
	 * 
	 * @param correlativities
	 * @return correlativities sorted by the compareTo criteria
	 */
	private List<Correlativity> getSortedCorrelativities(List<Correlativity> correlativities) {
		return correlativities.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return subjects of correlativities with no duplicated dependencies
	 */
	private List<Subject> mapCorrelativitiesToDependenciesFiltered() {
		return this.correlativities.stream()
				.filter(candidate -> correlativities.stream().noneMatch(dep -> dep.dependsOn(candidate.subject)))
				.map(correlativity -> correlativity.subject)
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return subjects of all the correlativities
	 */
	private List<Subject> mapCorrelativitiesToDependencies() {
		return this.correlativities.stream()
				.map(correlativity -> correlativity.subject)
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param career
	 * @param correlativities
	 * @return true if correlativities has any subject with a different career
	 */
	private boolean hasAnotherCareerInCorrelativities(Career career, List<Correlativity> correlativities) {
		if (correlativities.isEmpty()) {
			return false;
		} else {
			return correlativities.stream()
					.anyMatch(c -> !c.subject.getCareer().equals(career) || c.hasAnotherCareerInCorrelativities(career, c.correlativities));
		}
	}

}
