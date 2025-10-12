package org.pf.coop.portal.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="tab_member_group")
public class MemberGroup extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6338516558211734720L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_member_group")
	@SequenceGenerator(name="key_member_group", 
		sequenceName="seq_key_member_group",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_name", length=50, nullable=false, unique=true)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="f_parent_group", nullable=true)
	private MemberGroup parentGroup;
	
	@OneToMany(mappedBy = "parentGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<MemberGroup> children = new HashSet<MemberGroup>();
	
	@Transient
	private String searchFor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MemberGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(MemberGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	public Set<MemberGroup> getChildren() {
		return children;
	}

	public void setChildren(Set<MemberGroup> children) {
		this.children = children;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	@Override
	public String toString() {
		return "MemberGroup [" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "") + "]";
	}
	
	public String getLabel() {
		String str = this.name;	
		return str;
	}
	
	public Boolean getAncestor(Long id) {
		if (this.parentGroup == null) return false;
		else {
			if (id.equals(this.parentGroup.getId())) return true;
			else return this.parentGroup.getAncestor(id);
		}
	}

	@Override
	public void setAddDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setAddDefaults(modifiedBy);
		this.setSearchString(name != null ? name : "");
	}

	@Override
	public void setUpdateDefaults(String modifiedBy) {
		// TODO Auto-generated method stub
		super.setUpdateDefaults(modifiedBy);
		this.setSearchString(name != null ? name : "");
	}
}

