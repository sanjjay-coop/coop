package org.pf.coop.portal.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_profession")
public class Profession implements Serializable {
	
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

	@Override
	public String toString() {
		return "Profession [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "]";
	}
	
}
