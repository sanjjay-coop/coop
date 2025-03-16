package org.pf.coop.portal.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.MemberGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepo extends JpaRepository<Member, Long>{

	public Member findByEmailIgnoreCase(String email);
	public Member findByMemIdIgnoreCase(String memId);
	public Member findByMobile(String mobile);
	
	@Query("select mem from Member mem order by mem.id desc limit 5")
	public List<Member> listMemberRecent();
	
	@Query("select mem from Member mem left join mem.memGroup mg where lower(mem.firstName) like %:str% or "
			+ "lower(mem.middleName) like %:str% or "
			+ "lower(mem.lastName) like %:str% or "
			+ "lower(mem.memId) like %:str% or "
			+ "lower(mem.resAddress) like %:str% or "
			+ "lower(mg.name) like %:str% or "
			+ "lower(mem.resCity) like %:str% order by mem.firstName desc")
	public List<Member> listMemberSearch(String str);
	
	public List<Member> findAllBySubEndDateBetween(Date date1, Date date2);
	
	public Optional<Member> findByMemGroup(MemberGroup memGroup);
	
	Page<Member> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);
	
}
