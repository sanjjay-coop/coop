package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long>{

	public Category findByName(String name);
	
	List<Category> findByRecordAddDateIsNull();
}
