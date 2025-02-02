package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepo extends JpaRepository<Occupation, Long> {

	public Occupation findByName(String name);
}