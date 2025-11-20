package org.pf.coop.portal.model;

import java.io.Serializable;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.pf.coop.common.BaseObject;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_organization")
public class Organization extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4244178488670986472L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_organization")
	@SequenceGenerator(name="key_organization", 
		sequenceName="seq_key_organization",
		allocationSize=1)
	private Long id;
	
	@FullTextField
	@Column(name="f_organization_name", length=200, nullable=false)
	private String organizationName;
	
	@FullTextField
	@Column(name="f_description", length=2000, nullable=false)
	private String description;
	
	@Column(name="f_address", length=100, nullable=false)
	private String address;
	
	@KeywordField
	@Column(name="f_city", length=50, nullable=false)
	private String city;
	
	@Column(name="f_pin", length=10, nullable=true)
	private String pin;
	
	@FullTextField
	@Column(name="f_state", length=50, nullable=true)
	private String state;
	
	@KeywordField
	@Column(name="f_country", length=50, nullable=true)
	private String country;
	
	@FullTextField
	@Column(name="f_contact_name", length=50, nullable=true)
	private String contactName;
	
	@KeywordField
	@Column(name="f_contact_post", length=50, nullable=true)
	private String contactPost;
	
	@KeywordField
	@Column(name="f_contact_phone", length=20, nullable=true)
	private String contactPhone;
	
	@KeywordField
	@Column(name="f_contact_email", length=255, nullable=true)
	private String contactEmail;
	
	@Column(name="f_url", length=100, nullable=true)
	private String url;
	
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

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getContactPost() {
		return contactPost;
	}

	public void setContactPost(String contactPost) {
		this.contactPost = contactPost;
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

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Organization [" + (id != null ? "id=" + id + ", " : "")
				+ (organizationName != null ? "organizationName=" + organizationName + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (address != null ? "address=" + address + ", " : "") + (city != null ? "city=" + city + ", " : "")
				+ (pin != null ? "pin=" + pin + ", " : "") + (state != null ? "state=" + state + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (contactName != null ? "contactName=" + contactName + ", " : "")
				+ (contactPost!= null ? "contactPost=" + contactPost + ", " : "")
				+ (contactPhone != null ? "contactPhone=" + contactPhone + ", " : "")
				+ (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "")
				+ (url != null ? "url=" + url + ", " : "") + "]";
	}
	
	@Override
	public void setAddDefaults(String modifiedBy) {
		this.setSearchString((organizationName != null ? organizationName + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (address != null ? address + ", " : "") + (city != null ? city + ", " : "")
				+ (state != null ? state + ", " : "")
				+ (country != null ? country + ", " : "")
				+ (contactName != null ? contactName + ", " : "")
				+ (contactPost!= null ? contactPost + ", " : "")
				+ (contactPhone != null ? contactPhone + ", " : "")
				+ (contactEmail != null ? contactEmail + ", " : "")
				+ (url != null ? url + ", " : ""));
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		this.setSearchString((organizationName != null ? organizationName + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (address != null ? address + ", " : "") + (city != null ? city + ", " : "")
				+ (state != null ? state + ", " : "")
				+ (country != null ? country + ", " : "")
				+ (contactName != null ? contactName + ", " : "")
				+ (contactPost!= null ? contactPost + ", " : "")
				+ (contactPhone != null ? contactPhone + ", " : "")
				+ (contactEmail != null ? contactEmail + ", " : "")
				+ (url != null ? url + ", " : ""));
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
	}
}