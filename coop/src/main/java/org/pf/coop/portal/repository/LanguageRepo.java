package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepo extends JpaRepository<Language, Long> {

	public Language findByName(String name);
	
	List<Language> findByRecordAddDateIsNull();
	
}
