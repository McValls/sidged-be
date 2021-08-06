package com.mvalls.sidged.core.repositories;

import com.mvalls.sidged.core.model.Desertor;

public interface DesertorRepository {

    Desertor findByLanguage(String language);
    
}
