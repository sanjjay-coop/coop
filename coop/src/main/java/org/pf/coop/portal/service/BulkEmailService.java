package org.pf.coop.portal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.BulkEmail;
import org.pf.coop.portal.model.Member;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.BulkEmailRepo;
import org.pf.coop.portal.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BulkEmailService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private BulkEmailRepo bulkEmailRepo;
	
	@Autowired
	private MemberRepo memberRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<BulkEmail> oe = this.bulkEmailRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	@Transactional
	public TransactionResult addBulkEmail(Integer dayLimit, BulkEmail obj, String updateBy) {
		
		int pageNumber = 0;
		
		List<Member> listMember = new ArrayList<Member>();
		
		do {
			
			Pageable pageable = PageRequest.of(pageNumber++, dayLimit, Sort.by(Sort.Direction.ASC, "id"));
			
			Page<Member> page = this.memberRepo.findAll(pageable);
			
			listMember = page.getContent();
			
			
			if (!listMember.isEmpty()) {
				
				String bcc = "";
				
				for (Member member : listMember) {
					bcc = member.getEmail() + ", " + bcc;
				}
				
				bcc = bcc.trim();
				bcc = bcc.substring(0, bcc.length() - 1);
				
				BulkEmail bm = new BulkEmail();
				
				bm.setBcc(bcc);
				bm.setSubject(obj.getSubject());
				bm.setMessage(obj.getMessage());
				bm.setStatus("N");
				bm.setDateSent((Calendar.getInstance()).getTime());
				
				bulkEmailRepo.save(bm);
			}
			
		} while (!listMember.isEmpty());
	
		return new TransactionResult(obj, true);
	}

	@Transactional
	public TransactionResult deleteBulkEmail(Long id, String updateBy) {

		Optional<BulkEmail> oe = this.bulkEmailRepo.findById(id);

		if (oe.isEmpty())
			return new TransactionResult(false, "No such record found.");

		BulkEmail obj = oe.get();
		
		audit = new Audit(updateBy, "BulkEmail", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "DEL");
		auditRepo.save(audit);
		
		bulkEmailRepo.delete(obj);

		return new TransactionResult(true, "Record deleted successfully");
	}
	

	@Transactional
	public TransactionResult updateBulkEmail(BulkEmail bulkEmail, String updateBy) {
		
		Optional<BulkEmail> oe = this.bulkEmailRepo.findById(bulkEmail.getId());
		
		if (oe.isEmpty()) return new TransactionResult(false, "No such record found.");
		
		BulkEmail obj = oe.get();
		
		obj.setBcc(bulkEmail.getBcc());
		obj.setSubject(bulkEmail.getSubject());
		obj.setMessage(bulkEmail.getMessage());
		obj.setStatus(bulkEmail.getStatus());
		obj.setDateSent(bulkEmail.getDateSent());
		
		obj = bulkEmailRepo.save(obj);
		
		audit = new Audit(updateBy, "BulkEmail", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "UPD");
		auditRepo.save(audit);
		
		return new TransactionResult(obj, true);
	}
}
