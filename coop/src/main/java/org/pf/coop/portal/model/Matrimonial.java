package org.pf.coop.portal.model;

import java.io.Serializable;
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
@Table(name="tab_matrimonial")
public class Matrimonial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4442003395578092453L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_matrimonial")
	@SequenceGenerator(name="key_matrimonial", 
		sequenceName="seq_key_matrimonial",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_owner", nullable=false)
	private Member owner;
	
	@Column(name="f_looking_for", length=20, nullable=false)
	private String lookingFor;
	
	@Column(name="f_description", length=2000, nullable=false)
	private String description;
	
	@Column(name="f_age", nullable=false)
	private Integer age;
	
	@ManyToOne
	@JoinColumn(name="f_education_level", nullable=false)
	private EducationLevel educationLevel;
	
	@ManyToOne
	@JoinColumn(name="f_occupation", nullable=false)
	private Occupation occupation;
	
	@Column(name="f_exp_description", length=2000, nullable=false)
	private String expDescription;
	
	@Column(name="f_exp_min_age", nullable=false)
	private Integer expMinAge;
	
	@Column(name="f_exp_max_age", nullable=false)
	private Integer expMaxAge;
	
	@ManyToOne
	@JoinColumn(name="f_exp_education_level", nullable=false)
	private EducationLevel expEducationLevel;
	
	@ManyToOne
	@JoinColumn(name="f_exp_occupation", nullable=false)
	private Occupation expOccupation;
	
	@Column(name="f_mobile", length=20, nullable=false)
	private String mobile;
	
	@Column(name="f_email", length=255, nullable=false)
	private String email;
	
	@Column(name="f_add_date", nullable=true)
	private Date addDate;
	
	@Column(name="f_enabled", nullable=false)
	private Boolean enabled = false;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public String getLookingFor() {
		return lookingFor;
	}

	public void setLookingFor(String lookingFor) {
		this.lookingFor = lookingFor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getExpDescription() {
		return expDescription;
	}

	public void setExpDescription(String expDescription) {
		this.expDescription = expDescription;
	}

	public Integer getExpMinAge() {
		return expMinAge;
	}

	public void setExpMinAge(Integer expMinAge) {
		this.expMinAge = expMinAge;
	}

	public Integer getExpMaxAge() {
		return expMaxAge;
	}

	public void setExpMaxAge(Integer expMaxAge) {
		this.expMaxAge = expMaxAge;
	}

	public EducationLevel getExpEducationLevel() {
		return expEducationLevel;
	}

	public void setExpEducationLevel(EducationLevel expEducationLevel) {
		this.expEducationLevel = expEducationLevel;
	}

	public Occupation getExpOccupation() {
		return expOccupation;
	}

	public void setExpOccupation(Occupation expOccupation) {
		this.expOccupation = expOccupation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "Matrimonial [" + (id != null ? "id=" + id + ", " : "") + (owner != null ? "owner=" + owner + ", " : "")
				+ (lookingFor != null ? "lookingFor=" + lookingFor + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (age != null ? "age=" + age + ", " : "")
				+ (educationLevel != null ? "educationLevel=" + educationLevel + ", " : "")
				+ (occupation != null ? "occupation=" + occupation + ", " : "")
				+ (expDescription != null ? "expDescription=" + expDescription + ", " : "")
				+ (expMinAge != null ? "expMinAge=" + expMinAge + ", " : "")
				+ (expMaxAge != null ? "expMaxAge=" + expMaxAge + ", " : "")
				+ (expEducationLevel != null ? "expEducationLevel=" + expEducationLevel + ", " : "")
				+ (expOccupation != null ? "expOccupation=" + expOccupation + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (addDate != null ? "addDate=" + addDate + ", " : "") + (enabled != null ? "enabled=" + enabled : "")
				+ "]";
	}

}
