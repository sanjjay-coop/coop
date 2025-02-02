package org.pf.coop.portal.model.library;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_library_title_type")
public class TitleType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1494031476312779153L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_library_title_type")
	@SequenceGenerator(name="key_library_title_type", 
		sequenceName="seq_key_library_title_type",
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
		return "TitleType [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "]";
	}
	
}
