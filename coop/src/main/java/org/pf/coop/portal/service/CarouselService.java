package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Carousel;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.CarouselRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;

@Service
public class CarouselService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private CarouselRepo carouselRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Carousel> oe = this.carouselRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addCarousel(Carousel obj, String updateBy) throws IOException {
		
		obj.setFileName(StringUtils.cleanPath(obj.getFile().getOriginalFilename()));
		obj.setFileType(obj.getFile().getContentType());
		obj.setFileData(obj.getFile().getBytes());
		
		obj = carouselRepo.save(obj);
		
		audit = new Audit(updateBy, "Carousel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteCarousel(Long id, String updateBy) {

		Optional<Carousel> oe = this.carouselRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		Carousel obj = oe.get();
		
		audit = new Audit(updateBy, "Carousel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		carouselRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateCarousel(Carousel carousel, String updateBy) throws IOException {
		
		Optional<Carousel> oe = this.carouselRepo.findById(carousel.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		Carousel obj = oe.get();
		
		obj.setFileName(StringUtils.cleanPath(carousel.getFile().getOriginalFilename()));
		obj.setFileType(carousel.getFile().getContentType());
		obj.setFileData(carousel.getFile().getBytes());
		
		obj.setDescription(carousel.getDescription());
		obj.setTitle(carousel.getTitle());
		obj.setPubEndDate(carousel.getPubEndDate());
		
		obj = carouselRepo.save(obj);
		
		audit = new Audit(updateBy, "Carousel", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
