package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.EventType;
import org.pf.coop.portal.repository.EventTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEventType implements Converter<String, EventType>{

	@Autowired
	private EventTypeRepo repo;
	
	@Override
	public EventType convert(String source) {
		try {
			Optional<EventType> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
