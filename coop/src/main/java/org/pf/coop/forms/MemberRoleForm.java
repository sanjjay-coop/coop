package org.pf.coop.forms;

import java.io.Serializable;

import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.model.Role;

public class MemberRoleForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9174620882671159166L;
	
	private Member member;
	private Role role;
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
