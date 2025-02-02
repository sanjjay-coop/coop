package org.pf.coop.portal.repository.library;

import org.pf.coop.portal.model.library.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepo extends JpaRepository<Library, Long>{

	public Library findByShortName(String shortName);
	public Library findByName(String name);
}

