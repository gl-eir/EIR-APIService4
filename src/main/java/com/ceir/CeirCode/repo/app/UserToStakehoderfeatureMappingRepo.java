package com.ceir.CeirCode.repo.app;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.app.UserToStakehoderfeatureMapping;

public interface UserToStakehoderfeatureMappingRepo extends JpaRepository<UserToStakehoderfeatureMapping, Long>,JpaSpecificationExecutor<UserToStakehoderfeatureMapping>{
	public List<UserToStakehoderfeatureMapping> findByUserTypeFeature_IdOrderByCreatedOnAsc(long id);
	public UserToStakehoderfeatureMapping findById(long id);
	public List<UserToStakehoderfeatureMapping> findByUserTypeFeature_IdAndPeriodOrPeriodAndUserTypeFeature_IdOrderByCreatedOnAsc(long id,Integer period1,Integer period2,long id2);
    public UserToStakehoderfeatureMapping findByUserTypeFeature_IdAndStakeholderFeature_Id(long usertypeId,long featureId);
    public List<UserToStakehoderfeatureMapping> findByStakeholderFeature_IdAndUserTypeFeature_IdNot(long featureId,long usertypeId);
    public List<UserToStakehoderfeatureMapping> findByStakeholderFeature_Id(long featureId);
	
}    
