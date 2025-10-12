package org.pf.coop.portal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
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
@Table(name="tab_receipt")
public class Receipt extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1773587062657673710L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_receipt")
	@SequenceGenerator(name="key_receipt", 
		sequenceName="seq_key_receipt",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_member", nullable=false)
	private Member member;
	
	@Column(name="f_receipt_date", nullable=true)
	private Date receiptDate;
	
	@Column(name="f_receipt_number", length=100, nullable=false, unique=true)
	private String receiptNumber;
	
	@Column(name="f_details", length=500, nullable=false)
	private String details;
	
	@Column(name="f_amount", precision=10, scale=2, nullable=false)
	private BigDecimal amount;
	
	@Column(name="f_file_name", length=255, nullable=true)
	private String fileName;
	
	@Column(name="f_file_type", length=255, nullable=true)
	private String fileType;
	
	@Lob
    @Column(name = "f_document")
	private byte[] document;
	
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
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
		return "Receipt [" + (id != null ? "id=" + id + ", " : "") + (member != null ? "member=" + member + ", " : "")
				+ (receiptDate != null ? "receiptDate=" + receiptDate + ", " : "")
				+ (receiptNumber != null ? "receiptNumber=" + receiptNumber + ", " : "")
				+ (details != null ? "details=" + details + ", " : "")
				+ (amount != null ? "amount=" + amount + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (fileType != null ? "fileType=" + fileType + ", " : "")
				+ (document != null ? "document=" + Arrays.toString(document) : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((member != null ? member + ", " : "")
				+ (receiptDate != null ? receiptDate + ", " : "")
				+ (receiptNumber != null ? receiptNumber + ", " : "")
				+ (details != null ? details + ", " : "")
				+ (amount != null ? amount + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((member != null ? member + ", " : "")
				+ (receiptDate != null ? receiptDate + ", " : "")
				+ (receiptNumber != null ? receiptNumber + ", " : "")
				+ (details != null ? details + ", " : "")
				+ (amount != null ? amount + ", " : ""));
	}

}
