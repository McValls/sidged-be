package com.mvalls.sidged.strategies.actions;

import java.util.Collection;

public interface UpdateActionStrategy<T> {

	Collection<T> execute(T t, Collection<T> collection);
	
}
