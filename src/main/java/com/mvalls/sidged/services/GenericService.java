package com.mvalls.sidged.services;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class GenericService<T, K extends JpaRepository<T, Long>> {

	public T create(T t) {
		return getRepository().save(t);
	}
	
	public T update(T t) {
		return getRepository().save(t);
	}
	
	public void delete(T t) {
		getRepository().delete(t);
	}
	
	public Collection<T> findAll() {
		return getRepository().findAll();
	}
	
	public T findById(Long id) {
		return getRepository().findById(id).orElseThrow(() -> new IllegalArgumentException(id + " doesn't exists!"));
	}
	
	protected abstract K getRepository();
	
}
