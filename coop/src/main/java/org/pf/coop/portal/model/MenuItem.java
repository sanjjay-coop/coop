package org.pf.coop.portal.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_menu_item")
public class MenuItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8472224140909669929L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_menu_item")
	@SequenceGenerator(name="key_menu_item", 
		sequenceName="seq_key_menu_item",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=50, nullable=false)
	private String title;
	
	@Column(name="f_location", length=20, nullable=false)
	private String location;
	
	@Column(name="f_item_type", length=50, nullable=false)
	private String itemType;
	
	@Column(name="f_url", length=255, nullable=true)
	private String url;
	
	@ManyToOne
	@JoinColumn(name="f_category", nullable=true)
	private Category category;
	
	@Column(name="f_new_page", nullable=false)
	private Boolean newPage;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Boolean getNewPage() {
		return newPage;
	}

	public void setNewPage(Boolean newPage) {
		this.newPage = newPage;
	}

	@Override
	public String toString() {
		return "MenuItem [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "") + (url != null ? "url=" + url + ", " : "")
				+ (category != null ? "category=" + category + ", " : "")
				+ (newPage != null ? "newPage=" + newPage : "") + "]";
	}
	
}
