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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_news_feed")
public class NewsFeed extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2337946751480088161L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_news_feed")
	@SequenceGenerator(name="key_news_feed", 
		sequenceName="seq_key_news_feed",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=100, nullable=false)
	private String title;
	
	@Column(name="f_description", length=200, nullable=false)
	private String description;
	
	@Column(name="f_url", length=255, nullable=false)
	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "NewsFeed [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "") + (url != null ? "url=" + url : "")
				+ "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? "description =" + description + ", " : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? "description =" + description + ", " : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
}
