package com.ceir.CeirCode.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.model.app.PeriodValidate;
import com.ceir.CeirCode.model.app.StakeholderFeature;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserLogin;
import com.ceir.CeirCode.model.app.UserToStakehoderfeatureMapping;
import com.ceir.CeirCode.model.app.Usertype;
import com.ceir.CeirCode.model.constants.Period;
import com.ceir.CeirCode.repo.app.FeatureRepo;
import com.ceir.CeirCode.repo.app.UserRepo;
import com.ceir.CeirCode.repo.app.UserRoleRepo;
import com.ceir.CeirCode.repo.app.UserToStakehoderfeatureMappingRepo;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserFeatureRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.util.HttpResponse;
import com.ceir.CeirCode.util.Utility;
@Service
public class FeatureService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	UserRepo userRepo;
	@Autowired
	UserRoleRepo userRoleRepo;
	@Autowired
	UserToStakehoderfeatureMappingRepo userFeatureRepo;

	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;

	@Autowired
	Utility utility;

	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	UserFeatureRepoService userFeatureRepoImpl;
	
	@Autowired
	FeatureRepo featureRepo;
	
	@Autowired
	ReqHeaderRepoService headerService;
	@Autowired
	UserService userService;
	
	
	public ResponseEntity<?> featureData(Integer userId){
		try {  
			
			log.info("user id is: "+userId);
			User userData=userRepo.findById(userId);
			/*
			 * UserLogin userLogin
			 * RequestHeaders header=new
			 * RequestHeaders(userLogin.getUserAgent(),userLogin.getPublicIp(),userData.
			 * getUsername(),userLogin.getBrowser());
			 * 
			 * headerService.saveRequestHeader(header); userService.saveUserTrail(userData,
			 * "User Management","Login",41);
			 * 
			 */
			List<StakeholderFeature> featureList=new ArrayList<StakeholderFeature>();
			Usertype usertypeData=userData.getUsertype();
			SystemConfigurationDb systemConfigData=systemConfigurationDbRepoImpl.getDataByTag("GRACE_PERIOD_END_DATE");
			Integer period=null;
			List<UserToStakehoderfeatureMapping> data=new ArrayList<UserToStakehoderfeatureMapping>();
			if(systemConfigData!=null) {
				period=currentPeriod(systemConfigData);
				data=userFeatureRepo.findByUserTypeFeature_IdAndPeriodOrPeriodAndUserTypeFeature_IdOrderByCreatedOnAsc(usertypeData.getId(),Period.Both.getCode(),period,usertypeData.getId());
			}
			else {
				data=userFeatureRepo.findByUserTypeFeature_IdOrderByCreatedOnAsc(usertypeData.getId());			
			}

			if(data!=null) {
				for(UserToStakehoderfeatureMapping userToFeatureData:data) {
					StakeholderFeature feature=new StakeholderFeature(); 
					feature.setId(userToFeatureData.getStakeholderFeature().getId()); 
					feature.setName(userToFeatureData.getStakeholderFeature().getName());
					feature.setLink(userToFeatureData.getStakeholderFeature().getLink());
					feature.setLogo(userToFeatureData.getStakeholderFeature().getLogo());
					feature.setCategory(userToFeatureData.getStakeholderFeature().getCategory());
					featureList.add(feature); 
				}   
			}
			if(!featureList.isEmpty()) {
				return new ResponseEntity<>(featureList,HttpStatus.OK);
			}
			else {
				HttpResponse response=new HttpResponse();
				response.setStatusCode(204);    
				response.setResponse("Feature Data not Found");
				return new ResponseEntity<>(response,HttpStatus.OK);	
			}
		}   
		catch(Exception e) {
			e.printStackTrace();
			log.info("exception found");
			log.info(e.toString());
			HttpResponse response=new HttpResponse();
			response.setStatusCode(409); 
			response.setResponse("Oops something wrong happened");
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}
	}

	public Integer currentPeriod(SystemConfigurationDb systemConfigData) {
		Date currentDate=utility.currentOnlyDate();
		log.info("currentDate: "+currentDate);
		Integer period;
		try {
			Date GracePeriodEndDate=utility.stringToDate(systemConfigData.getValue());
			log.info("GracePeriodEndDate: "+GracePeriodEndDate);
			if(currentDate.before(GracePeriodEndDate)) {
				period=Period.Grace.getCode();
			
			}
			else {
				period=Period.Post_Grace.getCode();
			}
			return period;
		}
		catch(Exception e) {
			log.info(e.toString());
			return null;
		}
	}
	public HttpResponse periodValidation(PeriodValidate periodValidate) {
		Integer currentPeriod = null;
		SystemConfigurationDb systemConfigData=systemConfigurationDbRepoImpl.getDataByTag("GRACE_PERIOD_END_DATE");
		if(systemConfigData!=null) {
			currentPeriod=currentPeriod(systemConfigData);			
			log.info("current period= "+currentPeriod);
		}
		
		UserToStakehoderfeatureMapping userFeature=userFeatureRepoImpl.getByUsertypeIdAndFeatureId(periodValidate);
		if(userFeature!=null) 
		{
			log.info("period in feature"+userFeature.getPeriod());
			if(Period.None.getCode()==userFeature.getPeriod())
			{
				return new HttpResponse("this functinality is not supported ",420);									
			}
			else if(Period.Both.getCode()==userFeature.getPeriod())
			{
				return new HttpResponse("this functinality is supported ",200);									
			}
			else 
			{
				if(currentPeriod==userFeature.getPeriod()) {
					return new HttpResponse("this functinality is supported ",200);									
				}
				else {
					return new HttpResponse("this functinality is not supported now",420);									
				}
			}
		}
		else 
		{
			return new HttpResponse("Data is not found against this usertype Id and feature Id",409);
		}

	}
	
	
	public ResponseEntity<?> featureData(){
		try {
			List<StakeholderFeature> featureData=featureRepo.findAll();
			featureData.sort((f1,f2)->f1.getName().compareTo(f2.getName()));
			return new ResponseEntity<>(featureData, HttpStatus.OK);
		}
		catch(Exception e){
			HttpResponse response=new HttpResponse();
			response.setResponse("Oop something wrong happened");
			response.setStatusCode(409);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	
	public List<StakeholderFeature> featureAllData(){
		try {
			List<StakeholderFeature> featureData=featureRepo.findAll();
			featureData.sort((f1,f2)->f1.getName().compareTo(f2.getName()));
			return featureData;
		}
		catch(Exception e){
			HttpResponse response=new HttpResponse();
			response.setResponse("Oop something wrong happened");
			response.setStatusCode(409);
			return new ArrayList<StakeholderFeature>();
		}
	}
	
	public GenricResponse featureNameById(long id){
		try {
			log.info("feature id: "+id);
			StakeholderFeature feature=featureRepo.findById(id);
			if(feature!=null) {
				GenricResponse response=new GenricResponse(200,"feature data is found","",feature);
				return response;
			}
			else {
				GenricResponse response=new GenricResponse(403,"feature Id is wrong","");		
				return response;
			}
		}
		catch(Exception e){
			log.info(e.getMessage());
			log.info(e.toString());
			GenricResponse response=new GenricResponse(409,"Oops! something wrong happened","");		
			return response;
		}
	}
	
	
	
}