package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepo extends JpaRepository<Profession, Long> {

	public Profession findByName(String name);
	
	List<Profession> findByRecordAddDateIsNull();
	
}
