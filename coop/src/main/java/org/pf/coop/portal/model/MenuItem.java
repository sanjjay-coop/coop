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
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_menu_item")
public class MenuItem extends BaseObject implements Serializable{

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
	
	@Column(name="f_icon", length=100, nullable=true)
	private String icon;
	
	@ManyToOne
	@JoinColumn(name="f_category", nullable=true)
	private Category category;
	
	@Column(name="f_new_page", nullable=false)
	private Boolean newPage;
	
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "MenuItem [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "") + (url != null ? "url=" + url + ", " : "")
				+ (category != null ? "category=" + category + ", " : "")
				+ (icon != null ? "icon=" + icon + ", " : "")
				+ (newPage != null ? "newPage=" + newPage : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : ""));
	}
	
}
