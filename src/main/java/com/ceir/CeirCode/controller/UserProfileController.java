package com.ceir.CeirCode.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.filtermodel.SearchAssignee;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.ChangePassword;
import com.ceir.CeirCode.model.app.ChangeUserStatus;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.FilterRequest;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserHeader;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.UserStatusRequest;
import com.ceir.CeirCode.model.app.Userrole;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repoService.UserRoleRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.response.tags.RegistrationTags;
import com.ceir.CeirCode.service.UserProfileService;
import com.ceir.CeirCode.service.UserService;
import com.ceir.CeirCode.util.HttpResponse;
import com.google.common.primitives.Longs;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/userProfile")
@CrossOrigin
public class UserProfileController {

	@Autowired                                  
	UserService userService;    

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;

	@Autowired 
	UserProfileService userProService;
	
	@Autowired
	UserRoleRepoService userRoleRepoService;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@ApiOperation(value = "user profile record data.", response = UserProfile.class)
	@PostMapping("/record") 
	public MappingJacksonValue viewRecord(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file,
			@RequestParam(value = "source", defaultValue = "menu") String source){
		MappingJacksonValue mapping = null;
		log.info("source is:  "+source + "request::::"+filterRequest);
		if( file == 0) {
			Page<UserProfile> userProfileResponse  = userProService.viewAllRecord(filterRequest, pageNo, pageSize,source);
			List<SystemConfigListDb> asTypeList=systemConfigRepo.getByTag("AS_TYPE");
			if(userProfileResponse!=null) {
			for(UserProfile profile:userProfileResponse.getContent()) {
				for(SystemConfigListDb asType:asTypeList) {
					Integer value=asType.getValue();
					if(profile.getType()==value) {
						profile.setAsTypeName(asType.getInterp());
					}
					List<Userrole> userRoles=userRoleRepoService.findByUserId(profile.getUser().getId());
					if(userRoles.isEmpty()==false)
					{
						List<Long> rolesId=new ArrayList<Long>();
						for(Userrole role:userRoles) {
							rolesId.add(role.getUsertypeData().getId());
						}     
						long[] arr = new long[rolesId.size()]; 
						arr = Longs.toArray(rolesId); 
						profile.setRoles(arr); 
					}
					
				}
			}
			}
			mapping = new MappingJacksonValue(userProfileResponse);
			
		}else {
			FileDetails fileDetails = userProService.getFilterUSerPRofileInFile(filterRequest,source);
			mapping = new MappingJacksonValue(fileDetails);
		}
		return mapping;
	}


	@ApiOperation(value = "user profile data by  id", response = HttpResponse.class)
	@PostMapping("/viewProfile/{id}/{userId}") 
	public ResponseEntity<?> viewProfile(@PathVariable("id") long id,@PathVariable("userId") long userId,
			@RequestParam(name="publicIp", required = false) String publicIp,
			@RequestParam(name="browser", required = false) String browser){
		return userService.profileDataById(id,userId,publicIp,browser); 
	} 

	

	@ApiOperation(value = "change user status", response = HttpResponse.class)
	@PostMapping("/changeUserStatus")
	public ResponseEntity<?> changeUserStatus(@RequestBody ChangeUserStatus userStatus){
		return userService.changeUserStatus(userStatus);   
	}
	
	@ApiOperation(value = "admin approve user", response = HttpResponse.class)
	@PostMapping("/adminApproval") 
	public HttpResponse adminApproval(@RequestBody UserStatusRequest userData){
		return userService.AdminApproval(userData);
	} 
	
	
	
	@ApiOperation(value = "change password", response = HttpResponse.class)
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword password){
		return userService.changePassword(password);   
	} 
	@ApiOperation(value = "change expriry password", response = HttpResponse.class)
	@PostMapping("/updateExpirePassword")
	public ResponseEntity<?> changeExpiryPassword(@RequestBody ChangePassword password){
		return userService.updateExpirePassword(password);   
	}

	@ApiOperation(value = "change user status", response = HttpResponse.class)
	@PostMapping("/updateUserStatus")
	public ResponseEntity<?> updateUserStatus(@RequestBody UserStatusRequest userStatus){
		return userService.updateUserStatus(userStatus);   
	}                                                                                                                                     

	@ApiOperation(value = "user profile data by user id", response = HttpResponse.class)
	@PostMapping("/editProfile/{userid}") 
	public ResponseEntity<?> editProfile(@PathVariable("userid") long userid,@RequestBody UserHeader userheader){
		return userService.editProfile(userid,userheader);  
	} 

	/*
	 * @ApiOperation(value = "user profile data", response = HttpResponse.class)
	 * 
	 * @PostMapping("/updateProfile") public ResponseEntity<?>
	 * updateProfile(@RequestBody UserProfile userProfile){ return
	 * userService.updateProfile(userProfile); }
	 * 
	 */
	
	@ApiOperation(value = "search assignee.", response = UserProfile.class)
	@PostMapping("/searchAssignee")
	public MappingJacksonValue searchAssignee(@RequestBody SearchAssignee assignee,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {
		Page<UserProfile> profileList=userProService.assigneeInfo(assignee,pageNo,pageSize);
		MappingJacksonValue mapping=new MappingJacksonValue(profileList);
		return mapping;
	}
	
	
	@ApiOperation(value = "user profile status ", response = HttpResponse.class)
	@PutMapping("/updateStatus")
	public HttpResponse updateUserStatus(@RequestBody User user){
		return userService.updateStatus(user);
	}

	

	@ApiOperation(value = "add or delete roles", response = HttpResponse.class)
	@PostMapping("/getAddDeleteRoles")
	public ResponseEntity<?> addOrDeleteRoles(@RequestBody AllRequest request){
		return userService.addDeleteroles(request);  
	} 
	
	
	@ApiOperation(value = "Delete User.", response = HttpResponse.class)
	@DeleteMapping("/delete")
	public MappingJacksonValue  delete(@RequestBody AllRequest request) {
		log.info("Request:::::::::::::::"+request);
		GenricResponse genricResponse=null;
		if(userService.delete(request.getDataId())) {
	
			//audit
			/*
			 * log.info("currently no entry in audit_trail");
			 * userService.saveUserTrail(request.getUserId(),request.getUsername(),
			 * request.getUserType(),request.getUserTypeId(),Features.Registration_Request,
			 * SubFeatures.DELETE,request.getFeatureId());
			 */
			genricResponse	= new GenricResponse(200, RegistrationTags.User_Delete.getTag(),RegistrationTags.User_Delete.getMessage(), null);
		}
		else {
			
			genricResponse	= new GenricResponse(200, RegistrationTags.User_Fail.getTag(),RegistrationTags.User_Fail.getMessage(), null);
		}
		return  new MappingJacksonValue(genricResponse);

	}
	
	
}
