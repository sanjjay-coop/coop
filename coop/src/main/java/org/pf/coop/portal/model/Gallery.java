package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.pf.coop.common.BaseObject;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_gallery")
public class Gallery extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 835390466375540563L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_gallery")
	@SequenceGenerator(name="key_gallery", 
		sequenceName="seq_key_gallery",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=500, nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT", name="f_description", nullable=false)
	private String description;
	
	@Column(name="f_date", nullable=false)
	private Date date;
	
	@Transient 
	private String searchFor;
	
	@OneToMany(mappedBy="gallery", cascade = CascadeType.ALL)
	@OrderBy("recordAddDate ASC")
	private Set<Photo> photos;
	
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_file_data")
	private byte[] fileData;
	
	@Transient
	private MultipartFile file;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Gallery [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (date != null ? "date=" + date + ", " : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (date != null ? date + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (date != null ? date + ", " : ""));
	}
}
