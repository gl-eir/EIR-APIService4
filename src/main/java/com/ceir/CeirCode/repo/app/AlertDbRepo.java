package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.audit.AuditDB;

public interface AlertDbRepo extends JpaRepository<AlertDb, Long>,JpaSpecificationExecutor<AlertDb>{

	
	public AlertDb findById(long id);
	
	@Query("SELECT DISTINCT s.feature FROM AlertDb s")
	public List<String> findDistinctFeature();

	//public AuditDB save(AuditDB auditDB);
}
