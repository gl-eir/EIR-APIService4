package com.ceir.CeirCode.repo.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;


public interface SystemConfigDbListRepository extends JpaRepository<SystemConfigListDb, Long> {

 
	public ArrayList<SystemConfigListDb> getByTag(String tag);

	public SystemConfigListDb getById(Long id);
    
	

} 
