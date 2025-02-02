package org.pf.coop.portal.model.accounts;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="tab_accounts_income")
public class Income implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6403786738572559476L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_income")
	@SequenceGenerator(name="key_income", 
		sequenceName="seq_key_income",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_amount", precision=10, scale=2, nullable=false)
	private BigDecimal amount;
	
	@Column(name="f_narration", length=500, nullable=false)
	private String narration;
	
	@Column(name="f_transaction_date", nullable=false)
	private Date transactionDate;
	
	@Column(name="f_receipt_number", length=20, nullable=false)
	private String receiptNumber;
	
	@Column(name="f_receipt_date", nullable=false)
	private Date receiptDate;
	
	@Column(name="f_received_from", length=100, nullable=false)
	private String receivedFrom;
	
	@Column(name="f_mode_of_receipt", length=20, nullable=false)
	private String modeOfReceipt;
	
	@ManyToOne
	@JoinColumn(name="f_head_of_account", nullable=false)
	private HeadOfAccount headOfAccount; 
	
	@Column(name="f_update_date", nullable=false)
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public String getModeOfReceipt() {
		return modeOfReceipt;
	}

	public void setModeOfReceipt(String modeOfReceipt) {
		this.modeOfReceipt = modeOfReceipt;
	}

	public HeadOfAccount getHeadOfAccount() {
		return headOfAccount;
	}

	public void setHeadOfAccount(HeadOfAccount headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Income [" + (id != null ? "id=" + id + ", " : "") + (amount != null ? "amount=" + amount + ", " : "")
				+ (narration != null ? "narration=" + narration + ", " : "")
				+ (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "")
				+ (receiptNumber != null ? "receiptNumber=" + receiptNumber + ", " : "")
				+ (receiptDate != null ? "receiptDate=" + receiptDate + ", " : "")
				+ (receivedFrom != null ? "receivedFrom=" + receivedFrom + ", " : "")
				+ (modeOfReceipt != null ? "modeOfReceipt=" + modeOfReceipt + ", " : "")
				+ (headOfAccount != null ? "headOfAccount=" + headOfAccount + ", " : "")
				+ (updateDate != null ? "updateDate=" + updateDate : "") + "]";
	}
	
}
