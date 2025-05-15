package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.DashboardUsersFeatureStateMap;

public interface DashboardUsersFeatureStateMapRepository extends JpaRepository< DashboardUsersFeatureStateMap, Long>{
	public List<DashboardUsersFeatureStateMap> findByUserTypeIdAndFeatureId( Integer userTypeId, Integer featureId );
}
