package org.pf.coop.common;

public class YesNo {

	int value;
	String label;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public YesNo(int value, String label) {
		super();
		this.value = value;
		this.label = label;
	}
}
