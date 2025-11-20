package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;

import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.ServiceProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Long>{

	Page<ServiceProvider> findByEnabled(Boolean enabled, Pageable pageable);
	
	Page<ServiceProvider> findByOwner(Member member, Pageable pageable);
	
	@Query("select count(*) from ServiceProvider o where o.recordAddDate > ?1")
	long countAddAfterDate(Date cal);
	
	@Query("select o "
			+ "from ServiceProvider o "
			+ "where o.enabled = true "
			+ "order by o.id desc "
			+ "limit 5")
	public List<ServiceProvider> listServiceProviderRecent();
	
	@Query("select o "
			+ "from ServiceProvider o "
			+ "where o.enabled = true "
			+ "and "
			+ "o.recordAddDate >=:date "
			+ "order by o.serviceName asc ")
	public List<ServiceProvider> listServiceProviderForBulletin(Date date);
	
	public ServiceProvider findByIdAndOwner(Long id, Member owner);
	
	Page<ServiceProvider> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
	List<ServiceProvider> findBySearchString(String searchString);
	
	Page<ServiceProvider> findByEnabledAndSearchStringContainingIgnoreCase(Boolean enabled, String searchString, Pageable pageable);
	
	List<ServiceProvider> findByEnabledAndSearchString(Boolean enabled, String searchString);
	
}
