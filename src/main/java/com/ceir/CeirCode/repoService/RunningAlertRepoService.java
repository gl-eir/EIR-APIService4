package com.ceir.CeirCode.repoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.repo.app.RunningAlertDbRepo;


@Service
public class RunningAlertRepoService {

	
	@Autowired
		RunningAlertDbRepo alertRepo;
	
	public RunningAlertDb saveAlertDb(RunningAlertDb runningAlertDb) {
	
		try {
			return alertRepo.save(runningAlertDb);
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
