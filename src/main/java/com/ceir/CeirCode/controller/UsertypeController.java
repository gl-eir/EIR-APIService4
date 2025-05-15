package com.ceir.CeirCode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.filtermodel.UsertypeFilter;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.UserStatusRequest;
import com.ceir.CeirCode.model.app.Usertype;
import com.ceir.CeirCode.othermodel.ChangeUsertypeStatus;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.UserTypeService;
import com.ceir.CeirCode.util.HttpResponse;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
	public class UsertypeController {

	@Autowired
	UserTypeService usertypeService;

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;


	@ApiOperation(value = "user type  data.", response = Usertype.class)
	@PostMapping("/usertypeData") 
	public MappingJacksonValue viewRecord(@RequestBody UsertypeFilter filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file){
		MappingJacksonValue mapping = null;
		if(file == 0) {
			Page<Usertype> userTypeData  =usertypeService.viewAllUserytypes(filterRequest, pageNo, pageSize);
			List<SystemConfigListDb> statusList=systemConfigRepo.getByTag("UserType_Status");
			if(userTypeData!=null) {
				for(Usertype usertype:userTypeData.getContent()) {
					for(SystemConfigListDb status:statusList) {
						Integer value=status.getValue();
						if(usertype.getStatus()==value) {
							usertype.setStatusInterp(status.getInterp());
						}
					}
				}
			}
			mapping = new MappingJacksonValue(userTypeData);
		}else {
			FileDetails fileDetails = usertypeService.getFile(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}
		
		return mapping;
	}
	@ApiOperation(value = "change usertype status", response = HttpResponse.class)
	@PostMapping("/updateUserTypeStatus")
	public ResponseEntity<?> updateUserTypeStatus(@RequestBody ChangeUsertypeStatus userTypeStatus){
		return usertypeService.changeUserTypeStatus(userTypeStatus);  
	}                                                                                                                                     

	@ApiOperation(value = "check usertype status", response = GenricResponse.class)
	@CrossOrigin
	@PostMapping("/usertypeStatus/{usertypeId}")
	public ResponseEntity<?> checkStatus(@PathVariable("usertypeId")long usertypeId){
		return usertypeService.checkUsertypeStatus(usertypeId);
	} 
	
	@ApiOperation(value="view userType by id")
	@PostMapping("/usertype/viewById")
	public ResponseEntity<?> viewById(@RequestBody AllRequest request ){
		return usertypeService.viewById(request);
	}

}
