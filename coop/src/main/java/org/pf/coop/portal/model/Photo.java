package org.pf.coop.portal.model;

import java.io.Serializable;

import org.pf.coop.common.BaseObject;
import org.springframework.web.multipart.MultipartFile;

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
@Table(name="tab_photo")
public class Photo extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6378752056692814633L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_photo")
	@SequenceGenerator(name="key_photo", 
		sequenceName="seq_key_photo",
		allocationSize=1)
	private Long id;
	
	@ManyToOne()
    @JoinColumn(name = "f_gallery", nullable=false)
	private Gallery gallery;
	
	@Column(name="f_file_name", length=255, nullable=false)
	private String fileName;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private MultipartFile[] files;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
}
