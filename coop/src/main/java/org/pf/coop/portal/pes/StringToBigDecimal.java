package org.pf.coop.portal.pes;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBigDecimal implements Converter<String, BigDecimal>{
	
	@Override
	public BigDecimal convert(String id) {
		try {
			
			BigDecimal obj = new BigDecimal(id);
			
			return obj;
			
		} catch (Exception e) {
			return new BigDecimal(0);
		}
	}
}
