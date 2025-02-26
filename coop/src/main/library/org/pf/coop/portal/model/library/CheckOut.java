package org.pf.coop.portal.model.library;

import java.io.Serializable;
import java.util.Date;

import org.pf.coop.portal.model.Member;

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
@Table(name="tab_library_check_out")
public class CheckOut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -117829755049327381L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_library_check_out")
	@SequenceGenerator(name="key_library_check_out", 
		sequenceName="seq_key_library_check_out",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_title", nullable=false, unique=true)
	private Title title;
	
	@ManyToOne
	@JoinColumn(name="f_member", nullable=false)
	private Member member;
	
	@Column(name="f_transaction_date", nullable=false)
	private Date transactionDate;
	
	@Column(name="f_due_date", nullable=false)
	private Date dueDate;
	
	@Column(name="f_issued_by", length=50, nullable=false)
	private String issuedBy;

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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	@Override
	public String toString() {
		return "CheckOut [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (member != null ? "member=" + member + ", " : "")
				+ (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "")
				+ (dueDate != null ? "dueDate=" + dueDate + ", " : "")
				+ (issuedBy != null ? "issuedBy=" + issuedBy : "") + "]";
	}
	
}
