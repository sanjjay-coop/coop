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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_advert")
public class Advert extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8620929148218559424L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_advert")
	@SequenceGenerator(name="key_advert", 
		sequenceName="seq_key_advert",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_name", length=50, nullable=false, unique=true)
	private String name;
	
	@Column(name="f_content", length=1000, nullable=false)
	private String content;
	
	@Column(name="f_location", length=20, nullable=false)
	private String location;
	
	@Column(name="f_pub_date", nullable=false)
	private Date pubDate;
	
	@Column(name="f_exp_date", nullable=false)
	private Date expDate;
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Advert [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ (pubDate != null ? "pubDate=" + pubDate + ", " : "") + (expDate != null ? "expDate=" + expDate : "")
				+ "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((name != null ? name + ", " : "")
				+ (location != null ? location + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((name != null ? name + ", " : "")
				+ (location != null ? location + ", " : ""));
	}
}
