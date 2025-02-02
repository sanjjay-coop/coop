package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long>{

	@Query("select art from MenuItem art where art.location =:location order by art.title asc")
	public List<MenuItem> listMenuItemLocation(String location);
}