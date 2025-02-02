package org.pf.coop.portal.repository.library;

import org.pf.coop.portal.model.library.TitleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleTypeRepo extends JpaRepository<TitleType, Long>{

	public TitleType findByName(String name);
}
