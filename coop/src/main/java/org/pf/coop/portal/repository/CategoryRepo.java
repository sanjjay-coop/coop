package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long>{

	public Category findByName(String name);
}
