package org.pf.coop.portal.security;

import java.util.ArrayList;
import java.util.List;

import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Role;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityEmailOtpUserDetailsService implements UserDetailsService {

	
	@Autowired
	private MemberRepo memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		if (username == null) {
			throw new UsernameNotFoundException("Username was empty");
		}
		
		System.out.println("Searching user by Email.");
		
		Member member = memberRepo.findByEmailIgnoreCase(username);
		
		if (member==null) {
			throw new UsernameNotFoundException("Username was not found");
		}
		
		System.out.println("Member: " + member.getName());
		
		for(Role role : member.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
		}
		
		return new SecurityEmailOtpUserDetails(member, grantedAuthorities);
	}

}