package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_sponsorship")
public class Sponsorship implements Serializable {

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
	
	@Column(name="f_title", length=500, nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT", name="f_description", nullable=false)
	private String description;
	
	@Column(name="f_pub_date", nullable=false)
	private Date pubDate;
	
	@Column(name="f_exp_date", nullable=false)
	private Date expDate;
	
	@Column(name="f_last_date", nullable=false)
	private Date lastDate;

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

	@Override
	public String toString() {
		return "Sponsorship [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (pubDate != null ? "pubDate=" + pubDate + ", " : "")
				+ (expDate != null ? "expDate=" + expDate + ", " : "")
				+ (lastDate != null ? "lastDate=" + lastDate + ", " : "") + "]";
	}
}
