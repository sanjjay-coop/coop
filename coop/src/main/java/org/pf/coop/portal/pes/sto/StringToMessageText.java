package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.MessageText;
import org.pf.coop.portal.repository.MessageTextRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMessageText implements Converter<String, MessageText>{

	@Autowired
	private MessageTextRepo repo;
	
	@Override
	public MessageText convert(String source) {
		try {
			Optional<MessageText> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
