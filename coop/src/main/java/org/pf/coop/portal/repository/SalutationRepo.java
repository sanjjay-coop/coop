package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Salutation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalutationRepo extends JpaRepository<Salutation, Long>{

	public Salutation findByName(String name);
}
