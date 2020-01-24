package com.mvalls.sidged.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvalls.sidged.model.Time;

public interface TimeRepository extends JpaRepository<Time, Long>{

}
