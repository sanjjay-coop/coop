package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="tab_member")
public class Member implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4397165661675829237L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_member")
	@SequenceGenerator(name="key_member", 
		sequenceName="seq_key_member",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_salutation", nullable=false)
	private Salutation salutation;
	
	@Column(name="f_first_name", length=50, nullable=false)
	private String firstName;
	
	@Column(name="f_middle_name", length=50, nullable=true)
	private String middleName;
	
	@Column(name="f_last_name", length=50, nullable=false)
	private String lastName;
	
	@Column(name="f_mem_id", length=20, nullable=false, unique=true)
	private String memId;
	
	@ManyToOne
	@JoinColumn(name="f_mem_group", nullable=true)
	private MemberGroup memGroup; 
	
	@ManyToOne
	@JoinColumn(name="f_mem_type", nullable=false)
	private MemberType memType;
	
	@ManyToOne
	@JoinColumn(name="f_gender", nullable=false)
	private Gender gender;
	
	@ManyToOne
	@JoinColumn(name="f_education_level", nullable=true)
	private EducationLevel educationLevel;
	
	@Column(name="f_res_address", length=100, nullable=true)
	private String resAddress;
	
	@Column(name="f_res_city", length=50, nullable=true)
	private String resCity;
	
	@Column(name="f_res_pin", length=10, nullable=true)
	private String resPin;
	
	@Column(name="f_res_state", length=50, nullable=true)
	private String resState;
	
	@Column(name="f_res_country", length=50, nullable=true)
	private String resCountry;
	
	@Column(name="f_mobile", length=20, nullable=false, unique=true)
	private String mobile;
	
	@Column(name="f_off_designation", length=50, nullable=true)
	private String offDesignation;
	
	@Column(name="f_off_name", length=200, nullable=true)
	private String offName;
	
	@Column(name="f_off_address", length=100, nullable=true)
	private String offAddress;
	
	@Column(name="f_off_city", length=50, nullable=true)
	private String offCity;
	
	@Column(name="f_off_pin", length=10, nullable=true)
	private String offPin;
	
	@Column(name="f_off_state", length=50, nullable=true)
	private String offState;
	
	@Column(name="f_off_country", length=50, nullable=true)
	private String offCountry;
	
	@Column(name="f_email", length=100, nullable=false, unique=true)
	private String email;
	
	@Column(name="f_date_of_birth", nullable=true)
	private Date dateOfBirth;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(
			name = "tab_member_role",
			joinColumns = @JoinColumn(name = "member_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_photo")
	private byte[] fileData;
	
	@Column(name="f_profile_public", nullable=false)
	private Boolean profilePublic;

	@Column(name="f_education", length=500, nullable=true)
	private String education;
	
	@Column(columnDefinition = "TEXT", name="f_experience", nullable=true)
	private String experience;
	
	@Column(name="f_password", length=255, nullable=false)
	private String password;
	
	@Column(name="f_sub_end_date", nullable=false)
	private Date subEndDate;
	
	@Column(name="f_sub_start_date", nullable=false)
	private Date subStartDate;
	
	@Column(name="f_aadhar", length=20, nullable=true)
	private String aadhaar;
	
	@Transient
	private String retypePassword;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="f_member")
	@OrderBy("receiptDate DESC")
	private Set<Receipt> receipts = new HashSet<Receipt>();

	@ManyToOne
	@JoinColumn(name="f_marital_status", nullable=true)
	private MaritalStatus maritalStatus;
	
	@ManyToOne
	@JoinColumn(name="f_occupation", nullable=true)
	private Occupation occupation;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Salutation getSalutation() {
		return salutation;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public MemberGroup getMemGroup() {
		return memGroup;
	}

	public void setMemGroup(MemberGroup memGroup) {
		this.memGroup = memGroup;
	}

	public MemberType getMemType() {
		return memType;
	}

	public void setMemType(MemberType memType) {
		this.memType = memType;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getResAddress() {
		return resAddress;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	public String getResCity() {
		return resCity;
	}

	public void setResCity(String resCity) {
		this.resCity = resCity;
	}

	public String getResPin() {
		return resPin;
	}

	public void setResPin(String resPin) {
		this.resPin = resPin;
	}

	public String getResState() {
		return resState;
	}

	public void setResState(String resState) {
		this.resState = resState;
	}

	public String getResCountry() {
		return resCountry;
	}

	public void setResCountry(String resCountry) {
		this.resCountry = resCountry;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOffDesignation() {
		return offDesignation;
	}

	public void setOffDesignation(String offDesignation) {
		this.offDesignation = offDesignation;
	}

	public String getOffName() {
		return offName;
	}

	public void setOffName(String offName) {
		this.offName = offName;
	}

	public String getOffAddress() {
		return offAddress;
	}

	public void setOffAddress(String offAddress) {
		this.offAddress = offAddress;
	}

	public String getOffCity() {
		return offCity;
	}

	public void setOffCity(String offCity) {
		this.offCity = offCity;
	}

	public String getOffPin() {
		return offPin;
	}

	public void setOffPin(String offPin) {
		this.offPin = offPin;
	}

	public String getOffState() {
		return offState;
	}

	public void setOffState(String offState) {
		this.offState = offState;
	}

	public String getOffCountry() {
		return offCountry;
	}

	public void setOffCountry(String offCountry) {
		this.offCountry = offCountry;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public Boolean getProfilePublic() {
		return profilePublic;
	}

	public void setProfilePublic(Boolean profilePublic) {
		this.profilePublic = profilePublic;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getSubEndDate() {
		return subEndDate;
	}

	public void setSubEndDate(Date subEndDate) {
		this.subEndDate = subEndDate;
	}

	public Date getSubStartDate() {
		return subStartDate;
	}

	public void setSubStartDate(Date subStartDate) {
		this.subStartDate = subStartDate;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public Set<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Receipt> receipts) {
		this.receipts = receipts;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	@Override
	public String toString() {
		return "Member [" + (id != null ? "id=" + id + ", " : "")
				+ (salutation != null ? "salutation=" + salutation + ", " : "")
				+ (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (middleName != null ? "middleName=" + middleName + ", " : "")
				+ (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (memId != null ? "memId=" + memId + ", " : "") 
				+ (memGroup != null ? "memGroup=" + memGroup + ", " : "")
				+ (memType != null ? "memType=" + memType + ", " : "")
				+ (gender != null ? "gender=" + gender + ", " : "")
				+ (educationLevel != null ? "educationLevel=" + educationLevel + ", " : "")
				+ (resAddress != null ? "resAddress=" + resAddress + ", " : "")
				+ (resCity != null ? "resCity=" + resCity + ", " : "")
				+ (resPin != null ? "resPin=" + resPin + ", " : "")
				+ (resState != null ? "resState=" + resState + ", " : "")
				+ (resCountry != null ? "resCountry=" + resCountry + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (offDesignation != null ? "offDesignation=" + offDesignation + ", " : "")
				+ (offName != null ? "offName=" + offName + ", " : "")
				+ (offAddress != null ? "offAddress=" + offAddress + ", " : "")
				+ (offCity != null ? "offCity=" + offCity + ", " : "")
				+ (offPin != null ? "offPin=" + offPin + ", " : "")
				+ (offState != null ? "offState=" + offState + ", " : "")
				+ (offCountry != null ? "offCountry=" + offCountry + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (dateOfBirth != null ? "dateOfBirth=" + dateOfBirth + ", " : "")
				+ (roles != null ? "roles=" + roles + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (fileType != null ? "fileType=" + fileType + ", " : "")
				+ (fileData != null ? "fileData=" + Arrays.toString(fileData) + ", " : "")
				+ (profilePublic != null ? "profilePublic=" + profilePublic + ", " : "")
				+ (education != null ? "education=" + education + ", " : "")
				+ (experience != null ? "experience=" + experience + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (subEndDate != null ? "subEndDate=" + subEndDate + ", " : "")
				+ (subStartDate != null ? "subStartDate=" + subStartDate + ", " : "")
				+ (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "")
				+ (occupation != null ? "occupation=" + occupation + ", " : "")
				+ (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "") + "]";
	}
	
	public String getName() {
		String str = this.salutation.getName() + " " +
				this.getFirstName() + " ";
		
		if (this.getMiddleName()==null) str = str + this.getLastName();
		else str = str + this.getMiddleName() + " " + this.getLastName();
		
		str = str.replace("  ", " ");
		str = str.trim();
		
		return str;
	}
	
	public String getResidenceAddress() {
		return (resAddress != null ? resAddress + ", " : " ")
				+ (resCity != null ? resCity + ", " : " ")
				+ (resPin != null ? resPin + ", " : " ")
				+ (resState != null ? resState + ", " : " ")
				+ (resCountry != null ? resCountry : " ");
	}
	
	public String getOfficeAddress() {
		return (offAddress != null ? offAddress + ", " : " ")
				+ (offCity != null ? offCity + ", " : " ")
				+ (offPin != null ? offPin + ", " : " ")
				+ (offState != null ? offState + ", " : " ")
				+ (offCountry != null ? offCountry : " ");
	}
}