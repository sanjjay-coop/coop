package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.pf.coop.common.BaseObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_event")
public class Event extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5025830286885689199L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_event")
	@SequenceGenerator(name="key_event", 
		sequenceName="seq_key_event",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_title", length=500, nullable=false)
	private String title;
	
	@Column(columnDefinition = "TEXT", name="f_description", nullable=false)
	private String description;
	
	@GenericField
	@Column(name="f_start_date", nullable=false)
	private Date startDate;
	
	@GenericField
	@Column(name="f_end_date", nullable=false)
	private Date endDate;
	
	@GenericField
	@Column(name="f_publish", nullable=false)
	private Boolean publish;
	
	@Column(name="f_venue", length=100, nullable=true)
	private String venue;
	
	@Column(name="f_city", length=100, nullable=true)
	private String city;
	
	@Column(name="f_state", length=100, nullable=true)
	private String state;
	
	@ManyToOne
	@JoinColumn(name="f_event_type", nullable=false)
	private EventType eventType;
	
	@OneToMany(fetch = FetchType.EAGER, targetEntity=EventUpdate.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="f_event")
	@OrderBy("updateDate DESC")
	private Set<EventUpdate> updates = new HashSet<EventUpdate>();
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getPublish() {
		return publish;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	
	public Set<EventUpdate> getUpdates() {
		return updates;
	}

	public void setUpdates(Set<EventUpdate> updates) {
		this.updates = updates;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "Event [" + (id != null ? "id=" + id + ", " : "") + (title != null ? "title=" + title + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (endDate != null ? "endDate=" + endDate + ", " : "")
				+ (publish != null ? "publish=" + publish + ", " : "")
				+ (venue != null ? "venue=" + venue + ", " : "")
				+ (city != null ? "city=" + city + ", " : "")
				+ (state != null ? "state=" + state + ", " : "")
				+ (eventType != null ? "eventType=" + eventType : "") + "]";
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (venue != null ? venue + ", " : "")
				+ (city != null ? city + ", " : "")
				+ (state != null ? state + ", " : ""));
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		
		this.setSearchString((title != null ? title + ", " : "")
				+ (description != null ? description + ", " : "")
				+ (venue != null ? venue + ", " : "")
				+ (city != null ? city + ", " : "")
				+ (state != null ? state + ", " : ""));
	}
	
}