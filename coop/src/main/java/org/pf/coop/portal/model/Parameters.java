package org.pf.coop.portal.model;

import java.io.Serializable;

import org.pf.coop.common.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_parameters")
public class Parameters extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 694961765058886835L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parameters_generator")
	@SequenceGenerator(name = "parameters_generator", sequenceName = "parameters_id_seq", allocationSize = 1)
	@Column(name = "f_id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "f_site_url", length=255)
	private String siteUrl;
	
	@Column(name = "f_mail_enable")
	private Boolean mailEnable;
	
	@Column(name = "f_mail_host", length=255)
	private String mailHost;
	
	@Column(name = "f_mail_port")
	private Integer mailPort;
	
	@Column(name = "f_mail_username", length=255)
	private String mailUsername;
	
	@Column(name = "f_mail_password", length=255)
	private String mailPassword;
	
	@Column(name = "f_mail_transport_protocol", length=50)
	private String mailTransportProtocol;
	
	@Column(name = "f_mail_smtp_port")
	private Integer mailSmtpPort;
	
	@Column(name = "f_mail_smtp_auth")
	private Boolean mailSmtpAuth;
	
	@Column(name = "f_mail_smtp_starttls_enable")
	private Boolean mailSmtpStarttlsEnable;
	
	@Column(name = "f_mail_smtp_starttls_required")
	private Boolean mailSmtpStarttlsRequired;
	
	@Column(name = "f_from_email", length=255)
	private String fromEmail;
	
	@Column(name = "f_email_signature", length=255)
	private String emailSignature;
	
	@Column(name = "f_header", length=100)
	private String header;
	
	@Column(name = "f_sub_header", length=100)
	private String subHeader;
	
	@Column(name = "f_data_directory", length=255)
	private String dataDirectory;
	
	@Column(columnDefinition = "TEXT", name="f_disclaimer", nullable=true)
	private String disclaimer;
	
	@Column(columnDefinition = "TEXT", name="f_privacy_policy", nullable=true)
	private String privacyPolicy;
	
	@Column(columnDefinition = "TEXT", name="f_terms_and_conditions", nullable=true)
	private String termsAndConditions;
	
	@Column(name = "f_email_day_limit", nullable=true)
	private Integer emailDayLimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public Boolean getMailEnable() {
		return mailEnable;
	}

	public void setMailEnable(Boolean mailEnable) {
		this.mailEnable = mailEnable;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public Integer getMailPort() {
		return mailPort;
	}

	public void setMailPort(Integer mailPort) {
		this.mailPort = mailPort;
	}

	public String getMailUsername() {
		return mailUsername;
	}

	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailTransportProtocol() {
		return mailTransportProtocol;
	}

	public void setMailTransportProtocol(String mailTransportProtocol) {
		this.mailTransportProtocol = mailTransportProtocol;
	}

	public Integer getMailSmtpPort() {
		return mailSmtpPort;
	}

	public void setMailSmtpPort(Integer mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public Boolean getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public void setMailSmtpAuth(Boolean mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public Boolean getMailSmtpStarttlsEnable() {
		return mailSmtpStarttlsEnable;
	}

	public void setMailSmtpStarttlsEnable(Boolean mailSmtpStarttlsEnable) {
		this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
	}

	public Boolean getMailSmtpStarttlsRequired() {
		return mailSmtpStarttlsRequired;
	}

	public void setMailSmtpStarttlsRequired(Boolean mailSmtpStarttlsRequired) {
		this.mailSmtpStarttlsRequired = mailSmtpStarttlsRequired;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getEmailSignature() {
		return emailSignature;
	}

	public void setEmailSignature(String emailSignature) {
		this.emailSignature = emailSignature;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSubHeader() {
		return subHeader;
	}

	public void setSubHeader(String subHeader) {
		this.subHeader = subHeader;
	}

	public String getDataDirectory() {
		return dataDirectory;
	}

	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}

	public String getPrivacyPolicy() {
		return privacyPolicy;
	}

	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public Integer getEmailDayLimit() {
		return emailDayLimit;
	}

	public void setEmailDayLimit(Integer emailDayLimit) {
		this.emailDayLimit = emailDayLimit;
	}

	@Override
	public String toString() {
		return "Parameters [" + (id != null ? "id=" + id + ", " : "")
				+ (siteUrl != null ? "siteUrl=" + siteUrl + ", " : "")
				+ (mailEnable != null ? "mailEnable=" + mailEnable + ", " : "")
				+ (mailHost != null ? "mailHost=" + mailHost + ", " : "")
				+ (mailPort != null ? "mailPort=" + mailPort + ", " : "")
				+ (mailUsername != null ? "mailUsername=" + mailUsername + ", " : "")
				+ (mailPassword != null ? "mailPassword=" + mailPassword + ", " : "")
				+ (mailTransportProtocol != null ? "mailTransportProtocol=" + mailTransportProtocol + ", " : "")
				+ (mailSmtpPort != null ? "mailSmtpPort=" + mailSmtpPort + ", " : "")
				+ (mailSmtpAuth != null ? "mailSmtpAuth=" + mailSmtpAuth + ", " : "")
				+ (mailSmtpStarttlsEnable != null ? "mailSmtpStarttlsEnable=" + mailSmtpStarttlsEnable + ", " : "")
				+ (mailSmtpStarttlsRequired != null ? "mailSmtpStarttlsRequired=" + mailSmtpStarttlsRequired + ", "
						: "")
				+ (fromEmail != null ? "fromEmail=" + fromEmail + ", " : "")
				+ (header != null ? "header=" + header + ", " : "")
				+ (subHeader != null ? "subHeader=" + subHeader + ", " : "")
				+ (dataDirectory != null ? "dataDirectory=" + dataDirectory + ", " : "")
				+ (emailDayLimit!= null ? "emailDayLimit=" + emailDayLimit : "")
				+ (emailSignature != null ? "emailSignature=" + emailSignature : "") + "]";
	}
	
}
