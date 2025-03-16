package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.MemberApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberApplicationRepo extends JpaRepository<MemberApplication, Long>{
	
	public MemberApplication findByEmailIgnoreCase(String email);
	public MemberApplication findByMobile(String mobile);
	
	@Query("select mem from MemberApplication mem order by mem.id desc limit 5")
	public List<MemberApplication> listMemberApplicationRecent();
	
	Page<MemberApplication> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
