package org.pf.coop.portal.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_site_logo")
public class SiteLogo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1985590683488688197L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_site_logo")
	@SequenceGenerator(name="key_site_logo", 
		sequenceName="seq_key_site_logo",
		allocationSize=1)
	private Long id;
		
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_photo")
	private byte[] fileData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
