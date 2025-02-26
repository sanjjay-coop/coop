package org.pf.coop.portal.model.library;

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
@Table(name="tab_library_title")
public class Title implements Serializable {

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
	
	@ManyToOne
	@JoinColumn(name="f_library", nullable=false)
	private Library library;
	
	@ManyToOne
	@JoinColumn(name="f_title_type", nullable=false)
	private TitleType titleType;
	
	@Column(name="f_summary", length=2000, nullable=true)
	private String summary;

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

	@Override
	public String toString() {
		return "Title [" + (id != null ? "id=" + id + ", " : "")
				+ (accessionNumber != null ? "accessionNumber=" + accessionNumber + ", " : "")
				+ (uniformTitle != null ? "uniformTitle=" + uniformTitle + ", " : "")
				+ (authors != null ? "authors=" + authors + ", " : "")
				+ (library != null ? "library=" + library + ", " : "")
				+ (titleType != null ? "titleType=" + titleType + ", " : "")
				+ (summary != null ? "summary=" + summary : "") + "]";
	}
	
}
