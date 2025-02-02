package org.pf.coop.configProperties;

import org.pf.coop.portal.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	@Autowired
	private ParametersService parametersService;
	
	String location = "";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = parametersService.getParameters().getDataDirectory();
	}
}
