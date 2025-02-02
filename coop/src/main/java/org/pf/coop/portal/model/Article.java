package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_article")
public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778267752796560862L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_article")
	@SequenceGenerator(name="key_article", 
		sequenceName="seq_key_article",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=500, nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT", name="f_content", nullable=false)
	private String content;
	
	@Column(name="f_pub_date", nullable=false)
	private Date pubDate;
	
	@Column(name="f_exp_date", nullable=false)
	private Date expDate;
	
	@Column(name="f_publish", nullable=false)
	private Boolean publish;

	@Column(name="f_author", length=100, nullable=true)
	private String author;
	
	@Column(name="f_last_update_by", length=100, nullable=true)
	private String lastUpdateBy;
	
	@Column(name="f_update_date", nullable=false)
	private Date updateDate;
	
	@ManyToMany
	@JoinTable(
			name = "tab_article_category",
			joinColumns = @JoinColumn(name = "article_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<Category>();

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Boolean getPublish() {
		return publish;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Article [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (pubDate != null ? "pubDate=" + pubDate + ", " : "")
				+ (expDate != null ? "expDate=" + expDate + ", " : "")
				+ (publish != null ? "publish=" + publish + ", " : "")
				+ (author != null ? "author=" + author + ", " : "")
				+ (lastUpdateBy != null ? "lastUpdateBy=" + lastUpdateBy + ", " : "")
				+ (updateDate != null ? "updateDate=" + updateDate + ", " : "")
				+ (categories != null ? "categories=" + categories : "") + "]";
	}
}

