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
@Table(name="tab_gender")
public class Gender implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2810181740163326055L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_gender")
	@SequenceGenerator(name="key_gender", 
		sequenceName="seq_key_gender",
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
		return "Gender [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "]";
	}

}
