package org.pf.coop.portal.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="tab_message_text")
public class MessageText  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4492256406705654687L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "key_message_text")
	@SequenceGenerator(name="key_message_text", 
		sequenceName="seq_key_message_text",
		allocationSize=1)
	private Long id;
	
	@Column(name="f_message_for", length=50, nullable=false, unique=true)
	private String messageFor;
	
	@Column(name="f_subject", length=255, nullable=false, unique=true)
	private String subject;
	
	@Column(columnDefinition = "TEXT", name="f_content", nullable=false)
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageFor() {
		return messageFor;
	}

	public void setMessageFor(String messageFor) {
		this.messageFor = messageFor;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "MessageText [" + (id != null ? "id=" + id + ", " : "")
				+ (messageFor != null ? "messageFor=" + messageFor + ", " : "")
				+ (subject != null ? "subject=" + subject + ", " : "") + (content != null ? "content=" + content : "")
				+ "]";
	}
	
	public String getMessage(Member member) {
		String str = (content != null ? content : "");
		
		str = str.replace("${member.name}", member.getName() != null ? member.getName() : "");
		str = str.replace("${member.memId}", member.getMemId() != null ? member.getMemId() : "");
		str = str.replace("${member.residenceAddress}", member.getResidenceAddress() != null ? member.getResidenceAddress() : "");
		str = str.replace("${member.officeAddress}", member.getOfficeAddress() != null ? member.getOfficeAddress() : "");
		str = str.replace("${member.subStartDate}", member.getSubStartDate() != null ? member.getSubStartDate().toString() : "");
		str = str.replace("${member.subEndDate}", member.getSubEndDate() != null ? member.getSubEndDate().toString() : "");
		str = str.replace("${member.email}", member.getEmail() != null ? member.getEmail() : "");
		str = str.replace("${member.password}", member.getRetypePassword() != null ? member.getRetypePassword() : "");

		return str;
	}
	
	public String getMessage(MemberApplication memberApplication) {
		String str = (content != null ? content : "");
		
		str = str.replace("${memberApplication.name}", memberApplication.getName() != null ? memberApplication.getName() : "");
		str = str.replace("${memberApplication.residenceAddress}", memberApplication.getResidenceAddress() != null ? memberApplication.getResidenceAddress() : "");
		str = str.replace("${memberApplication.officeAddress}", memberApplication.getOfficeAddress() != null ? memberApplication.getOfficeAddress() : "");
		str = str.replace("${memberApplication.email}", memberApplication.getEmail() != null ? memberApplication.getEmail() : "");

		return str;
	}
	
	public String getMessage(Invitation invitation, String siteUrl) {
		String str = this.getMessage(invitation.getMember());
		
		str = str.replace("${invitation.name}", invitation.getName() != null ? invitation.getName() : "");
		str = str.replace("${invitation.url}", siteUrl + "acceptInvitation/" + invitation.getId() + "/" + invitation.getRandom());
		
		return str;
	}
}
