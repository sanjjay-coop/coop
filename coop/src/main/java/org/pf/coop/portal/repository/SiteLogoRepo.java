package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.SiteLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SiteLogoRepo extends JpaRepository<SiteLogo, Long>{
	
	@Query("select sl from SiteLogo sl where sl.id > 0 order by sl.id desc limit 1")
	public SiteLogo getSiteLogo();
}
