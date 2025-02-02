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
@Table(name="tab_marital_status")
public class MaritalStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6747366648090811602L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_marital_status")
	@SequenceGenerator(name="key_marital_status", 
		sequenceName="seq_key_marital_status",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_status", length=50, nullable=false, unique=true)
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MaritalStatus [" + (id != null ? "id=" + id + ", " : "") + (status != null ? "status=" + status : "")
				+ "]";
	}
	
}
