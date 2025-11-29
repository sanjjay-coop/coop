package org.pf.coop.portal.model;

import java.io.Serializable;

import org.pf.coop.common.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_volunteer")
public class Volunteer extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666875453254000661L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_volunteer")
	@SequenceGenerator(name="key_volunteer", 
		sequenceName="seq_key_volunteer",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_member", nullable=false)
	private Member member;
	
	@Column(columnDefinition = "TEXT", name="f_introduction", nullable=true)
	private String introduction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Override
	public String toString() {
		return "Volunteer [" + (id != null ? "id=" + id + ", " : "") + (member != null ? "member=" + member.getMemId() + ", " : "")
				+ (introduction != null ? "introduction=" + introduction : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((member != null ? member.getSearchString() + ", " : "")
				+ (introduction != null ? introduction : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((member != null ? member.getSearchString() + ", " : "")
				+ (introduction != null ? introduction : ""));
	}
	
	
	

}
