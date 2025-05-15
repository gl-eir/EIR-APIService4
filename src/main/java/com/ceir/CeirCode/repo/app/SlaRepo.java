package com.ceir.CeirCode.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.app.SlaReport;

public interface SlaRepo extends JpaRepository<SlaReport,Long>,JpaSpecificationExecutor<SlaReport>{

	
	public SlaReport findById(long id);
}
