package org.pf.coop.portal.repository.library;

import org.pf.coop.portal.model.library.CheckOut;
import org.pf.coop.portal.model.library.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepo extends JpaRepository<CheckOut, Long>{

	public CheckOut findByTitle(Title title);
}

