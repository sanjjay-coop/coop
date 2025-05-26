package org.pf.coop.portal.model.library;

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
@Table(name="tab_library_title")
public class Title extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6183286722384805101L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_library_title")
	@SequenceGenerator(name="key_library_title", 
		sequenceName="seq_key_library_title",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_accession_number", length=10, nullable=false, unique=true)
	private String accessionNumber;
	
	@Column(name="f_uniform_title", length=1000, nullable=false, unique=false)
	private String uniformTitle;
	
	@Column(name="f_authors", length=1000, nullable=false)
	private String authors;
	
	@Column(name="f_publisher", length=1000, nullable=true)
	private String publisher;
	
	@ManyToOne
	@JoinColumn(name="f_library", nullable=false)
	private Library library;
	
	@ManyToOne
	@JoinColumn(name="f_title_type", nullable=false)
	private TitleType titleType;
	
	@Column(name="f_summary", length=2000, nullable=true)
	private String summary;
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getUniformTitle() {
		return uniformTitle;
	}

	public void setUniformTitle(String uniformTitle) {
		this.uniformTitle = uniformTitle;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public TitleType getTitleType() {
		return titleType;
	}

	public void setTitleType(TitleType titleType) {
		this.titleType = titleType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Title [" + (id != null ? "id=" + id + ", " : "")
				+ (accessionNumber != null ? "accessionNumber=" + accessionNumber + ", " : "")
				+ (uniformTitle != null ? "uniformTitle=" + uniformTitle + ", " : "")
				+ (publisher != null ? "publisher=" + publisher + ", " : "")
				+ (authors != null ? "authors=" + authors + ", " : "")
				+ (library != null ? "library=" + library.getName() + ", " : "")
				+ (titleType != null ? "titleType=" + titleType.getName() + ", " : "")
				+ (summary != null ? "summary=" + summary : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		this.setSearchString((accessionNumber != null ? accessionNumber + ", " : "")
				+ (uniformTitle != null ? uniformTitle + ", " : "")
				+ (publisher != null ? publisher + ", " : "")
				+ (authors != null ? authors + ", " : "")
				+ (library != null ? library.getName() + ", " : "")
				+ (titleType != null ? titleType.getName() + ", " : "")
				+ (summary != null ? summary : ""));
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		this.setSearchString((accessionNumber != null ? accessionNumber + ", " : "")
				+ (uniformTitle != null ? uniformTitle + ", " : "")
				+ (publisher != null ? publisher + ", " : "")
				+ (authors != null ? authors + ", " : "")
				+ (library != null ? library.getName() + ", " : "")
				+ (titleType != null ? titleType.getName() + ", " : "")
				+ (summary != null ? summary : ""));
		super.setUpdateDefaults(modifiedBy);
	}
	
}
