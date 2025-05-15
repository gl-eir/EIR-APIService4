package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.MessageConfigurationDb;

public interface MessageConfigurationDbRepository extends JpaRepository<MessageConfigurationDb, Long> {


	public MessageConfigurationDb getByTag(String tagValue);

	public MessageConfigurationDb getById(Long id);

	public MessageConfigurationDb getByTagAndActive(String tagValue, int active);
	
	@Query("SELECT DISTINCT m.featureName FROM MessageConfigurationDb m")
	public List<String> findDistinctFeatureName();
}

