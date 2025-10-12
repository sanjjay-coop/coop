package org.pf.coop.portal.service;

import java.io.IOException;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.SiteLogo;
import org.pf.coop.portal.repository.SiteLogoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class SiteLogoService {
	
	@Autowired
	private SiteLogoRepo siteLogoRepo;	

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<SiteLogo> oe = this.siteLogoRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addSiteLogo(MultipartFile file, String updateBy) throws IOException {
		
		SiteLogo obj = new SiteLogo();
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		obj.setFileName(fileName);
		obj.setFileType(file.getContentType());
		obj.setFileData(file.getBytes());
		
		obj = siteLogoRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteSiteLogo(Long id, String updateBy) {

		Optional<SiteLogo> oe = this.siteLogoRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		SiteLogo obj = oe.get();
		
		siteLogoRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateSiteLogo(SiteLogo siteLogo, String updateBy) {
		
		Optional<SiteLogo> oe = this.siteLogoRepo.findById(siteLogo.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		SiteLogo obj = oe.get();

		
		obj = siteLogoRepo.save(obj);
		
		return new TransactionResult(obj, true);
	}
}
