package com.ceir.CeirCode.repo.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.audit.AuditTrail;

public interface AuditTrailRepo extends JpaRepository<AuditTrail,Long>{

	
}
