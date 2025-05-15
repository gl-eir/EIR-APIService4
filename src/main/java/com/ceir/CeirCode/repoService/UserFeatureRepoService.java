package com.ceir.CeirCode.repoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.PeriodValidate;
import com.ceir.CeirCode.model.app.UserToStakehoderfeatureMapping;
import com.ceir.CeirCode.repo.app.UserToStakehoderfeatureMappingRepo;
@Service
public class UserFeatureRepoService {

	@Autowired
	UserToStakehoderfeatureMappingRepo userfeatureRepo;
	
	public UserToStakehoderfeatureMapping getByUsertypeIdAndFeatureId(PeriodValidate periodValidate) 
	{
		try 
		{
			return userfeatureRepo.findByUserTypeFeature_IdAndStakeholderFeature_Id(periodValidate.getUsertypeId(),periodValidate.getFeatureId());
		}
		catch(Exception e) {
			return null;
		}
		
	}
}
