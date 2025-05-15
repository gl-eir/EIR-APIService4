package com.ceir.CeirCode.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.SpecificationBuilder.GenericSpecificationBuilder;
import com.ceir.CeirCode.configuration.FileStorageProperties;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.configuration.SortDirection;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filtermodel.SearchAssignee;
import com.ceir.CeirCode.model.app.DashboardUsersFeatureStateMap;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.FilterRequest;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.StateMgmtDb;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.UserProfileFileModel;
import com.ceir.CeirCode.model.app.Userrole;
import com.ceir.CeirCode.model.constants.AssigneeType;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.model.constants.UserStatus;
import com.ceir.CeirCode.model.constants.UsertypeData;
import com.ceir.CeirCode.repo.app.DashboardUsersFeatureStateMapRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.UserProfileRepo;
import com.ceir.CeirCode.repo.app.UserRoleRepo;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserRepoService;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.ceir.CeirCode.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class UserProfileService {

	@Autowired
	FileStorageProperties fileStorageProperties;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired  
	UserProfileRepo  userProfileRepo;

	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;

	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;

	@Autowired
	UserService userService;

	@Autowired
	UserRepoService userRepoService;

	@Autowired
	StateMgmtServiceImpl stateMgmtServiceImpl;

	@Autowired
	UserRoleRepo userRoleRepo;
	
	@Autowired
	Utility utility;
	
	@Autowired
	DashboardUsersFeatureStateMapRepository dashboardUsersFeatureStateMapRepository; 


	private GenericSpecificationBuilder<UserProfile> buildSpecification(FilterRequest filterRequest,List<StateMgmtDb> statusList, String source){

		GenericSpecificationBuilder<UserProfile> uPSB = new GenericSpecificationBuilder<UserProfile>(propertiesReader.dialect);	
		User user=userRepoService.findByUSerId(filterRequest.getUserId());
		
		
		if(Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate()!="")
			uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("createdOn",filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE)));

		if(Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate()!="")
			uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("createdOn",filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE)));

		if(Objects.nonNull(filterRequest.getAsType()) && filterRequest.getAsType()!=-1)
			uPSB.with(new SearchCriteria("type",filterRequest.getAsType(), SearchOperation.EQUALITY, Datatype.INTEGER));

		if(Objects.nonNull(filterRequest.getEmail()) && !filterRequest.getEmail().isEmpty())
			uPSB.with(new SearchCriteria("email",filterRequest.getEmail(), SearchOperation.LIKE, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getPhoneNo()) && !filterRequest.getPhoneNo().isEmpty())
			uPSB.with(new SearchCriteria("phoneNo",filterRequest.getPhoneNo(), SearchOperation.LIKE, Datatype.STRING));
//		
//		uPSB.addSpecification((Specification<UserProfile>)(root, query, cb)-> {
//			return cb.equal(root.join("user").get("username"), filterRequest.getPhoneNo());
//			});
		
		
		if(Objects.nonNull(filterRequest.getUserRoleTypeId()) && filterRequest.getUserRoleTypeId() !=0 && filterRequest.getUserRoleTypeId()!=-1)
			uPSB.addSpecification(uPSB.joinWithMultiple(new SearchCriteria("id",filterRequest.getUserRoleTypeId(), SearchOperation.EQUALITY, Datatype.LONG)));
		
		
		if (Objects.nonNull(filterRequest.getFilteredUsername()) && filterRequest.getFilteredUsername() != "") {
			log.info("username in filterRequest::::::::"+filterRequest.getFilteredUsername());
			uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("user-username",filterRequest.getFilteredUsername(), SearchOperation.LIKE, Datatype.STRING)));
		}
		
		//if(Objects.nonNull(filterRequest.getUsername()) && !filterRequest.getUsername().isEmpty()) {
			
			//uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("user-username",filterRequest.getUsername(), SearchOperation.LIKE, Datatype.STRING)));
		//}
		else if(Objects.nonNull(filterRequest.getStatus()) && filterRequest.getStatus()!=-1) 
		{
			log.info("status in filterRequest::::::::"+filterRequest.getStatus());
			uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("currentStatus",filterRequest.getStatus(), SearchOperation.EQUALITY, Datatype.INTEGER)));
		}
		else
		{
			//uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("currentStatus",UserStatus.PENDING_ADMIN_APPROVAL.getCode(), SearchOperation.EQUALITY, Datatype.INT)));				
			if(Objects.nonNull(filterRequest.getFeatureId()) && Objects.nonNull(filterRequest.getUserTypeId())) {

				List<DashboardUsersFeatureStateMap> dashboardUsersFeatureStateMap = dashboardUsersFeatureStateMapRepository.findByUserTypeIdAndFeatureId(filterRequest.getUserTypeId(), filterRequest.getFeatureId());
				log.info("dashboard data size: "+dashboardUsersFeatureStateMap.size());

				List<Integer> stockStatus = new LinkedList<>();

				if(Objects.nonNull(dashboardUsersFeatureStateMap)) {
					if("dashboard".equalsIgnoreCase(source) || "menu".equalsIgnoreCase(source)) {
						log.info("if filter is dashboard or menu");
						for(DashboardUsersFeatureStateMap dashboardUsersFeatureStateMap2 : dashboardUsersFeatureStateMap ) {
							stockStatus.add(dashboardUsersFeatureStateMap2.getState());
						}
					}else if("filter".equalsIgnoreCase(source)) {
						log.info("if source is filter");
						if(nothingInFilter(filterRequest)) {
							log.info("if filters are empty");
							for(DashboardUsersFeatureStateMap dashboardUsersFeatureStateMap2 : dashboardUsersFeatureStateMap ) {
								stockStatus.add(dashboardUsersFeatureStateMap2.getState());
							}
						}else {
							log.info("if filters are not  empty");
							for(StateMgmtDb stateMgmtDb : statusList ) {
								stockStatus.add(stateMgmtDb.getState());
							}
						}
					}else if("noti".equalsIgnoreCase(source)) {
						
					uPSB.addSpecification(uPSB.joinWithUser(new SearchCriteria("username",filterRequest.getUsername(), SearchOperation.EQUALITY, Datatype.STRING)));
						
				//log.info("Skip status check, because source is noti."+user.getUsername());
					}

					log.info("Array list to add is = " + stockStatus);
					if(!stockStatus.isEmpty()) {
						//specificationBuilder.addSpecification(specificationBuilder.in("stockStatus", stockStatus));
						uPSB.addSpecification(uPSB.inQuery("currentStatus",stockStatus));

					}else {
						log.warn("no predefined status are available.");
					}
				}
			}
	
		}
		
		
		uPSB.addSpecification(uPSB.joinWithMultiple(new SearchCriteria("selfRegister",1, SearchOperation.EQUALITY, Datatype.INTEGER)));
		
		if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
		//uPSB.orSearchUser(new SearchCriteria("username", filterRequest.getSearchString(), SearchOperation.EQUALITY_CASE_INSENSITIVE, Datatype.STRING));
	   // uPSB.orSearchUsertype(new SearchCriteria("usertypeName", filterRequest.getSearchString(), SearchOperation.EQUALITY_CASE_INSENSITIVE, Datatype.STRING));
		uPSB.orSearch(new SearchCriteria("user-username", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		uPSB.orSearch(new SearchCriteria("user-usertype-usertypeName", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		
		  uPSB.orSearch(new SearchCriteria("createdOn",filterRequest.getSearchString(), SearchOperation.EQUALITY, Datatype.DATE));
		  uPSB.orSearch(new SearchCriteria("modifiedOn",filterRequest.getSearchString(), SearchOperation.EQUALITY, Datatype.DATE));
		//  uPSB.orSearch(new SearchCriteria("type", filterRequest.getSearchString(),SearchOperation.LIKE, Datatype.STRING)); 
		  uPSB.orSearch(new SearchCriteria("email",filterRequest.getSearchString(),SearchOperation.LIKE, Datatype.STRING));
		  uPSB.orSearch(new SearchCriteria("phoneNo",filterRequest.getSearchString(),SearchOperation.LIKE, Datatype.STRING));
		 
		}
		
		return uPSB;
	}
	
	public boolean nothingInFilter(FilterRequest filterRequest) {
		if(Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate()!="") {
			return Boolean.FALSE;
		}
		if(Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate()!="") {
			return Boolean.FALSE;
		}

		if(Objects.nonNull(filterRequest.getStatus()) && filterRequest.getStatus()!=-1) {
			return Boolean.FALSE;
		}

		if(Objects.nonNull(filterRequest.getAsType()) && filterRequest.getAsType()!=-1) {
			return Boolean.FALSE;
		}
		if(Objects.nonNull(filterRequest.getUserRoleTypeId()) && filterRequest.getUserRoleTypeId()!=-1) {
			return Boolean.FALSE;
		}

		if(Objects.nonNull(filterRequest.getEmail()) &&  !filterRequest.getEmail().isEmpty()) {
			return Boolean.FALSE;
		}

		if(Objects.nonNull(filterRequest.getPhoneNo()) && !filterRequest.getPhoneNo().isEmpty()) {
			return Boolean.FALSE;
		}

		if(Objects.nonNull(filterRequest.getUsername()) && !filterRequest.getUsername().isEmpty()) {
			return Boolean.FALSE;
		}
       return Boolean.TRUE;
	}
	
	public List<UserProfile> getAll(FilterRequest filterRequest,String source) {
		List<StateMgmtDb> statusList = null;
		try {
			
			statusList = stateMgmtServiceImpl.getByFeatureIdAndUserTypeId(filterRequest.getFeatureId(), filterRequest.getUserTypeId());

			List<UserProfile> systemConfigListDbs = userProfileRepo.findAll( buildSpecification(filterRequest,statusList,source).build(), new Sort(Sort.Direction.DESC, "modifiedOn"));
			userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.Registration_Request,SubFeatures.EXPORT,filterRequest.getFeatureId(),
					filterRequest.getPublicIp(),filterRequest.getBrowser());
			for(UserProfile userProfile : systemConfigListDbs) {
				   log.info("after fetching systemConfigListDbs data"+systemConfigListDbs);
		    for(StateMgmtDb stateMgmtDb : statusList) {
				if(userProfile.getUser().getCurrentStatus() == stateMgmtDb.getState()) {
					userProfile.getUser().setStateInterp(stateMgmtDb.getInterp());
					   log.info("stateMgmtDb.getInterp() data"+stateMgmtDb.getInterp());
					break;
				}
			}
			}
			 log.info("systemConfigListDbs:::::::::"+systemConfigListDbs);
			return systemConfigListDbs;

		} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return null;
		}

	}

	

	public Page<UserProfile>  viewAllRecord(FilterRequest filterRequest, Integer pageNo, Integer pageSize,String source){
		try { 
			log.info("filter data:  "+filterRequest);
			//	Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			String orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "user.createdOn"
					: "Modified On".equalsIgnoreCase(filterRequest.getColumnName()) ? "user.modifiedOn"
						:"User ID".equalsIgnoreCase(filterRequest.getColumnName()) ? "user.username"
							: "Email".equalsIgnoreCase(filterRequest.getColumnName()) ? "email"
									: "Phone No.".equalsIgnoreCase(filterRequest.getColumnName()) ? "phoneNo"
											: "Type".equalsIgnoreCase(filterRequest.getColumnName())
													? "type"
													: "User Type".equalsIgnoreCase(filterRequest.getColumnName())
															? "user.usertype.usertypeName"
															:"Status".equalsIgnoreCase(filterRequest.getColumnName())
															? "user.currentStatus" : "modifiedOn";
			Sort.Direction direction;
			/*if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {*/
				direction= SortDirection.getSortDirection(filterRequest.getSort());
				/* } */
			Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
			
			
			List<StateMgmtDb> statusList = stateMgmtServiceImpl.getByFeatureIdAndUserTypeId(filterRequest.getFeatureId(), filterRequest.getUserTypeId());
			Page<UserProfile> page = userProfileRepo.findAll( buildSpecification(filterRequest,statusList,source).build(), pageable );
			
			if ("filter".equals(source)) {
				userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
						filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.Registration_Request,SubFeatures.FILTER,filterRequest.getFeatureId(),filterRequest.getPublicIp(),filterRequest.getBrowser());
			}else {
				userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.Registration_Request,SubFeatures.VIEW_ALL,filterRequest.getFeatureId(),filterRequest.getPublicIp(),filterRequest.getBrowser());
			}
			for(UserProfile userProfile : page.getContent()) {
		        log.info("after fetching state mgmt data");

				for(StateMgmtDb stateMgmtDb : statusList) {
					if(userProfile.getUser().getCurrentStatus() == stateMgmtDb.getState()) {
						userProfile.getUser().setStateInterp(stateMgmtDb.getInterp());
						break;
					}
				}
				// setInterp(consignmentMgmt2);
			}
			return page;

		} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return null;

		}
	}

    public GenericSpecificationBuilder<UserProfile> assigneeSearchByFields(SearchAssignee searchAssignee,GenericSpecificationBuilder<UserProfile> specification)
    {
    	
    	if(Objects.nonNull(searchAssignee.getField())) 
		{
			if(searchAssignee.getType()!=null && searchAssignee.getType()!=0) {
				if(searchAssignee.getField()!=null && !searchAssignee.getField().equals(""))
				{	
					if(searchAssignee.getType()==AssigneeType.NAME.getCode()) {
						specification.with(new SearchCriteria("displayName", searchAssignee.getField(), SearchOperation.LIKE,Datatype.STRING));
					}
					else if(searchAssignee.getType()==AssigneeType.CONTACT.getCode()) {
						specification.with(new SearchCriteria("phoneNo", searchAssignee.getField(),SearchOperation.LIKE, Datatype.STRING));
					}
					else if(searchAssignee.getType()==AssigneeType.EMAIL.getCode()) {
						specification.with(new SearchCriteria("email", searchAssignee.getField(),SearchOperation.LIKE, Datatype.STRING));
					}
					else {

					}
				}
			}
		}
    	return specification;
    	

    }
    
	public Page<UserProfile> assigneeInfo(SearchAssignee searchAssignee, Integer pageNo, Integer pageSize){
		log.info("inside assignee controller");
		log.info("assignee information: "+searchAssignee);
		Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
		GenericSpecificationBuilder<UserProfile> specification=new GenericSpecificationBuilder<UserProfile>(propertiesReader.dialect) ;
	
		if(Objects.nonNull(searchAssignee.getUserTypeId())){

			if(searchAssignee.getUserTypeId()==UsertypeData.Distributor.getCode()) {
				specification.addSpecification(specification.joinWithUser(new SearchCriteria("currentStatus",UserStatus.APPROVED.getCode(), SearchOperation.EQUALITY, Datatype.INTEGER)));
				ArrayList<Integer> arrays=new ArrayList<Integer>();
				arrays.add(UsertypeData.Importer.getCode());
				arrays.add(UsertypeData.Distributor.getCode());
				arrays.add(UsertypeData.Manufacturer.getCode());
				specification.addSpecification(specification.inQuery("usertype",arrays));
				assigneeSearchByFields(searchAssignee,specification);
				return userProfileRepo.findAll(specification.build(),pageable);
			}
			else if(searchAssignee.getUserTypeId()==UsertypeData.Retailer.getCode()) {
				specification.addSpecification(specification.joinWithUser(new SearchCriteria("currentStatus",UserStatus.APPROVED.getCode(), SearchOperation.EQUALITY, Datatype.INTEGER)));
				ArrayList<Integer> arrays=new ArrayList<Integer>();
				arrays.add(UsertypeData.Importer.getCode());
				arrays.add(UsertypeData.Distributor.getCode());
				arrays.add(UsertypeData.Retailer.getCode());				
				arrays.add(UsertypeData.Manufacturer.getCode());
				specification.addSpecification(specification.inQuery("usertype",arrays));
				assigneeSearchByFields(searchAssignee,specification);
				return userProfileRepo.findAll(specification.build(),pageable);	
			}
            else if(searchAssignee.getUserTypeId()==UsertypeData.Custom.getCode()) {
				ArrayList<Integer> arrays=new ArrayList<Integer>();
				arrays.add(UsertypeData.Distributor.getCode());
				arrays.add(UsertypeData.Retailer.getCode());
				log.info("going to user data by distributor and retailer");
				List<Userrole> userData=userRoleRepo.findDistinctUserDataByUsertypeData_IdOrUsertypeData_Id(UsertypeData.Distributor.getCode(), UsertypeData.Retailer.getCode());
				ArrayList<Long> userIds=new ArrayList<Long>();
				for(Userrole userRoles:userData) {
					userIds.add(userRoles.getUserData().getId());
				}
				log.info("use Ids are : "+userIds);
				if(Objects.nonNull(userIds))
				{
					specification.addSpecification(specification.joinWithUser(new SearchCriteria("currentStatus",UserStatus.APPROVED.getCode(), SearchOperation.EQUALITY, Datatype.INTEGER)));
					//specification.addSpecification(specification.inQuery("usertype",arrays));
					//specification.in(new SearchCriteria("user",userIds,SearchOperation.EQUALITY, Datatype.ARRAYLIST),userIds);
					specification.addSpecification(specification.in("user",userIds));
					assigneeSearchByFields(searchAssignee,specification);
					return userProfileRepo.findAll(specification.build(),pageable);
					
				}
				else {
	            	return new PageImpl<UserProfile>(new ArrayList<>(), pageable, 0);   	 
				}
			}
			else if(searchAssignee.getUserTypeId()==UsertypeData.Importer.getCode()) {
			     if(searchAssignee.getRoleTypeId()==UsertypeData.Distributor.getCode())
			     {
						specification.addSpecification(specification.joinWithUser(new SearchCriteria("currentStatus",UserStatus.APPROVED.getCode(), SearchOperation.EQUALITY, Datatype.INTEGER)));
						ArrayList<Integer> arrays=new ArrayList<Integer>();
						arrays.add(UsertypeData.Importer.getCode());
						arrays.add(UsertypeData.Distributor.getCode());
						arrays.add(UsertypeData.Manufacturer.getCode());
						assigneeSearchByFields(searchAssignee,specification);
						specification.addSpecification(specification.inQuery("usertype",arrays));
						return userProfileRepo.findAll(specification.build(),pageable);
			    	 
			     }
			     else if(searchAssignee.getRoleTypeId()==UsertypeData.Retailer.getCode())
			     {
						specification.addSpecification(specification.joinWithUser(new SearchCriteria("currentStatus",UserStatus.APPROVED.getCode(), SearchOperation.EQUALITY, Datatype.INTEGER)));
						ArrayList<Integer> arrays=new ArrayList<Integer>();
						arrays.add(UsertypeData.Importer.getCode());
						arrays.add(UsertypeData.Distributor.getCode());
						arrays.add(UsertypeData.Retailer.getCode());				
						arrays.add(UsertypeData.Manufacturer.getCode());
						specification.addSpecification(specification.inQuery("usertype",arrays));
						assigneeSearchByFields(searchAssignee,specification);
						return userProfileRepo.findAll(specification.build(),pageable);	
			    	 
			     }
			     else {
		            	return new PageImpl<UserProfile>(new ArrayList<>(), pageable, 0);   	 
			     }
			}
            else {
            	return new PageImpl<UserProfile>(new ArrayList<>(), pageable, 0);
            }
		}
        else {
        	return new PageImpl<UserProfile>(new ArrayList<>(), pageable, 0);
        }
		
	}


	public FileDetails getFilterUSerPRofileInFile(FilterRequest profileFilter,String source) {
		log.info("inside export user profile data into file service");
		log.info("filter data:  "+profileFilter);
		String fileName = null;
		Writer writer   = null;
		UserProfileFileModel uPFm = null;
		SystemConfigurationDb userProfileDowlonadDir=systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb userProfileDowlonadLink=systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//User user=userRepoService.findByUSerId(profileFilter.getUserId());
		//if(user!=null) {
		//	userService.saveUserTrail(user, "Registration Request", "Export", profileFilter.getFeatureId(), profileFilter.getPublicIp(), profileFilter.getBrowser());
	//	}
		String filePath  = userProfileDowlonadDir.getValue();
		log.info("filePath:  "+filePath);
		StatefulBeanToCsvBuilder<UserProfileFileModel> builder = null;
		StatefulBeanToCsv<UserProfileFileModel> csvWriter      = null;
		List<UserProfileFileModel> fileRecords       = null;
		CustomMappingStrategy<UserProfileFileModel> mapStrategy = new CustomMappingStrategy<>();
		
		
		
		try {
			mapStrategy.setType(UserProfileFileModel.class);

		
			
			List<UserProfile> userProfileData = this.getAll(profileFilter,source);

			List<SystemConfigListDb> asTypeList=systemConfigRepo.getByTag("AS_TYPE");

			for(UserProfile profile:userProfileData) {
				for(SystemConfigListDb asType:asTypeList) {
					Integer value=asType.getValue();
					if(profile.getType()==value) {
						profile.setAsTypeName(asType.getInterp());
					}
				}
			}

			if( userProfileData.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_UserProfile.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_UserProfile.csv";
			}
			log.info(" file path plus filke name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( userProfileData.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<UserProfileFileModel>(); 
				for( UserProfile userProfile : userProfileData ) {
					uPFm = new UserProfileFileModel();
					uPFm.setRequestedOn(userProfile.getUser().getCreatedOn().format(dtf));
					uPFm.setModifiedOn(userProfile.getUser().getModifiedOn().format(dtf));	
					uPFm.setUserID(userProfile.getUser().getUsername());
					uPFm.setEmail(userProfile.getEmail());
					uPFm.setPhone(userProfile.getPhoneNo());
					//uPFm.setStatus(UserStatus.getUserStatusByCode(userProfile.getUser().getCurrentStatus()).getDescription());
					uPFm.setType(userProfile.getAsTypeName());
					uPFm.setUserType(userProfile.getUser().getUsertype().getUsertypeName());
					uPFm.setStatus(userProfile.getUser().getStateInterp());				
					uPFm.setApprovedBy(userProfile.getUser().getApprovedBy());
					fileRecords.add(uPFm);
				}
				csvWriter.write(fileRecords);
			}else {
				csvWriter.write(new UserProfileFileModel());
			}
			return new FileDetails( fileName, filePath,userProfileDowlonadLink.getValue().replace("$LOCAL_IP",propertiesReader.localIp)+fileName ); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}finally {
			try {

				if( writer != null )
					writer.close();
			} catch (IOException e) {}
		}

	}
}
