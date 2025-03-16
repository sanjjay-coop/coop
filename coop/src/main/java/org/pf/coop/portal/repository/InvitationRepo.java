package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.Invitation;
import org.pf.coop.portal.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepo extends JpaRepository<Invitation, Long>{
	
	Invitation findByEmail(String email);
	
	Page<Invitation> findByMember(Member member, Pageable pageable);
	
	Invitation findByIdAndMember(Long id, Member member);
	
	Page<Invitation> findBySearchStringContainingIgnoreCase(String searchString, Pageable pageable);

}
