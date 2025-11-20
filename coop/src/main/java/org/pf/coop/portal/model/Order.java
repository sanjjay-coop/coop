package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
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
@Table(name="tab_order")
public class Order extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1773587062657673710L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_order")
	@SequenceGenerator(name="key_order", 
		sequenceName="seq_key_order",
		allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="f_order_category", nullable=false)
	private OrderCategory orderCategory;
	
	@GenericField
	@Column(name="f_order_date", nullable=true)
	private Date orderDate;
	
	@GenericField
	@Column(name="f_order_number", length=100, nullable=true)
	private String orderNumber;
	
	@FullTextField
	@Column(name="f_subject", length=500, nullable=false)
	private String subject;
	
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

	public OrderCategory getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(OrderCategory orderCategory) {
		this.orderCategory = orderCategory;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
		return "Order [" + (id != null ? "id=" + id + ", " : "")
				+ (orderCategory != null ? "orderCategory=" + orderCategory + ", " : "")
				+ (orderDate != null ? "orderDate=" + orderDate + ", " : "")
				+ (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "")
				+ (subject != null ? "subject=" + subject + ", " : "")
				+ "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((orderDate != null ? orderDate + ", " : "")
				+ (orderNumber != null ? orderNumber + ", " : "")
				+ (subject != null ? subject + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((orderDate != null ? orderDate + ", " : "")
				+ (orderNumber != null ? orderNumber + ", " : "")
				+ (subject != null ? subject + ", " : ""));
	}
	
	
}
