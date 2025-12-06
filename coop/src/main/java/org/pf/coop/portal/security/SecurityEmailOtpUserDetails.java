package org.pf.coop.portal.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pf.coop.portal.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityEmailOtpUserDetails  implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1250040463061257870L;
	
	private Member member;
	
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public SecurityEmailOtpUserDetails(Member member, List<GrantedAuthority> authorities) {
		this.member = member;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		//return member.getPassword();
		return member.getOtp();
	}

	@Override
	public String getUsername() {
		return member.getMemId();		
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}