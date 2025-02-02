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
@Table(name = "tab_contact")
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6822813739722805964L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_generator")
	@SequenceGenerator(name = "contact_generator", sequenceName = "contact_id_seq", allocationSize = 1)
	@Column(name = "f_id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "f_name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "f_designation", nullable = false, length = 50)
	private String designation;
	
	@Column(name = "f_address", nullable = false, length = 500)
	private String address;
	
	@Column(name = "f_phone", nullable = false, length = 20)
	private String phone;
	
	@Column(name = "f_fax", length = 20)
	private String fax;
	
	@Column(name = "f_email", nullable = false, length=255)
	private String email;

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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Contact [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (designation != null ? "designation=" + designation + ", " : "")
				+ (address != null ? "address=" + address + ", " : "") + (phone != null ? "phone=" + phone + ", " : "")
				+ (fax != null ? "fax=" + fax + ", " : "") + (email != null ? "email=" + email : "") + "]";
	}
	
}
