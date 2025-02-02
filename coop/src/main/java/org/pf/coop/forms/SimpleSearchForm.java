package org.pf.coop.forms;

import java.io.Serializable;

public class SimpleSearchForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7273698749332560702L;
	
	private String searchString;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
