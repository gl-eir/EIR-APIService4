package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;

public interface SystemConfigDbRepository  extends JpaRepository<SystemConfigurationDb, Long> {

 
    
	public SystemConfigurationDb findByTag(String tag);
}
