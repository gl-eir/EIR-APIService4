package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.RuleEngineMapping;

public interface RuleEngineMappingRepo extends JpaRepository<RuleEngineMapping, Long> {
	@Query("SELECT DISTINCT r.feature FROM RuleEngineMapping r")
	public List<String> findDistinctFeature();

	@Query("SELECT DISTINCT r.userType FROM RuleEngineMapping r")
	public List<String> findDistinctUserType();
}
