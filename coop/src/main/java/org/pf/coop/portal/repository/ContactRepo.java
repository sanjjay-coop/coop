package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepo extends JpaRepository<Contact, Long> {

	public Contact findByName(String name);
}
