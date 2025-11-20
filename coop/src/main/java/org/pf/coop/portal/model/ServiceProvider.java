package org.pf.coop.portal.model;

import java.io.Serializable;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
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
@Table(name="tab_service_provider")
public class ServiceProvider extends BaseObject implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1784028484514467088L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_service_provider")
	@SequenceGenerator(name="key_service_provider", 
		sequenceName="seq_key_service_provider",
		allocationSize=1)
	private Long id;
	
	@FullTextField
	@Column(name="f_service_name", length=200, nullable=false)
	private String serviceName;
	
	@FullTextField
	@Column(name="f_description", length=3000, nullable=false)
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
	@Column(name="f_contact_phone", length=20, nullable=true)
	private String contactPhone;
	
	@KeywordField
	@Column(name="f_contact_email", length=255, nullable=true)
	private String contactEmail;
	
	@FullTextField
	@Column(name="f_keywords", length=500, nullable=true)
	private String keywords;
	
	@IndexedEmbedded
	@ManyToOne
	@JoinColumn(name="f_owner", nullable=false)
	private Member owner;	
	
	@Column(name="f_enabled", nullable=false)
	private Boolean enabled = false;

	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
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

	@Override
	public String toString() {
		return "ServiceProvider [" + (id != null ? "id=" + id + ", " : "")
				+ (serviceName != null ? "serviceName=" + serviceName + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (address != null ? "address=" + address + ", " : "") + (city != null ? "city=" + city + ", " : "")
				+ (pin != null ? "pin=" + pin + ", " : "") + (state != null ? "state=" + state + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (contactName != null ? "contactName=" + contactName + ", " : "")
				+ (contactPhone != null ? "contactPhone=" + contactPhone + ", " : "")
				+ (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "")
				+ (keywords != null ? "keywords=" + keywords + ", " : "") + (owner != null ? "owner=" + owner : "")
				+ "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((serviceName != null ? serviceName + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (address != null ? address + ", " : "") + (city != null ? city + ", " : "")
				+ (pin != null ? pin + ", " : "") + (state != null ? state + ", " : "")
				+ (country != null ? country + ", " : "")
				+ (contactName != null ? contactName + ", " : "")
				+ (contactPhone != null ? contactPhone + ", " : "")
				+ (contactEmail != null ? contactEmail + ", " : "")
				+ (keywords != null ? keywords + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((serviceName != null ? serviceName + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (address != null ? address + ", " : "") + (city != null ? city + ", " : "")
				+ (pin != null ? pin + ", " : "") + (state != null ? state + ", " : "")
				+ (country != null ? country + ", " : "")
				+ (contactName != null ? contactName + ", " : "")
				+ (contactPhone != null ? contactPhone + ", " : "")
				+ (contactEmail != null ? contactEmail + ", " : "")
				+ (keywords != null ? keywords + ", " : ""));
	}
}
