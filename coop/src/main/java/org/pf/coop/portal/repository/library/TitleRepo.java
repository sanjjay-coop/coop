package org.pf.coop.portal.repository.library;

import java.util.List;

import org.pf.coop.portal.model.library.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TitleRepo extends JpaRepository<Title, Long>{

	public Title findByAccessionNumber(String accessionNumber);

	@Query("select t from Title t "
			+ "where lower(t.accessionNumber) like %:str% or "
			+ "lower(t.uniformTitle) like %:str% or "
			+ "lower(t.authors) like %:str% order by t.uniformTitle asc")
	public List<Title> listTitle(String str);
}
