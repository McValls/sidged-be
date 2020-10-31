package com.mvalls.sidged.core.services;

import java.util.Collection;

import com.mvalls.sidged.core.repositories.GenericRepository;

/**
 * 
 * @author Marcelo Valls
 * 
 *         This file is part of SIDGED-Backend.
 * 
 *         SIDGED-Backend is free software: you can redistribute it and/or
 *         modify it under the terms of the GNU General Public License as
 *         published by the Free Software Foundation, either version 3 of the
 *         License, or (at your option) any later version.
 * 
 *         SIDGED-Backend is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with SIDGED-Backend. If not, see
 *         <https://www.gnu.org/licenses/>.
 *
 */
public abstract class GenericService<T, R extends GenericRepository<T, Long>> {

	protected final R repository;

	public GenericService(R repository) {
		super();
		this.repository = repository;
	}

	public T create(T t) {
		return repository.create(t);
	}

	public T update(T t) {
		return repository.update(t);
	}

	public void delete(Long id) {
		repository.delete(id);
	}

	public Collection<T> findAll() {
		return repository.findAll();
	}

	public T findById(Long id) {
		return repository.findById(id);
	}

}
