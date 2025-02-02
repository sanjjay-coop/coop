package org.pf.coop.forms;

import java.io.Serializable;
import java.util.Date;

public class ReportForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5805768924817761398L;
	
	private Date startDate;
	private Date endDate;
	private String reportType;
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
}
