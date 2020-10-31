package com.mvalls.sidged.core.repositories;

import java.util.List;

public interface GenericRepository<T, ID> {

	T create(T obj);
	T update(T obj);
	void delete(ID obj);
	T findById(ID id);
	List<T> findAll();
	
	
	
}
