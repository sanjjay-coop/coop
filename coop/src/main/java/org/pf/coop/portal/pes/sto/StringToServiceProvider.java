package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.ServiceProvider;
import org.pf.coop.portal.repository.ServiceProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToServiceProvider implements Converter<String, ServiceProvider>{

	@Autowired
	private ServiceProviderRepo repo;
	
	@Override
	public ServiceProvider convert(String source) {
		try {
			Optional<ServiceProvider> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
