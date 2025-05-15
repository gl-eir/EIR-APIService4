package com.ceir.CeirCode.repoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.audit.AuditTrail;
import com.ceir.CeirCode.repo.audit.AuditTrailRepo;
@Service
public class AudiTrailRepoService {

	@Autowired
	AuditTrailRepo auditTrailRepo;

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public AuditTrail saveAuditTrail(AuditTrail auditTrail) {
		try 
		{
			return auditTrailRepo.save(auditTrail);
		}
		catch(Exception e)
		{
			log.info("error in saving data in audit trail");
			log.info(e.toString());
			return null;
		}
		
	}
}
