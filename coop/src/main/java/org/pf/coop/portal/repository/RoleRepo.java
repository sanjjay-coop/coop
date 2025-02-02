package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long>{

	public Role findByCode(String code);
	public Role findByDescription(String description);
}
