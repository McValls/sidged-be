package com.mvalls.sidged.core.repositories;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

public interface GenericRepository<T, ID> {

	default T create(T obj) {
		throw new NotImplementedException("Must override the create method");
	}
	
	
	default T update(T obj) {
		throw new NotImplementedException("Must override the update method");
	}
	
	
	default void delete(ID obj) {
		throw new NotImplementedException("Must override the delete method");
	}
	
	default T findById(ID id) {
		throw new NotImplementedException("Must override the findById method");
	}
	
	default List<T> findAll() {
		throw new NotImplementedException("Must override the findAll method");
	}
	
	
	
}
