package org.pf.coop.portal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_funding")
public class Funding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4778267752796560862L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_funding")
	@SequenceGenerator(name="key_funding", 
		sequenceName="seq_key_funding",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_applicant", nullable=false)
	private Member applicant;
	
	@Column(columnDefinition = "TEXT", name="f_purpose", nullable=false)
	private String purpose;
	
	@Column(name="f_application_date", nullable=false)
	private Date applicationDate;
	
	@Column(name="f_amount_requested", precision=10, scale=2, nullable=false)
	private BigDecimal amountRequested;
	
	@Column(name="f_sanction_date", nullable=true)
	private Date sanctionDate;
	
	@Column(name="f_amount_sanctioned", precision=10, scale=2, nullable=true)
	private BigDecimal amountSanctioned;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getApplicant() {
		return applicant;
	}

	public void setApplicant(Member applicant) {
		this.applicant = applicant;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public BigDecimal getAmountRequested() {
		return amountRequested;
	}

	public void setAmountRequested(BigDecimal amountRequested) {
		this.amountRequested = amountRequested;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public BigDecimal getAmountSanctioned() {
		return amountSanctioned;
	}

	public void setAmountSanctioned(BigDecimal amountSanctioned) {
		this.amountSanctioned = amountSanctioned;
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

	@Override
	public String toString() {
		return "Funding [" + (id != null ? "id=" + id + ", " : "")
				+ (applicant != null ? "applicant=" + applicant + ", " : "")
				+ (purpose != null ? "purpose=" + purpose + ", " : "")
				+ (applicationDate != null ? "applicationDate=" + applicationDate + ", " : "")
				+ (amountRequested != null ? "amountRequested=" + amountRequested + ", " : "")
				+ (sanctionDate != null ? "sanctionDate=" + sanctionDate + ", " : "")
				+ (amountSanctioned != null ? "amountSanctioned=" + amountSanctioned + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (address != null ? "address=" + address + ", " : "")
				+ (city != null ? "city=" + city + ", " : "") + (pin != null ? "pin=" + pin + ", " : "")
				+ (state != null ? "state=" + state + ", " : "") + (country != null ? "country=" + country + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (remarks != null ? "remarks=" + remarks + ", " : "")
				+ (statusDate != null ? "statusDate=" + statusDate : "") + "]";
	}
}

