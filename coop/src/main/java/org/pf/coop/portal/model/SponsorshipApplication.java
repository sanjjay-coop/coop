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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_sponsorship_application")
public class SponsorshipApplication extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3391729427414634286L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_sponsorship_application")
	@SequenceGenerator(name="key_sponsorship_application", 
		sequenceName="seq_key_sponsorship_application",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_sponsorship", nullable=false)
	private Sponsorship sponsorship;
	
	@Column(name="f_application_date", nullable=false)
	private Date applicationDate;
	
	@Column(name="f_reasons_for_application", length=300, nullable=false)
	private String reasonsForApplication;
	
	@Column(name="f_education_experience", length=500, nullable=false)
	private String educationAndExperience;
	
	@Column(name="f_name", length=100, nullable=false)
	private String name;
	
	@Column(name="f_address", length=100, nullable=false)
	private String address;
	
	@Column(name="f_city", length=50, nullable=false)
	private String city;
	
	@Column(name="f_pin", length=10, nullable=true)
	private String pin;

	@Column(name="f_state", length=50, nullable=false)
	private String state;

	@Column(name="f_country", length=50, nullable=false)
	private String country;

	@Column(name="f_email", length=50, nullable=false)
	private String email;

	@Column(name="f_mobile", length=10, nullable=false)
	private String mobile;
	
	@Column(name="f_status", length=20, nullable=false)
	private String status;
	
	@Column(name="f_remarks", length=500, nullable=true)
	private String remarks;
	
	@Column(name="f_status_date", nullable=true)
	private Date statusDate;

	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sponsorship getSponsorship() {
		return sponsorship;
	}

	public void setSponsorship(Sponsorship sponsorship) {
		this.sponsorship = sponsorship;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getReasonsForApplication() {
		return reasonsForApplication;
	}

	public void setReasonsForApplication(String reasonsForApplication) {
		this.reasonsForApplication = reasonsForApplication;
	}

	public String getEducationAndExperience() {
		return educationAndExperience;
	}

	public void setEducationAndExperience(String educationAndExperience) {
		this.educationAndExperience = educationAndExperience;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "SponsorshipApplication [" + (id != null ? "id=" + id + ", " : "")
				+ (sponsorship != null ? "sponsorship=" + sponsorship + ", " : "")
				+ (applicationDate != null ? "applicationDate=" + applicationDate + ", " : "")
				+ (reasonsForApplication != null ? "reasonsForApplication=" + reasonsForApplication + ", " : "")
				+ (educationAndExperience != null ? "educationAndExperience=" + educationAndExperience + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (address != null ? "address=" + address + ", " : "")
				+ (city != null ? "city=" + city + ", " : "") + (pin != null ? "pin=" + pin + ", " : "")
				+ (state != null ? "state=" + state + ", " : "") + (country != null ? "country=" + country + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (remarks != null ? "remarks=" + remarks + ", " : "")
				+ (statusDate != null ? "statusDate=" + statusDate : "") + "]";
	}
	
	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((sponsorship != null ? sponsorship + ", " : "")
				+ (educationAndExperience != null ? "educationAndExperience=" + educationAndExperience + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (city != null ? "city=" + city + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((sponsorship != null ? sponsorship + ", " : "")
				+ (educationAndExperience != null ? "educationAndExperience=" + educationAndExperience + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (city != null ? "city=" + city + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
	
}
