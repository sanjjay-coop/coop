package org.pf.coop.portal.model.library;

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
@Table(name="tab_library_check_in")
public class CheckIn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6258408462723244261L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_library_check_in")
	@SequenceGenerator(name="key_library_check_in", 
		sequenceName="seq_key_library_check_in",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_title", nullable=false)
	private Title title;
	
	@Column(name="f_member_id", length=50, nullable=false)
	private String memberId;
	
	@Column(name="f_check_out_date", nullable=false)
	private Date checkOutDate;
	
	@Column(name="f_issued_by", length=50, nullable=false)
	private String issuedBy;
	
	@Column(name="f_check_in_date", nullable=false)
	private Date checkInDate;
	
	@Column(name="f_received_by", length=50, nullable=false)
	private String receivedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	@Override
	public String toString() {
		return "CheckIn [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (memberId != null ? "memberId=" + memberId + ", " : "")
				+ (checkOutDate != null ? "checkOutDate=" + checkOutDate + ", " : "")
				+ (issuedBy != null ? "issuedBy=" + issuedBy + ", " : "")
				+ (checkInDate != null ? "checkInDate=" + checkInDate + ", " : "")
				+ (receivedBy != null ? "receivedBy=" + receivedBy : "") + "]";
	}
}
