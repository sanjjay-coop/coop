package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;

import org.pf.coop.common.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_holiday")
public class Holiday extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778267752796560862L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_holiday")
	@SequenceGenerator(name="key_holiday", 
		sequenceName="seq_key_holiday",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_name", length=100, nullable=false)
	private String name;
	
	@Column(name="f_date", nullable=false, unique=true)
	private Date date;

	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "Holiday [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (date != null ? "date=" + date + ", " : "") 
				+ "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString("Holiday [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (date != null ? "date=" + date + ", " : "") 
				+ "]");
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString("Holiday [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (date != null ? "date=" + date + ", " : "")
				+ "]");
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
	
}
