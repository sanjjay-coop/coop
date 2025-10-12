package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationLevelRepo extends JpaRepository<EducationLevel, Long>{

	public EducationLevel findByName(String name);
	
	List<EducationLevel> findByRecordAddDateIsNull();
}
