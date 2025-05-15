package com.ceir.CeirCode.repoService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
@Service
public class SystemConfigurationDbRepoService {


	
	@Autowired
	SystemConfigDbListRepository systemConfigRepo;
	
	public ArrayList<SystemConfigListDb> getDataByTag(String tag) {
		
	try {
		ArrayList<SystemConfigListDb> systemConfigurationDb=systemConfigRepo.getByTag(tag);
		return systemConfigurationDb;
	}
		
	catch(Exception e) {
		return new ArrayList<SystemConfigListDb>();
	}
	}
}
