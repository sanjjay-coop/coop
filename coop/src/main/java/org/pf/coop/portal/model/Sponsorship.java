package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.pf.coop.common.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Indexed
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_sponsorship")
public class Sponsorship extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2429580392681677518L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_sponsorship")
	@SequenceGenerator(name="key_sponsorship", 
		sequenceName="seq_key_sponsorship",
		allocationSize=1)
	private Long id;
	
	@FullTextField
	@Column(name="f_title", length=500, nullable=false)
	private String title;
	
	@FullTextField
	@Column(columnDefinition = "TEXT", name="f_description", nullable=false)
	private String description;
	
	@GenericField
	@Column(name="f_pub_date", nullable=false)
	private Date pubDate;
	
	@GenericField
	@Column(name="f_exp_date", nullable=false)
	private Date expDate;
	
	@GenericField
	@Column(name="f_last_date", nullable=false)
	private Date lastDate;

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

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Sponsorship [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (pubDate != null ? "pubDate=" + pubDate + ", " : "")
				+ (expDate != null ? "expDate=" + expDate + ", " : "")
				+ (lastDate != null ? "lastDate=" + lastDate + ", " : "") + "]";
	}
	
	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? "description=" + description + ", " : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? "description=" + description + ", " : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : ""));
	}
}
