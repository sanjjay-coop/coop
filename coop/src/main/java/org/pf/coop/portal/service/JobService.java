package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Job;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class JobService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private JobRepo jobRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Job> oe = this.jobRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addJob(Job obj, String updateBy) throws IOException{
		
		if (obj.getFile()!=null) {
			String fileName = StringUtils.cleanPath(obj.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(obj.getFile().getContentType());
			obj.setFileData(obj.getFile().getBytes());
		}
		
		obj.setAddDate(Calendar.getInstance().getTime());
		obj.setEnabled(false);
		
		obj = jobRepo.save(obj);
	
		audit = new Audit(updateBy, "Job", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteJob(Long id, String updateBy) {

		Optional<Job> oe = this.jobRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Job obj = oe.get();
		
		audit = new Audit(updateBy, "Job", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		jobRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateJob(Job job, String updateBy) throws IOException {
		
		Optional<Job> oe = this.jobRepo.findById(job.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Job obj = oe.get();
		
		obj.setAddress(job.getAddress());
		obj.setCity(job.getCity());
		obj.setContactDesignation(job.getContactDesignation());
		obj.setContactEmail(job.getContactEmail());
		obj.setContactName(job.getContactName());
		obj.setContactPhone(job.getContactPhone());
		obj.setCountry(job.getCountry());
		obj.setDescription(job.getDescription());
		obj.setFirmName(job.getFirmName());
		obj.setLastDate(job.getLastDate());
		obj.setMinimumQualification(job.getMinimumQualification());
		obj.setPin(job.getPin());
		obj.setPosition(job.getPosition());
		obj.setSalary(job.getSalary());
		obj.setUrl(job.getUrl());
		
		if (job.getFile()!=null) {
			String fileName = StringUtils.cleanPath(job.getFile().getOriginalFilename());
			obj.setFileName(fileName);
			obj.setFileType(job.getFile().getContentType());
			obj.setFileData(job.getFile().getBytes());
		}
		
		
		obj = jobRepo.save(obj);
		
		audit = new Audit(updateBy, "Job", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
	
	@Transactional
	public TransactionResult enableDisableJob(Long id, Boolean status, String updateBy) {

		Optional<Job> oe = this.jobRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Job obj = oe.get();
		
		obj.setEnabled(status);
		
		audit = new Audit(updateBy, "Job", status.toString(), obj.getId(), Calendar.getInstance().getTime(), "ENABLE/DISABLE");
		auditRepo.save(audit);
		
		jobRepo.save(obj);

		return new TransactionResult(true, "Record updated successfully");
	}
	
	@Transactional
	public void updateSearchString() {
		
		List<Job> listObj = this.jobRepo.findBySearchString(null);
		
		for(Job obj : listObj) {
			obj.setUpdateDefaults("system");
			this.jobRepo.save(obj);
		}
	}
}
