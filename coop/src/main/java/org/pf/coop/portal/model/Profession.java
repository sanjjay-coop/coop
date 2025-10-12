package org.pf.coop.portal.model;

import java.io.Serializable;

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
@Table(name="tab_profession")
public class Profession extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3097200406199031492L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_profession")
	@SequenceGenerator(name="key_profession", 
		sequenceName="seq_key_profession",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_name", length=50, nullable=false, unique=true)
	private String name;
	
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

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Profession [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString(this.name);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString(this.name);
	}
	
}
