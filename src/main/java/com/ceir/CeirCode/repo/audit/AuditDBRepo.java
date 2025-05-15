package com.ceir.CeirCode.repo.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.audit.AuditDB;
public interface AuditDBRepo extends JpaRepository<AuditDB,Long>{
    public AuditDB getById(long id);
}
