package org.pf.coop.portal.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.pf.coop.common.TransactionResult;
import org.pf.coop.portal.model.Audit;
import org.pf.coop.portal.model.Parameters;
import org.pf.coop.portal.repository.AuditRepo;
import org.pf.coop.portal.repository.ParametersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ParametersService {

	@Autowired
	private AuditRepo auditRepo;
	
	private Audit audit;
	
	@Autowired
	private ParametersRepo parametersRepo;

	@Transactional 
	public Object getById(Long id) {
		if (id == null) return null;
		
		Optional<Parameters> oe = this.parametersRepo.findById(id);
		
		if (oe.isEmpty()) return null;
		else return oe.get();
	}
	
	public Parameters getParameters() {
		List<Parameters> listParameters = parametersRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		
		if (listParameters.isEmpty()) {
			return null;
		} else {
			return listParameters.get(0);
		}
	}
	
	@Transactional
	public TransactionResult addParameters(Parameters obj, String updateBy) {
		
		obj.setAddDefaults(updateBy);
		
		obj = parametersRepo.save(obj);
		
		audit = new Audit(updateBy, "Parameters", obj.toString(), obj.getId(), Calendar.getInstance().getTime(), "ADD");
		auditRepo.save(audit);
	
		return new TransactionResult(obj, true);
	}
}

