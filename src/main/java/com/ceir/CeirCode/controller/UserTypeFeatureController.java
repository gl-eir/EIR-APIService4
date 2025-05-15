package com.ceir.CeirCode.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.filtermodel.UserTypeFeatureFilter;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.UserToStakehoderfeatureMapping;
import com.ceir.CeirCode.model.app.Usertype;
import com.ceir.CeirCode.othermodel.ChangePeriod;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.UserToStakehoderfeatureMappingRepo;
import com.ceir.CeirCode.repo.app.UsertypeRepo;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.UserTypeFeatureService;
import com.ceir.CeirCode.util.HttpResponse;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserTypeFeatureController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	UserTypeFeatureService userTypeFeatureService;

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;

	@Autowired
	UserToStakehoderfeatureMappingRepo userToStakehoderfeatureMappingRepo;

	@Autowired
	UsertypeRepo usertypeRepo;

	@ApiOperation(value = "user type feature  data.", response = UserToStakehoderfeatureMapping.class)
	@PostMapping("/userTypeFeatureData")
	public MappingJacksonValue viewRecord(@RequestBody UserTypeFeatureFilter filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {
		MappingJacksonValue mapping = null;
		if (file == 0) {
			Page<UserToStakehoderfeatureMapping> userTypeFeatureData = userTypeFeatureService
					.viewAllUserTypeFeatures(filterRequest, pageNo, pageSize,"View");
			mapping = new MappingJacksonValue(userTypeFeatureData);
			if (userTypeFeatureData != null) {
				List<SystemConfigListDb> periodList = systemConfigRepo.getByTag("Period");
				for (UserToStakehoderfeatureMapping feature : userTypeFeatureData.getContent()) {
					feature.setUsertypeInterp(feature.getUserTypeFeature().getUsertypeName());
					feature.setFeatureInterp(feature.getStakeholderFeature().getName());
					for (SystemConfigListDb data : periodList) {
						Integer value = data.getValue();
						if (feature.getPeriod() == value) {
							feature.setPeriodInterp(data.getInterp());
						}
					}
				}
			}
			mapping = new MappingJacksonValue(userTypeFeatureData);
		} else {
			FileDetails fileDetails = userTypeFeatureService.getFile(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}

		return mapping;
	}

	@ApiOperation(value = "change usertype feature period", response = HttpResponse.class)
	@PostMapping("/updatePeriod")
	public ResponseEntity<?> updateUserTypeFeaturePeriod(@RequestBody ChangePeriod period) {
		return userTypeFeatureService.changePeriod(period);
	}

	@GetMapping("/userToFeatureDropdown/{featureId}/{usertypeId}")
	public List<UserToStakehoderfeatureMapping> userToFeatureDropdown(@PathVariable("featureId") long featureId,
			@PathVariable("usertypeId") long usertypeId) {
		List<UserToStakehoderfeatureMapping> response = new ArrayList<UserToStakehoderfeatureMapping>();
	
		List<UserToStakehoderfeatureMapping> list = userToStakehoderfeatureMappingRepo
				.findByStakeholderFeature_IdAndUserTypeFeature_IdNot(featureId, usertypeId);
		// log.info("userTypelist = "+userTypelist);

		for (UserToStakehoderfeatureMapping userToStakehoderfeatureMapping : list) {
			long id =  userToStakehoderfeatureMapping.getUserTypeFeature().getId();
			Usertype userTypelist = usertypeRepo.findById(id);
			if (userTypelist.getId() == id) {
				UserToStakehoderfeatureMapping obj = new UserToStakehoderfeatureMapping();
				obj.setUsertypeInterp(userTypelist.getUsertypeName());
				obj.setId(userTypelist.getId());
				obj.setCreatedOn(userTypelist.getCreatedOn());
				obj.setModifiedOn(userTypelist.getModifiedOn());
				response.add(obj);
			}
			
		}
		return response;

	}
	

	@PostMapping("/userTypeDropdownForGrievance")
	public List<UserToStakehoderfeatureMapping> userTypeDropdownForGrievance() {
		List<UserToStakehoderfeatureMapping> response = new ArrayList<UserToStakehoderfeatureMapping>();
	long featureId=6;
		List<UserToStakehoderfeatureMapping> list = userToStakehoderfeatureMappingRepo.findByStakeholderFeature_Id(featureId);
		// log.info("userTypelist = "+userTypelist);
		log.info("stakeholder feature by id=="+list); 
		for (UserToStakehoderfeatureMapping userToStakehoderfeatureMapping : list) {
			long id =  userToStakehoderfeatureMapping.getUserTypeFeature().getId();
			Usertype userTypelist = usertypeRepo.findById(id);
			if (userTypelist.getId() == id) {
				UserToStakehoderfeatureMapping obj = new UserToStakehoderfeatureMapping();
				obj.setUsertypeInterp(userTypelist.getUsertypeName());
				obj.setId(userTypelist.getId());
				obj.setCreatedOn(userTypelist.getCreatedOn());
				obj.setModifiedOn(userTypelist.getModifiedOn());
				obj.setUsertypeName(userTypelist.getUsertypeName());
				response.add(obj);
			}
			
			
		}
		log.info("final userType list=="+response); 
		return response;

	}
	
	@ApiOperation(value="view userType Period by id")
	@PostMapping("/viewPeriodById")
	public ResponseEntity<?> viewById(@RequestBody AllRequest request ){
		return userTypeFeatureService.viewById(request);
	}

}