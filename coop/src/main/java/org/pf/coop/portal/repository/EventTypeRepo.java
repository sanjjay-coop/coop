package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepo extends JpaRepository<EventType, Long>{

	public EventType findByName(String name);
}