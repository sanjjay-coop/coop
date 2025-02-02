package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTypeRepo extends JpaRepository<MemberType, Long>{

	public MemberType findByName(String name);
}
