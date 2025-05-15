package com.ceir.CeirCode.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.SpecificationBuilder.GenericSpecificationBuilder;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.configuration.SortDirection;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filemodel.UserMgmtFileModel;
import com.ceir.CeirCode.filtermodel.UserMgmtFilter;
import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserDetails;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.Userrole;
import com.ceir.CeirCode.model.app.Usertype;
import com.ceir.CeirCode.model.constants.AlertStatus;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.model.constants.UsertypeData;
import com.ceir.CeirCode.othermodel.UserPort;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
import com.ceir.CeirCode.repo.app.UserProfileRepo;
import com.ceir.CeirCode.repo.app.UserRepo;
import com.ceir.CeirCode.repo.app.UsertypeRepo;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.RunningAlertRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.response.tags.PortAddsTags;
import com.ceir.CeirCode.response.tags.RegistrationTags;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class UserMgmtService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	UserRepoService userRepoService;

	@Autowired
	UsertypeRepo usertypeRepo;

	@Autowired
	UserProfileRepo userProfileRepo;

	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;

	@Autowired
	RunningAlertRepoService alertDbRepo;

	@Autowired
	ReqHeaderRepoService headerService;

	@Autowired
	UserService userService;
	
	@Autowired
	PooledPBEStringEncryptor pooledPBEStringEncryptor;

	private final Logger log = LoggerFactory.getLogger(getClass());

	private GenericSpecificationBuilder<User> buildSpecification(UserMgmtFilter filterRequest) {

		GenericSpecificationBuilder<User> uPSB = new GenericSpecificationBuilder<User>(propertiesReader.dialect);
		if (Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate() != "")
			uPSB.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));
		if (Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate() != "")
			uPSB.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));
		if (Objects.nonNull(filterRequest.getUsertypeId()) && filterRequest.getUsertypeId() != -1)
			uPSB.with(new SearchCriteria("usertype", filterRequest.getUsertypeId(), SearchOperation.EQUALITY,Datatype.INT));
		
		/*
		 * if(Objects.nonNull(filterRequest.getEmail()) && filterRequest.getEmail()!=
		 * "") { log.info("1::::::::"+filterRequest.getEmail());
		 * uPSB.joinWithUserProfile(new SearchCriteria("email",filterRequest.getEmail(),
		 * SearchOperation.EQUALITY, Datatype.STRING)); }
		 * if(Objects.nonNull(filterRequest.getPhoneNo()) &&
		 * filterRequest.getPhoneNo()!= "") {
		 * log.info("2::::::::"+filterRequest.getPhoneNo());
		 * uPSB.joinWithUserProfile(new
		 * SearchCriteria("phoneNo",filterRequest.getPhoneNo(),
		 * SearchOperation.EQUALITY, Datatype.STRING)); }
		 */
		
		  if(Objects.nonNull(filterRequest.getEmail()) && filterRequest.getEmail()!=""){ 
			 log.info("3::::::::"+filterRequest.getEmail()); 
			 uPSB.orSearch(new SearchCriteria("userProfile-email", filterRequest.getEmail(),SearchOperation.LIKE, Datatype.STRING)); 
		  }
		  if(Objects.nonNull(filterRequest.getPhoneNo()) && filterRequest.getPhoneNo()!= "") {
			  log.info("4::::::::"+filterRequest.getPhoneNo()); 
			  uPSB.orSearch(new SearchCriteria("userProfile-phoneNo", filterRequest.getPhoneNo(),SearchOperation.LIKE, Datatype.STRING)); 
		  }
		 
		
		
		if (Objects.nonNull(filterRequest.getFilteredUsername()) && filterRequest.getFilteredUsername() != "")
			uPSB.with(new SearchCriteria("username", filterRequest.getFilteredUsername(), SearchOperation.LIKE,
					Datatype.STRING));
		
		
		
		//uPSB.with(new SearchCriteria("currentStatus", 3, SearchOperation.EQUALITY, Datatype.INT));
		
		
		/*
		 * uPSB.addSpecification( uPSB.joinWithUserType(new
		 * SearchCriteria("selfRegister", 0, SearchOperation.EQUALITY, Datatype.INT)));
		 */
		
		uPSB.addSpecification(
				uPSB.joinWithUserType(new SearchCriteria("selfRegister", 0, SearchOperation.EQUALITY, Datatype.INT)));
		
		
		
		// uPSB.addSpecification(uPSB.joinWithUserProfile());

		if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
			log.info("filterRequest.getSearchString()::::::::" + filterRequest.getSearchString());
			uPSB.orSearch(new SearchCriteria("userProfile-email", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			uPSB.orSearch(new SearchCriteria("userProfile-phoneNo", filterRequest.getSearchString(),
					SearchOperation.LIKE, Datatype.STRING));
			uPSB.orSearch(new SearchCriteria("createdOn", filterRequest.getSearchString(), SearchOperation.EQUALITY,
					Datatype.DATE));
			uPSB.orSearch(new SearchCriteria("modifiedOn", filterRequest.getSearchString(), SearchOperation.EQUALITY,
					Datatype.DATE));

			uPSB.orSearch(new SearchCriteria("username", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
		}

		return uPSB;

	}

	public List<User> getAll(UserMgmtFilter filterRequest) {

		try {
			List<User> systemConfigListDbs = userRepo.findAll(buildSpecification(filterRequest).build());
			return systemConfigListDbs;

		} catch (Exception e) {
			log.info("Exception found =" + e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return null;
		}
	}

	public Page<User> viewAllRecord(UserMgmtFilter details, Integer pageNo, Integer pageSize, String operation) {
		try {
			log.info("filter data:  " + details);
			// RequestHeaders header=new
			// RequestHeaders(details.getUserAgent(),details.getPublicIp(),details.getUsername());
			// headerService.saveRequestHeader(header);
			String orderColumn;
			if(operation.equals("Export")) {
				orderColumn = "modifiedOn";
			}else {
			  log.info("column Name :: " + details.getOrderColumnName()); 
			  orderColumn = "Created On".equalsIgnoreCase(details.getOrderColumnName()) ? "createdOn" :
				  				   		"Modified On".equalsIgnoreCase(details.getOrderColumnName()) ? "modifiedOn" :
				  				   			"User ID".equalsIgnoreCase(details.getOrderColumnName()) ? "username" :
				  				   				"Email".equalsIgnoreCase(details.getOrderColumnName()) ? "userProfile.email" :
				  				   					"Contact Number".equalsIgnoreCase(details.getOrderColumnName()) ? "userProfile.phoneNo"
				  				   							: "User Type".equalsIgnoreCase(details.getOrderColumnName()) ?
				  				   									"usertype.usertypeName" : "modifiedOn"; 
			}
			/*
			 * Sort.Direction direction = getSortDirection(details.getOrder() == null ?
			 * "desc" : details.getOrder());
			 * 
			 * log.info("orderColumn Name is : "+orderColumn+
			 * "  -------------  direction is : "+direction);
			 * 
			 * Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction,
			 * orderColumn));
			 * 
			 * 
			 * Page<User> page = userRepo.findAll(buildSpecification(details).build(),
			 * pageable);
			 */
			
			log.info("orderColumn data:  "+orderColumn);
			log.info("---system.getSort() : "+details.getSort());
			Sort.Direction direction;
			if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				
				direction=Sort.Direction.DESC;
			
			}
			else {
			
				direction= SortDirection.getSortDirection(details.getSort());
				
			}
			if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(details.getSort()).equals(Sort.Direction.ASC)) {
				
				direction=Sort.Direction.ASC;
				
			}
			
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));
		
			log.info("column Name :: " + details.getOrderColumnName()+"---system.getSort() : "+details.getSort());
			
			Page<User> page = userRepo.findAll(buildSpecification(details).build(), pageable);
			
			//Page<AlertDb> page=alertDbRepo.findAll(buildSpecification(details).build(),pageable);
			
			String operationType = "view".equalsIgnoreCase(operation) ? SubFeatures.VIEW_ALL : SubFeatures.EXPORT;
			userService.saveUserTrail(details.getUserId(), details.getUsername(), details.getUserType(),
					details.getUserTypeId(), Features.User_Management, operationType, details.getFeatureId(),details.getPublicIp(),details.getBrowser());

			 return page;

		} catch (Exception e) {
			log.info("Exception found =" + e.getMessage());
			log.info(e.toString());
			return null;

		}
		
	}

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	@Transactional
	public GenricResponse saveUser(UserDetails details) {
		Usertype userType = new Usertype(details.getUsertypeId());
		String displayName = "";
		log.info("data: " + details);
		// RequestHeaders header=new
		// RequestHeaders(details.getUserAgent(),details.getPublicIp(),details.getUsername());
		// headerService.saveRequestHeader(header);

		if (Objects.nonNull(details.getMiddleName())) {
			displayName = details.getFirstName() + " " + details.getMiddleName() + " " + details.getLastName();

		} else {

			displayName = details.getFirstName() + " " + details.getLastName();

		}
		UserProfile profile = new UserProfile(details.getFirstName(), details.getMiddleName(), details.getLastName(),
				details.getEmail(), details.getPhoneNo(), displayName);
		List<Userrole> role = new ArrayList<Userrole>();
		User user = new User(details.getUserName(), details.getPassword(), 3, details.getRemarks(), profile, userType,
				role);
		user.setUserLanguage("en");
		profile.setUser(user);
		// Userrole userRole = new Userrole(user, userType);
		// role.add(userRole);
		User output = new User();
		boolean emailExist = userProfileRepo.existsByEmailAndUser_CurrentStatusNot(details.getEmail(), 21);
		if (emailExist) {
			log.info("if email already exist in the data");
			GenricResponse response = new GenricResponse(409, RegistrationTags.Email_Exist.getTag(),
					RegistrationTags.Email_Exist.getMessage(), "");
			log.info("response send to user: " + response);
			return response;
		}

		boolean phoneExist = userProfileRepo.existsByPhoneNoAndUser_CurrentStatusNot(details.getPhoneNo(), 21);
		if (phoneExist) {
			log.info("if phone number already exist in the data");
			GenricResponse response = new GenricResponse(409, RegistrationTags.Phone_Exist.getTag(),
					RegistrationTags.Phone_Exist.getMessage(), "");
			log.info("response send to user: " + response);
			return response;
		}
		if (Objects.isNull(details.getUsertypeId()) || details.getUsertypeId() == 0) {
			GenricResponse response = new GenricResponse(409, "Usertype Id value must not be null or 0", "");
			log.info("Usertype Id value must not be null or 0");
			log.info("response send to user: " + response);
			return response;

		}
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(UsertypeData.CEIRAdmin.getCode(), "CEIRAdmin_User_Limit");
		map.put(UsertypeData.SystemAdmin.getCode(), "SystemAdmin_User_Limit");
		map.put(UsertypeData.Anonymous.getCode(), "Anonymous_User_Limit");
		map.put(UsertypeData.Operation.getCode(), "Operation_User_Limit");
		map.put(UsertypeData.End_User.getCode(), "customer_user_limit");
		map.put(UsertypeData.Customer_Care.getCode(), "end_user_limit");
		map.put(UsertypeData.Reporting.getCode(), "reporting_user_limit");
		map.put(UsertypeData.NOC.getCode(), "noc_user_limit");
		int usertypeId = (int) details.getUsertypeId();
		log.info("then going to fetch data from system configuration db by tag " + map.get(usertypeId));
		SystemConfigurationDb systemConfigData = systemConfigurationDbRepoImpl.getDataByTag(map.get(usertypeId));
		long userLimit = 0;
		if (systemConfigData != null) {
			try {
				log.info("value got by tag= " + systemConfigData.getValue());
				userLimit = Long.parseLong(systemConfigData.getValue());
			} catch (Exception e) {

				log.info(e.toString());
			}

		}

		long count = userRepoService.countByUsertypeId(details.getUsertypeId());
		log.info("total users find by this usertype= " + count);
		log.info("now going to compare these two above values");
		Optional<Usertype> usertypeDetail = Optional.ofNullable(usertypeRepo.findById(details.getUsertypeId()));

		if (count >= userLimit) {
			log.info("if usertype count greater than total users limit then we don't able to create new user now");
			log.info("user creation limit is exceeded for " + usertypeDetail.get().getUsertypeName());
			RunningAlertDb alertDb = new RunningAlertDb("alert001",
					" user creation limit is exceeded for " + usertypeDetail.get().getUsertypeName() + " usertype",
					AlertStatus.Init.getCode());
			alertDbRepo.saveAlertDb(alertDb);
			GenricResponse response = new GenricResponse(409, RegistrationTags.user_exceed_limit.getTag(),
					RegistrationTags.user_exceed_limit.getMessage(), "");
			return response;
		}

		try {
			user.setPassword(pooledPBEStringEncryptor.encrypt(details.getPassword()));
			output = userRepo.save(user);
		} catch (Exception e) {
			log.info("Exception occurs");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(500, RegistrationTags.COMMAN_FAIL_MSG.getTag(),
					RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
			return response;

		}
		if (output != null) {
			log.info("going to save audit trail");
			userService.saveUserTrail(details.getUserId(), details.getUsername(), details.getViewUserType(),
					details.getUsertypeId(), Features.User_Management, SubFeatures.SAVE, details.getFeatureId(),details.getPublicIp(),details.getBrowser());

			GenricResponse response = new GenricResponse(200, RegistrationTags.User_Success_Save.getTag(),
					RegistrationTags.User_Success_Save.getMessage(), "");
			return response;
		} else {
			GenricResponse response = new GenricResponse(500, RegistrationTags.User_Fail_Save.getTag(),
					RegistrationTags.User_Fail_Save.getMessage(), "");
			return response;
		}

	}

	public GenricResponse updateUser(UserDetails details) {
		log.info("data: " + details);
		// RequestHeaders header=new
		// RequestHeaders(details.getUserAgent(),details.getPublicIp(),details.getUsername());
		// headerService.saveRequestHeader(header);
		userService.saveUserTrail(details.getUserId(), details.getUsername(), details.getViewUserType(),
				details.getUsertypeId(), Features.User_Management, SubFeatures.UPDATE, details.getFeatureId(),details.getPublicIp(),details.getBrowser());

		Usertype userType = new Usertype(details.getUsertypeId());
		List<Userrole> role = new ArrayList<Userrole>();
		User userData = new User();
		try {
			userData = userRepo.findById(details.getId());
			if (Objects.isNull(userData)) {
				log.info("user id is wrong");
				GenricResponse response = new GenricResponse(500, RegistrationTags.COMMAN_FAIL_MSG.getTag(),
						RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
				return response;
			}
		} catch (Exception e) {
			log.info("Exception occurs");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(500, "Oops something wrong happened", "");
			return response;
		}

		// boolean emailExist=userProfileRepo.existsByEmail(details.getEmail());
		// boolean phoneExist=userProfileRepo.existsByPhoneNo(details.getPhoneNo());

		UserProfile profile = userData.getUserProfile();
		String username = details.getUsername();
		log.info("userName of System Admin " + username);
		if (!details.getEmail().equalsIgnoreCase(profile.getEmail())) {
			boolean emailExist = userProfileRepo.existsByEmailAndUser_CurrentStatusNot(details.getEmail(), 21);
			if (emailExist) {
				log.info("if email already exist in the data");
				GenricResponse response = new GenricResponse(409, RegistrationTags.Email_Exist.getTag(),
						RegistrationTags.Email_Exist.getMessage(), "");
				log.info("response send to user: " + response);
				return response;
			}
		}

		if (!details.getPhoneNo().equalsIgnoreCase(profile.getPhoneNo())) {
			boolean phoneExist = userProfileRepo.existsByPhoneNoAndUser_CurrentStatusNot(details.getPhoneNo(), 21);
			if (phoneExist) {
				log.info("if phone number already exist in the data");
				GenricResponse response = new GenricResponse(409, RegistrationTags.Phone_Exist.getTag(),
						RegistrationTags.Phone_Exist.getMessage(), "");
				log.info("response send to user: " + response);
				return response;
			}
			if (Objects.isNull(details.getUsertypeId()) || details.getUsertypeId() == 0) {
				GenricResponse response = new GenricResponse(409, "Usertype Id value must not be null or 0", "");
				log.info("Usertype Id value must not be null or 0");
				log.info("response send to user: " + response);
				return response;
			}
		}
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(UsertypeData.CEIRAdmin.getCode(), "CEIRAdmin_User_Limit");
		map.put(UsertypeData.SystemAdmin.getCode(), "SystemAdmin_User_Limit");
		map.put(UsertypeData.Anonymous.getCode(), "Anonymous_User_Limit");
		map.put(UsertypeData.Operation.getCode(), "Operation_User_Limit");
		map.put(UsertypeData.End_User.getCode(), "customer_user_limit");
		map.put(UsertypeData.Customer_Care.getCode(), "end_user_limit");
		map.put(UsertypeData.Reporting.getCode(), "reporting_user_limit");
		map.put(UsertypeData.NOC.getCode(), "noc_user_limit");
		int usertypeId = (int) details.getUsertypeId();
		log.info("then going to fetch data from system configuration db by tag " + map.get(usertypeId));
		SystemConfigurationDb systemConfigData = systemConfigurationDbRepoImpl.getDataByTag(map.get(usertypeId));
		long userLimit = 0;
		if (systemConfigData != null) {
			try {
				log.info("value got by tag= " + systemConfigData.getValue());
				userLimit = Long.parseLong(systemConfigData.getValue());
			} catch (Exception e) {

				log.info(e.toString());
			}

		}

		long count = userRepoService.countByUsertypeId(details.getUsertypeId());
		log.info("total users find by this usertype= " + count);
		
		log.info("now going to compare these two above values");
		Optional<Usertype> usertypeDetail = Optional.ofNullable(usertypeRepo.findById(details.getUsertypeId()));
		User output = null;
		
		
		long rowId = details.getId();
		//long  usertypeID = details.getUsertypeId();
		
		if (count > userLimit) {
			log.info("if usertype count greater than total users limit then we don't able to create new user now");
			log.info("user creation limit is exceeded for " + usertypeDetail.get().getUsertypeName());
			RunningAlertDb alertDb = new RunningAlertDb("alert001",
					" user creation limit is exceeded for " + usertypeDetail.get().getUsertypeName() + " usertype",
					AlertStatus.Init.getCode());
			alertDbRepo.saveAlertDb(alertDb);
			GenricResponse response = new GenricResponse(409, RegistrationTags.user_exceed_limit.getTag(),
					RegistrationTags.user_exceed_limit.getMessage(), "");
			return response;
		}else if(count<=userLimit) {
			User user = userRepo.findById(rowId);
			log.info("user.getUserTypeId()--" +user.getUsertype().getId()+"      details.getUsertypeId()"+ details.getUsertypeId());
			if((count==userLimit) && (user.getUsertype().getId()!=details.getUsertypeId()) ) {
				log.info("if usertype count greater than total users limit then we don't able to create new user now");
				log.info("user creation limit is exceeded for " + usertypeDetail.get().getUsertypeName());
				RunningAlertDb alertDb = new RunningAlertDb("alert001",
						" user creation limit is exceeded for " + usertypeDetail.get().getUsertypeName() + " usertype",
						AlertStatus.Init.getCode());
				alertDbRepo.saveAlertDb(alertDb);
				GenricResponse response = new GenricResponse(409, RegistrationTags.user_exceed_limit.getTag(),
						RegistrationTags.user_exceed_limit.getMessage(), "");
				return response;
			}
			profile.setEmail(details.getEmail());
			profile.setPhoneNo(details.getPhoneNo());
			
			  for (Userrole rolesData : userData.getUserRole()) {
			  rolesData.setUsertypeData(userType); 
			  role.add(rolesData); }
			 
			userData.setRemark(details.getRemarks());
			userData.setUsertype(userType);
			userData.setUserRole(role);
			userData.setUserProfile(profile);
			userData.setApprovedBy(username);
			userData.setModifiedBy(username);
			
			if (details.getCurrentStatus()==1) {
				log.info("Enable the user : "+details.getCurrentStatus());
				userData.setCurrentStatus(3);
			}else if(details.getCurrentStatus()==0) {
				log.info("Disable The user : "+details.getCurrentStatus());
				userData.setCurrentStatus(5);
			}
			
			log.info("userName of System Admin going to save in Approved by field " + username);
			output = new User();
			
			try {
				log.info("updating userdata parameters : "+userData);
				output = userRepo.save(userData);
			} catch (Exception e) {
				log.info("Exception occure");
				log.info(e.toString());
				GenricResponse response = new GenricResponse(500, "User failed to update",
						PortAddsTags.PAdd_Save_Fail.getTag());
				return response;

			}
		}
		
		if (output != null) {
			GenricResponse response = new GenricResponse(200, RegistrationTags.User_Success_Update.getTag(),
					RegistrationTags.User_Success_Update.getMessage(), "");
			return response;
		} else {
			GenricResponse response = new GenricResponse(500, RegistrationTags.User_Fail_Update.getTag(),
					RegistrationTags.User_Fail_Update.getMessage(), "");
			return response;
		}
		
	}

	@Transactional
	public GenricResponse deleteById(AllRequest details) {
		try {
			log.info("data: " + details);
			User user = userRepo.findByUsername(details.getUsername());
			// RequestHeaders header=new
			// RequestHeaders(details.getUserAgent(),details.getPublicIp(),details.getUsername());
			// headerService.saveRequestHeader(header);
			userService.saveUserTrail(details.getUserId(), details.getUsername(), details.getUserType(),
					details.getUserTypeId(), Features.User_Management, SubFeatures.DELETE, details.getFeatureId(),details.getPublicIp(),details.getBrowser());

			//userRepo.setStatusForUser(21, user.getCurrentStatus(), details.getDataId());
			log.info("UserID to delete from users "+details.getDataId());
			userRepo.deleteById(details.getDataId());
			GenricResponse response = new GenricResponse(200, "User Successfuly delete", "");
			return response;
		} catch (Exception e) {
			log.info("Exception occure");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(500, "User fail to delete", "");
			return response;

		}

	}

	public GenricResponse viewById(AllRequest request) {
		log.info("inside view by address port controller");
		log.info("data: " + request);
		// RequestHeaders header=new
		// RequestHeaders(request.getUserAgent(),request.getPublicIp(),request.getUsername());
		// headerService.saveRequestHeader(header);
		userService.saveUserTrail(request.getUserId(), request.getUsername(), request.getUserType(),
				request.getUserTypeId(), Features.User_Management, "View Profile", request.getFeatureId(),request.getPublicIp(),request.getBrowser());
		User output = new User();
		try {
			output = userRepo.findById(request.getDataId());
		} catch (Exception e) {
			log.info("Exception occure");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(500, "user id is wrong", "");
			return response;
		}

		if (output != null) {
			Usertype usertype = usertypeRepo.findById(output.getUsertype().getId());
			UserDetails details = new UserDetails(output.getUserProfile().getFirstName(),
					output.getUserProfile().getMiddleName(), output.getUserProfile().getLastName(),
					output.getUserProfile().getPhoneNo(), output.getUserProfile().getEmail(),
					usertype.getUsertypeName(), output.getUsername(), request.getDataId(), output.getUsertype().getId(),
					output.getRemark(), output.getApprovedBy(),output.getCurrentStatus());
			log.info("Name of system Admin when viewing  " + output.getApprovedBy());
			GenricResponse response = new GenricResponse(200, "", "", details);

			return response;
		} else {
			GenricResponse response = new GenricResponse(500, PortAddsTags.PAdd_Data_By_Id_Fail.getMessage(),
					PortAddsTags.PAdd_Data_By_Id_Fail.getTag());
			return response;
		}

	}

	public GenricResponse DataByUserTypeId(long userTypeId) {
		log.info("inside view data by userTypeId: " + userTypeId);
		List<User> output = new ArrayList<User>();
		try {
			output = userRepo.findByUsertype_IdAndCurrentStatus(userTypeId, 3);
		} catch (Exception e) {
			log.info("Exception occurs");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(500, "Oops Somthing wrong happened", "");
			return response;
		}
		if (output != null && !output.isEmpty()) {
			GenricResponse response = new GenricResponse(200, "", "", output);
			return response;
		} else {
			GenricResponse response = new GenricResponse(409, "No data found for this userTypeId", "");
			return response;
		}

	}

	public GenricResponse portData(UserPort data) {
		log.info("data given: " + data);
		List<User> output = new ArrayList<User>();
		try {
			output = userRepo.findByUsertype_IdAndCurrentStatusAndUserProfile_ArrivalPortAndUserProfile_PortAddress(
					data.getUserTypeId(), 3, data.getArrivalPort(), data.getPortAddress());
		} catch (Exception e) {
			log.info("Exception occurs");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(500, "Oops Somthing wrong happened", "");
			return response;
		}
		if (output != null && !output.isEmpty()) {
			GenricResponse response = new GenricResponse(200, "", "", output);
			return response;
		} else {
			GenricResponse response = new GenricResponse(409, "data not found", "");
			return response;
		}

	}

	public FileDetails getFile(UserMgmtFilter filter) {
		// TODO Auto-generated method stub

		log.info("inside export user management service");
		log.info("filter data:  " + filter);
		String fileName = null;
		Writer writer = null;
		UserMgmtFileModel uPFm = null;
		SystemConfigurationDb dowlonadDir = systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink = systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		Integer pageNo = 0;
		Integer pageSize = Integer
				.valueOf(systemConfigurationDbRepoImpl.getDataByTag("file.max-file-record").getValue());
		String filePath = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<UserMgmtFileModel> builder = null;
		StatefulBeanToCsv<UserMgmtFileModel> csvWriter = null;
		List<UserMgmtFileModel> fileRecords = null;
		MappingStrategy<UserMgmtFileModel> mapStrategy = new CustomMappingStrategy<>();

		try {

			mapStrategy.setType(UserMgmtFileModel.class);
			filter.setSort("");
			List<User> list = viewAllRecord(filter, pageNo, pageSize, "Export").getContent();

			if (list.size() > 0) {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_UserManagement.csv";
			} else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_UserManagement.csv";
			}
			log.info(" file path plus file name: " + Paths.get(filePath + fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if (list.size() > 0) {
				// List<SystemConfigListDb> systemConfigListDbs =
				// configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<UserMgmtFileModel>();
				for (User user : list) {
					uPFm = new UserMgmtFileModel();
					uPFm.setCreatedOn(user.getCreatedOn().format(dtf));
					uPFm.setModifiedOn(user.getModifiedOn().format(dtf));
					uPFm.setUsername(user.getUsername());
					uPFm.setEmail(user.getUserProfile().getEmail());
					uPFm.setPhoneNo(user.getUserProfile().getPhoneNo());
					uPFm.setUsertypeName(user.getUsertype().getUsertypeName());

					fileRecords.add(uPFm);
				}
				csvWriter.write(fileRecords);
			}
			log.info("fileName::" + fileName);
			log.info("filePath::::" + filePath);
			log.info("link:::" + dowlonadLink.getValue());
			return new FileDetails(fileName, filePath,
					dowlonadLink.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		} finally {
			try {

				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

	}

}
