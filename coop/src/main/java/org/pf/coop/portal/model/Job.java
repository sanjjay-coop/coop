package org.pf.coop.portal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.pf.coop.common.BaseObject;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_job")
public class Job extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811609931614130656L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_employment")
	@SequenceGenerator(name="key_employment", 
		sequenceName="seq_key_employment",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_position", length=50, nullable=false)
	private String position;
	
	@Column(name="f_salary", precision=10, scale=2, nullable=false)
	private BigDecimal salary;
	
	@ManyToOne
	@JoinColumn(name="f_minimum_qualification", nullable=false)
	private EducationLevel minimumQualification;
	
	@Column(name="f_firm_name", length=200, nullable=false)
	private String firmName;
	
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
	
	@Column(name="f_contact_name", length=50, nullable=false)
	private String contactName;
	
	@Column(name="f_contact_designation", length=50, nullable=false)
	private String contactDesignation;
	
	@Column(name="f_contact_phone", length=20, nullable=false)
	private String contactPhone;
	
	@Column(name="f_contact_email", length=255, nullable=false)
	private String contactEmail;
	
	@Column(name="f_url", length=100, nullable=true)
	private String url;
	
	@Column(name="f_last_date", nullable=false)
	private Date lastDate;
	
	@Column(name="f_description", length=2000, nullable=false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="f_owner", nullable=false)
	private Member owner;

	@Column(name="f_add_date", nullable=true)
	private Date addDate;
	
	@Column(name="f_enabled", nullable=false)
	private Boolean enabled = false;
	
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_photo")
	private byte[] fileData;
	
	@Transient
	private MultipartFile file;

	@Transient
	private String searchFor;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public EducationLevel getMinimumQualification() {
		return minimumQualification;
	}

	public void setMinimumQualification(EducationLevel minimumQualification) {
		this.minimumQualification = minimumQualification;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactDesignation() {
		return contactDesignation;
	}

	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Job [" + (id != null ? "id=" + id + ", " : "") + (position != null ? "position=" + position + ", " : "")
				+ (salary != null ? "salary=" + salary + ", " : "")
				+ (minimumQualification != null ? "minimumQualification=" + minimumQualification + ", " : "")
				+ (firmName != null ? "firmName=" + firmName + ", " : "")
				+ (address != null ? "address=" + address + ", " : "") + (city != null ? "city=" + city + ", " : "")
				+ (pin != null ? "pin=" + pin + ", " : "") + (state != null ? "state=" + state + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (contactName != null ? "contactName=" + contactName + ", " : "")
				+ (contactDesignation != null ? "contactDesignation=" + contactDesignation + ", " : "")
				+ (contactPhone != null ? "contactPhone=" + contactPhone + ", " : "")
				+ (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "")
				+ (url != null ? "url=" + url + ", " : "") + (lastDate != null ? "lastDate=" + lastDate + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (owner != null ? "owner=" + owner + ", " : "") + (addDate != null ? "addDate=" + addDate + ", " : "")
				+ (enabled != null ? "enabled=" + enabled : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((position != null ? "position=" + position + ", " : "")
				+ (salary != null ? salary + ", " : "")
				+ (firmName != null ? firmName + ", " : "")
				+ (city != null ? city + ", " : "")
				+ (country != null ? country + ", " : "")
				+ (contactName != null ? contactName + ", " : "")
				+ (contactPhone != null ? contactPhone + ", " : "")
				+ (contactEmail != null ? contactEmail + ", " : "")
				+ (description != null ? "description=" + description + ", " : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((position != null ? "position=" + position + ", " : "")
				+ (salary != null ? salary + ", " : "")
				+ (firmName != null ? firmName + ", " : "")
				+ (city != null ? city + ", " : "")
				+ (country != null ? country + ", " : "")
				+ (contactName != null ? contactName + ", " : "")
				+ (contactPhone != null ? contactPhone + ", " : "")
				+ (contactEmail != null ? contactEmail + ", " : "")
				+ (description != null ? "description=" + description + ", " : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
	
}
