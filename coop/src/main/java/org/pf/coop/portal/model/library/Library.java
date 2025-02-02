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
@Table(name="tab_library_library")
public class Library implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6833064548815126639L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_library_library")
	@SequenceGenerator(name="key_library_library", 
		sequenceName="seq_key_library_library",
		allocationSize=1)
	private Long id;

	@Column(name="f_short_name", length=10, nullable=false, unique=true)
	private String shortName;

	@Column(name="f_name", length=200, nullable=false, unique=true)
	private String name;
	
	@Column(name="f_address", length=100, nullable=true)
	private String address;

	@Column(name="f_city", length=50, nullable=true)
	private String city;

	@Column(name="f_pin", length=50, nullable=true)
	private String pin;
	
	@Column(name="f_state", length=50, nullable=true)
	private String state;

	@Column(name="f_country", length=50, nullable=true)
	private String country;

	@Column(name="f_mobile", length=10, nullable=true)
	private String mobile;

	@Column(name="f_phone", length=10, nullable=true)
	private String phone;

	@Column(name="f_email", length=100, nullable=true)
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Library [" + (id != null ? "id=" + id + ", " : "")
				+ (shortName != null ? "shortName=" + shortName + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (address != null ? "address=" + address + ", " : "")
				+ (city != null ? "city=" + city + ", " : "") + (pin != null ? "pin=" + pin + ", " : "")
				+ (state != null ? "state=" + state + ", " : "") + (country != null ? "country=" + country + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "") + (phone != null ? "phone=" + phone + ", " : "")
				+ (email != null ? "email=" + email : "") + "]";
	}
	
}
