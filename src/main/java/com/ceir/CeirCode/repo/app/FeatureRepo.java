package com.ceir.CeirCode.repo.app; 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ceir.CeirCode.model.app.StakeholderFeature;

@Repository
public interface FeatureRepo extends JpaRepository<StakeholderFeature, Long>,JpaSpecificationExecutor<StakeholderFeature>{
	
	public List<StakeholderFeature> findAll();
	public StakeholderFeature findById(long id);
}
