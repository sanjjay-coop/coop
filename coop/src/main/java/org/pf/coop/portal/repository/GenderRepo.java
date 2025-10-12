package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepo extends JpaRepository<Gender, Long>{

	public Gender findByName(String name);
	
	List<Gender> findByRecordAddDateIsNull();
}
