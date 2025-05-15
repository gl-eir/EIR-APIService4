package com.ceir.CeirCode.repoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.SlaReport;
import com.ceir.CeirCode.repo.app.SlaRepo;
@Service
public class SlaRepoService {

	
	@Autowired
	SlaRepo slaRepo;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	
	
	public SlaReport getById(long id){
		try {
			return slaRepo.findById(id);
		}
		catch(Exception e) {
			log.info("error occurs when fetching data by id");
			log.info(e.toString());
			return null;
		}
	}
	

}
