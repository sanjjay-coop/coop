package org.pf.coop.portal.repository;

import java.util.List;

import org.pf.coop.portal.model.MemberGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberGroupRepo extends JpaRepository<MemberGroup, Long>{

	public MemberGroup findByName(String name);
	
	@Modifying
	@Transactional
	@Query("delete from MemberGroup o where o.id = ?1")
	public void deleteMemberGroup(Long id);
	
	List<MemberGroup> findByRecordAddDateIsNull();
	
	Page<MemberGroup> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
}
