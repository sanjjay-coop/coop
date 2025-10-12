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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_bulk_email")
public class BulkEmail extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_bulk_email")
	@SequenceGenerator(name="key_bulk_email", 
		sequenceName="seq_key_bulk_email",
		allocationSize=1)
	private Long id;
	
	@Column(columnDefinition = "TEXT", name="f_bcc", nullable=false)
	private String bcc;
	
	@Column(name="f_subject", length=200, nullable=false)
	private String subject;
	
	@Column(columnDefinition = "TEXT", name="f_message", nullable=false)
	private String message;
	
	@Column(name="f_date_sent", nullable=true)
	private Date dateSent;
	
	@Column(name="f_status", length=5, nullable=true)
	private String status;
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "BulkEmail [" + (id != null ? "id=" + id + ", " : "") + (bcc != null ? "bcc=" + bcc + ", " : "")
				+ (subject != null ? "subject=" + subject + ", " : "")
				+ (message != null ? "message=" + message + ", " : "")
				+ (dateSent != null ? "dateSent=" + dateSent + ", " : "") + (status != null ? "status=" + status : "")
				+ "]";
	}
	
	public String getHtmlString() {
		String str = this.getMessage();
		
		str = str.replace("\n", "<br/>");
		
		return str;
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((subject != null ? subject + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((subject != null ? subject + ", " : ""));
	}
	
	
}
