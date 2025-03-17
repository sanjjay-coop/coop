package org.pf.coop.common;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseObject {

	@Column(columnDefinition = "TEXT", name="f_search_string", nullable=true)
	private String searchString;
	
	@Column(name="f_record_add", nullable=true)
	private Date recordAddDate;
	
	@Column(name="f_record_update_date", nullable=true)
	private Date recordUpdateDate;
	
	@Column(name="f_record_modified_by", length=20, nullable=true)
	private String recordModifiedBy;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Date getRecordAddDate() {
		return recordAddDate;
	}

	public void setRecordAddDate(Date recordAddDate) {
		this.recordAddDate = recordAddDate;
	}

	public Date getRecordUpdateDate() {
		return recordUpdateDate;
	}

	public void setRecordUpdateDate(Date recordUpdateDate) {
		this.recordUpdateDate = recordUpdateDate;
	}

	public String getRecordModifiedBy() {
		return recordModifiedBy;
	}

	public void setRecordModifiedBy(String recordModifiedBy) {
		this.recordModifiedBy = recordModifiedBy;
	}

	public void setAddDefaults(String modifiedBy) {
		this.setRecordAddDate(Calendar.getInstance().getTime());
		this.setRecordModifiedBy(modifiedBy);
		this.setRecordUpdateDate(Calendar.getInstance().getTime());
	}

	public void setUpdateDefaults(String modifiedBy) {
		this.setRecordModifiedBy(modifiedBy);
		this.setRecordUpdateDate(Calendar.getInstance().getTime());
	}
}
