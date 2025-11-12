package org.pf.coop.portal.model.accounts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@Table(name="tab_accounts_expenditure")
public class Expenditure extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6403786738572559476L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_expenditure")
	@SequenceGenerator(name="key_expenditure", 
		sequenceName="seq_key_expenditure",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_amount", precision=10, scale=2, nullable=false)
	private BigDecimal amount;
	
	@Column(name="f_towards", length=100, nullable=true)
	private String towards;
	
	@Column(name="f_narration", length=500, nullable=false)
	private String narration;
	
	@Column(name="f_transaction_date", nullable=false)
	private Date transactionDate;
	
	@Column(name="f_voucher_invoice_number", length=20, nullable=false)
	private String voucherInvoiceNumber;
	
	@Column(name="f_payment_date", nullable=false)
	private Date voucherDate;
	
	@Column(name="f_paid_to", length=100, nullable=false)
	private String paidTo;
	
	@Column(name="f_mode_of_payment", length=20, nullable=false)
	private String modeOfPayment;
	
	@ManyToOne
	@JoinColumn(name="f_head_of_account", nullable=false)
	private HeadOfAccount headOfAccount; 
	
	@Column(name="f_update_date", nullable=false)
	private Date updateDate;

	@Transient
	private String searchFor;

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

	public String getTowards() {
		return towards;
	}

	public void setTowards(String towards) {
		this.towards = towards;
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

	public String getVoucherInvoiceNumber() {
		return voucherInvoiceNumber;
	}

	public void setVoucherInvoiceNumber(String voucherInvoiceNumber) {
		this.voucherInvoiceNumber = voucherInvoiceNumber;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date paymentDate) {
		this.voucherDate = paymentDate;
	}

	public String getPaidTo() {
		return paidTo;
	}

	public void setPaidTo(String paidTo) {
		this.paidTo = paidTo;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
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

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Expenditure [" + (id != null ? "id=" + id + ", " : "")
				+ (amount != null ? "amount=" + amount + ", " : "")
				+ (towards != null ? "towards=" + towards + ", " : "")
				+ (narration != null ? "narration=" + narration + ", " : "")
				+ (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "")
				+ (voucherInvoiceNumber != null ? "voucherInvoiceNumber=" + voucherInvoiceNumber + ", " : "")
				+ (voucherDate != null ? "paymentDate=" + voucherDate + ", " : "")
				+ (paidTo != null ? "paidTo=" + paidTo + ", " : "")
				+ (modeOfPayment != null ? "modeOfPayment=" + modeOfPayment + ", " : "")
				+ (headOfAccount != null ? "headOfAccount=" + headOfAccount + ", " : "")
				+ (updateDate != null ? "updateDate=" + updateDate : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((amount != null ? amount + ", " : "")
				+ (towards != null ? towards + ", " : "")
				+ (narration != null ? narration + ", " : "")
				+ (transactionDate != null ? transactionDate + ", " : "")
				+ (voucherInvoiceNumber != null ? voucherInvoiceNumber + ", " : "")
				+ (voucherDate != null ? voucherDate + ", " : "")
				+ (paidTo != null ? paidTo + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((amount != null ? amount + ", " : "")
				+ (towards != null ? towards + ", " : "")
				+ (narration != null ? narration + ", " : "")
				+ (transactionDate != null ? transactionDate + ", " : "")
				+ (voucherInvoiceNumber != null ? voucherInvoiceNumber + ", " : "")
				+ (voucherDate != null ? voucherDate + ", " : "")
				+ (paidTo != null ? paidTo + ", " : ""));
	}

}
