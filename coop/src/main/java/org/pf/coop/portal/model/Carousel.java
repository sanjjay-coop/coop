package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="tab_carousel")
public class Carousel implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4288853153476971668L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_carousel")
	@SequenceGenerator(name="key_carousel", 
		sequenceName="seq_key_carousel",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=50, nullable=false)
	private String title;
	
	@Column(name="f_description", length=200, nullable=false)
	private String description;
	
	@Column(name="f_pub_end_date", nullable=true)
	private Date pubEndDate;
	
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

	public Date getPubEndDate() {
		return pubEndDate;
	}

	public void setPubEndDate(Date pubEndDate) {
		this.pubEndDate = pubEndDate;
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
		return "Carousel [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (pubEndDate != null ? "pubEndDate=" + pubEndDate + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (fileType != null ? "fileType=" + fileType + ", " : "") + "]";
	}
	
	
}
