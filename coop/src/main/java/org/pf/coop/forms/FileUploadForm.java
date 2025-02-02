package org.pf.coop.forms;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5900710433862597600L;
	
	private String description;
	private MultipartFile file;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
