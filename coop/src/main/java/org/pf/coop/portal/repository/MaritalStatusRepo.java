package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaritalStatusRepo extends JpaRepository<MaritalStatus, Long> {

	public MaritalStatus findByStatus(String status);
}
