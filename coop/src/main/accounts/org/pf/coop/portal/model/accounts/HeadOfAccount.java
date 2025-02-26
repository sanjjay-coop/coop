package org.pf.coop.portal.model.accounts;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_accounts_head")
public class HeadOfAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4254894504176848292L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_head")
	@SequenceGenerator(name="key_head", 
		sequenceName="seq_key_head",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_code", length=20, nullable=false, unique=true)
	private String code;
	
	@Column(name="f_name", length=100, nullable=false, unique=true)
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HeadOfAccount [" + (id != null ? "id=" + id + ", " : "") + (code != null ? "code=" + code + ", " : "")
				+ (name != null ? "name=" + name : "") + "]";
	}

}
