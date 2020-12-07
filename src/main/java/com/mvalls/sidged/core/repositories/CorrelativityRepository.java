package com.mvalls.sidged.core.repositories;

import java.util.List;
import java.util.Optional;

import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.model.correlativity.Correlativity;

public interface CorrelativityRepository {

	List<Correlativity> findAllByCareerCode(String string);

	Optional<Correlativity> findBySubjectCode(String string);

	Correlativity addCorrelativity(Subject subject, Subject newCorrelativeSubject);

}
