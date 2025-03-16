package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_invitation")
public class Invitation extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 566514617116710474L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_invitation")
	@SequenceGenerator(name="key_invitation", 
		sequenceName="seq_key_invitation",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_email", length=100, nullable=false, unique=true)
	private String email;
	
	@Column(name="f_name", length=100, nullable=false)
	private String name;
	
	@Column(name="f_random", length=20, nullable=false)
	private String random;
	
	@Column(name="f_date", nullable=false)
	private Date date;
	
	@Column(name="f_update_date", nullable=true)
	private Date updateDate;
	
	@Column(name="f_status", nullable=true)
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name="f_member", nullable=false)
	private Member member;
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Invitation [" + (id != null ? "id=" + id + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (random != null ? "random=" + random + ", " : "")
				+ (date != null ? "date=" + date + ", " : "") + (status != null ? "status=" + status + ", " : "")
				+ (member != null ? "member=" + member : "") + "]";
	}
	
	public boolean isResendPossible(){
		
		if (this.updateDate==null) return true;
		else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -15);
			if (this.updateDate.before(cal.getTime())) return true;
			else return false;
		}
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((email != null ? email + ", " : "")
				+ (name != null ? name + ", " : "")
				+ (member != null ? member.getName() : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((email != null ? email + ", " : "")
				+ (name != null ? name + ", " : "")
				+ (member != null ? member.getName() : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
}
