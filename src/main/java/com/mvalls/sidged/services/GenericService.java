package com.mvalls.sidged.services;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
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
