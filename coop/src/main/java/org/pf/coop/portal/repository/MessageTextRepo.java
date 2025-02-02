package org.pf.coop.portal.repository;

import org.pf.coop.portal.model.MessageText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTextRepo extends JpaRepository<MessageText, Long>{
	
	public MessageText findByMessageFor(String messageFor);
	
	public MessageText findBySubject(String subject);
}
