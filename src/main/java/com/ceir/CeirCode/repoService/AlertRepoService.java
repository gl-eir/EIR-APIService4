package com.ceir.CeirCode.repoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.repo.app.AlertDbRepo;

@Service
public class AlertRepoService {

	@Autowired
	AlertDbRepo alertRepo;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	
	
	public AlertDb getById(long id){
		try {
			return alertRepo.findById(id);
		}
		catch(Exception e) {
			log.info("error occurs when fetching data by id");
			log.info(e.toString());
			return null;
		}
	}
	
	public AlertDb  save(AlertDb alertDb) {
		
		try {
			log.info("going to save   alertDb data");
			return alertRepo.save(alertDb);
			
		}
		catch(Exception e) {
			log.info("error occurs while adding  alertDb data");
			log.info(e.toString());
			return  null;
		}
	}
	
}
