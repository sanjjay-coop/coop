package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_audit")
public class Audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5377746372070626615L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_audit")
	@SequenceGenerator(name="key_audit", 
		sequenceName="seq_key_audit",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_target_id", nullable=false)
	private Long targetId;
	
	@Column(columnDefinition = "TEXT", name="f_object_string", nullable=false)
	private String objectString;
	
	@Column(name="f_transaction_date", nullable=false)
	private Date transactionDate;
	
	@Column(name="f_transaction_type", length=50, nullable=false)
	private String transactionType;
	
	@Column(name="f_object_name", length=50, nullable=false)
	private String objectName;
	
	@Column(name="f_object_modified_by", length=50, nullable=false)
	private String objectModifiedBy;

	public Audit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Audit(String objectModifiedBy, 
			String objectName, 
			String objectString, 
			Long targetId, 
			Date transactionDate, 
			String transactionType) {
		super();
		this.targetId = targetId;
		this.objectString = objectString;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.objectName = objectName;
		this.objectModifiedBy = objectModifiedBy;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getObjectString() {
		return objectString;
	}

	public void setObjectString(String objectString) {
		this.objectString = objectString;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectModifiedBy() {
		return objectModifiedBy;
	}

	public void setObjectModifiedBy(String objectModifiedBy) {
		this.objectModifiedBy = objectModifiedBy;
	}
	
}
