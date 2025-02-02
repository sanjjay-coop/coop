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
@Table(name="tab_event_update")
public class EventUpdate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6945505161226664111L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_event_update")
	@SequenceGenerator(name="key_event_update", 
		sequenceName="seq_key_event_update",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_description", length=2000, nullable=false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="f_event", nullable=false)
	private Event event;
	
	@Column(name="f_update_date", nullable=false)
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description; 
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "EventUpdate [" + (id != null ? "id=" + id + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (event != null ? "event=" + event.getId() + ", " : "")
				+ (updateDate != null ? "updateDate=" + updateDate : "") + "]";
	}
}
