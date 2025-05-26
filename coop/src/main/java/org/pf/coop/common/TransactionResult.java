package org.pf.coop.common;

public class TransactionResult {
	
	private Object obj;
	
	private boolean status = false;
	
	private String message;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TransactionResult(Object obj, boolean status) {
		super();
		this.obj = obj;
		this.status = status;
	}

	public TransactionResult(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public TransactionResult(Object obj, boolean status, String message) {
		super();
		this.obj = obj;
		this.status = status;
		this.message = message;
	}
	
}
