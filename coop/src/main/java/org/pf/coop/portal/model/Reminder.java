package org.pf.coop.portal.model;

import java.io.Serializable;
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
@Table(name="tab_reminder")
public class Reminder extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2299271002012921788L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_reminder")
	@SequenceGenerator(name="key_reminder", 
		sequenceName="seq_key_reminder",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=255, nullable=false)
	private String title;
	
	@Column(name="f_description", length=2000, nullable=false)
	private String description;
	
	@Column(name="f_rem_date", nullable=false)
	private Date remDate;
	
	@Column(name="f_rem_days", nullable=false)
	private Long remDays;
	
	@Column(name="f_rem_start_date", nullable=true)
	private Date remStartDate;
	
	@Column(name="f_rem_by_email", nullable=false)
	private Boolean remByEmail;
	
	@Column(name="f_rem_by_self", nullable=false)
	private Boolean remBySelf;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRemDate() {
		return remDate;
	}

	public void setRemDate(Date remDate) {
		this.remDate = remDate;
	}

	public Long getRemDays() {
		return remDays;
	}

	public void setRemDays(Long remDays) {
		this.remDays = remDays;
	}

	public Date getRemStartDate() {
		return remStartDate;
	}

	public void setRemStartDate(Date remStartDate) {
		this.remStartDate = remStartDate;
	}

	public Boolean getRemByEmail() {
		return remByEmail;
	}

	public void setRemByEmail(Boolean remByEmail) {
		this.remByEmail = remByEmail;
	}

	public Boolean getRemBySelf() {
		return remBySelf;
	}

	public void setRemBySelf(Boolean remBySelf) {
		this.remBySelf = remBySelf;
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
		return "Reminder [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (remDate != null ? "remDate=" + remDate + ", " : "")
				+ (remDays != null ? "remDays=" + remDays + ", " : "")
				+ (remByEmail != null ? "remByEmail=" + remByEmail + ", " : "")
				+ (remBySelf != null ? "remBySelf=" + remBySelf + ", " : "")
				+ (member != null ? "member=" + member : "") + "]";
	}

	public String getReminderMessage() {
		return "Reminder Message --> [" + title
				+ " [" + description + "] "
				+ " - " + remDate + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (member != null ? member.getMemId() : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (member != null ? member.getMemId() : ""));
	}
	
}
