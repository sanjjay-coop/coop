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
@Table(name="tab_role")
public class Role extends BaseObject implements Serializable, Comparable<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6496756960904504875L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_role")
	@SequenceGenerator(name="key_role", 
		sequenceName="seq_key_role",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_code", length=50, nullable=false, unique=true)
	private String code;
	
	@Column(name="description", length=100, nullable=false)
	private String description;
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	// compareTo method to sort in 
    // ascending order 
    public int compareTo(Role obj) 
    { 
        return this.code.compareToIgnoreCase(obj.getCode()); 
    } 

	@Override
	public String toString() {
		return "Role [" + (id != null ? "id=" + id + ", " : "") + (code != null ? "code=" + code + ", " : "")
				+ (description != null ? "description=" + description : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((code != null ? code + ", " : "")
				+ (description != null ? description : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((code != null ? code + ", " : "")
				+ (description != null ? description : ""));
	}

}

