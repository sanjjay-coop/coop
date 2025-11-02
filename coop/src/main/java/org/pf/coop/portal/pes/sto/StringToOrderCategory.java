package org.pf.coop.portal.pes.sto;

import java.util.Optional;

import org.pf.coop.portal.model.OrderCategory;
import org.pf.coop.portal.repository.OrderCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOrderCategory implements Converter<String, OrderCategory>{

	@Autowired
	private OrderCategoryRepo repo;
	
	@Override
	public OrderCategory convert(String source) {
		try {
			Optional<OrderCategory> optionalEntity = repo.findById(Long.valueOf(Long.parseLong(source)));
			if (optionalEntity.isPresent()) return optionalEntity.get();
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
}
