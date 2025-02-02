package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.EventUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventUpdateRepo extends JpaRepository<EventUpdate, Long>{

	@Modifying
	@Query("delete from EventUpdate c where c.id = :id")
	void deleteById(Long id);
}
