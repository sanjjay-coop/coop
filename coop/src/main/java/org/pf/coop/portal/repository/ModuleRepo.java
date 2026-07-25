package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepo extends JpaRepository<Module, Long>{

	public Module findByName(String name);
	
}
