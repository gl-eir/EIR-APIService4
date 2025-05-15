package com.ceir.CeirCode.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import com.ceir.CeirCode.filemodel.AlertDbFile;
import com.ceir.CeirCode.filemodel.RunningAlertFile;
import com.ceir.CeirCode.filtermodel.AlertDbFilter;
import com.ceir.CeirCode.filtermodel.RunningAlertFilter;
import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.audit.AuditDB;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.app.RunningAlertDbRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
import com.ceir.CeirCode.repo.audit.AuditDBRepo;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserRepoService;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.ceir.CeirCode.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
@Service
public class RunningAlertDbService {


	private final Logger log = LoggerFactory.getLogger(getClass());


	@Autowired
	PropertiesReaders propertiesReader;
	@Autowired
	UserService userService;

	@Autowired
	UserRepoService userRepoService;

	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;

	@Autowired
	Utility utility;

	@Autowired
	RunningAlertDbRepo runningAertRepo;

	@Autowired
	ReqHeaderRepoService headerService;

	@Autowired
	SystemConfigDbRepository systemConfigDbRepository; 
	
	@Autowired
	AuditDBRepo auditDb;
	
	private GenericSpecificationBuilder<RunningAlertDb> buildSpecification(RunningAlertFilter filterRequest){

		GenericSpecificationBuilder<RunningAlertDb> rASB = new GenericSpecificationBuilder<RunningAlertDb>(propertiesReader.dialect);	

		if(Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate()!="")
			rASB.with(new SearchCriteria("createdOn",filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));

		if(Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate()!="")
			rASB.with(new SearchCriteria("createdOn",filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));

		if(Objects.nonNull(filterRequest.getAlertId()) && filterRequest.getAlertId()!="")
			rASB.with(new SearchCriteria("alertId",filterRequest.getAlertId(), SearchOperation.EQUALITY, Datatype.STRING));
		
		
		if(filterRequest.getDescription()==null) {
			log.info("Description Recieved in IF ===" +filterRequest.getDescription());
		}
		else{/*(Objects.nonNull(filterRequest.getFeature()))*/
			log.info("Description Recieved in Else ===" +filterRequest.getDescription());
			rASB.with(new SearchCriteria("description",filterRequest.getDescription(), SearchOperation.LIKE, Datatype.STRING));
		}
		
		if(Objects.nonNull(filterRequest.getStatus()) && filterRequest.getStatus()!=-1)
			rASB.with(new SearchCriteria("status",filterRequest.getStatus(), SearchOperation.EQUALITY, Datatype.INTEGER));

		if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
			rASB.orSearch(new SearchCriteria("description", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			rASB.orSearch(new SearchCriteria("alertId", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			rASB.orSearch(new SearchCriteria("username", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		}

		return rASB;
	}

	public List<RunningAlertDb> getAll(RunningAlertFilter filterRequest) {

		try {

			List<RunningAlertDb> runningAlertDb = runningAertRepo.findAll( buildSpecification(filterRequest).build());
			runningAlertDb.sort((a1,a2)->a2.getModifiedOn().compareTo(a1.getModifiedOn()));
			return runningAlertDb;

		} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return null;
		}

	}


	public Page<RunningAlertDb>  viewRunningAlertData(RunningAlertFilter filterRequest, Integer pageNo, Integer pageSize,String operation,String source){
		try { 
			log.info("filter data:  "+filterRequest+" source :  "+source);
//			RequestHeaders header=new RequestHeaders(filterRequest.getUserAgent(),filterRequest.getPublicIp(),filterRequest.getUsername());
//			headerService.saveRequestHeader(header);
			
			String orderColumn = "Created On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "createdOn"
					: "Alert ID".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "alertId"
						:"Description".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "description"
							: "Status".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "status"
									:"modifiedOn";
			
			//Sort.Direction direction = getSortDirection(filterRequest.getOrder() == null ? "desc" : filterRequest.getOrder()); 
			  
			//log.info("orderColumn Name is : "+orderColumn+ "  -------------  direction is : "+direction);
			  
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
			
			log.info("orderColumn data:  "+orderColumn);
			log.info("---system.getSort() : "+filterRequest.getSort());
			Sort.Direction direction;
			if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				
				direction=Sort.Direction.DESC;
			
			}
			else {
			
				direction= SortDirection.getSortDirection(filterRequest.getSort());
				
			}
			if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
				
				direction=Sort.Direction.ASC;
				
			}
			  Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
			  
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			Page<RunningAlertDb> page=runningAertRepo.findAll(buildSpecification(filterRequest).build(),pageable);	
			if(source.equalsIgnoreCase("menu")) {
				/*
				 * userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername
				 * (), filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.
				 * Running_Alert_Management,SubFeatures.VIEW_ALL,filterRequest.getFeatureId(),
				 * filterRequest.getPublicIp(),filterRequest.getBrowser());
				 */
				
				auditDb.save(new AuditDB(filterRequest.getUserId(), filterRequest.getUsername(),
						Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
						Long.valueOf(filterRequest.getFeatureId()), Features.Running_Alert_Management, SubFeatures.VIEW_ALL, "", "NA",
						"SystemAdmin",filterRequest.getPublicIp(),filterRequest.getBrowser()));
			}
			else if (source.equalsIgnoreCase("filter")) {
				/*
				 * userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername
				 * (), filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.
				 * Running_Alert_Management,SubFeatures.FILTER,filterRequest.getFeatureId(),
				 * filterRequest.getPublicIp(),filterRequest.getBrowser());
				 */
				
				auditDb.save(new AuditDB(filterRequest.getUserId(), filterRequest.getUsername(),
						Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
						Long.valueOf(filterRequest.getFeatureId()), Features.Running_Alert_Management, SubFeatures.FILTER, "", "NA",
						"SystemAdmin",filterRequest.getPublicIp(),filterRequest.getBrowser()));
			} else if (source.equalsIgnoreCase("ViewExport")){
				log.info("for "+source+" no entries in Audit Trail");
			}
			return page;

		} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
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
	
	
	
	public FileDetails getRunningAlertInFile(RunningAlertFilter runAlertFilter,String source) {
		log.info("filter data:  "+runAlertFilter);
		String fileName = null;
		Writer writer   = null;
		RunningAlertFile adFm  = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		//DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigDbRepository.findByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<RunningAlertFile> builder = null;
		StatefulBeanToCsv<RunningAlertFile> csvWriter      = null;
		List<RunningAlertFile> fileRecords       = null;
		MappingStrategy<RunningAlertFile> mapStrategy = new CustomMappingStrategy<>();
		
		
		try {
			mapStrategy.setType(RunningAlertFile.class);
			runAlertFilter.setSort("");
			List<RunningAlertDb> list = viewRunningAlertData(runAlertFilter, pageNo, pageSize,"Export",source).getContent();
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_RunningAlert.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_RunningAlert.csv";
			}
			log.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			/*
			 * userService.saveUserTrail(runAlertFilter.getUserId(),runAlertFilter.
			 * getUsername(),
			 * runAlertFilter.getUserType(),runAlertFilter.getUserTypeId(),Features.
			 * Running_Alert_Management,SubFeatures.EXPORT,runAlertFilter.getFeatureId(),
			 * runAlertFilter.getPublicIp(),runAlertFilter.getBrowser());
			 */
			
			auditDb.save(new AuditDB(runAlertFilter.getUserId(), runAlertFilter.getUsername(),
					Long.valueOf(runAlertFilter.getUserTypeId()), runAlertFilter.getUserType(),
					Long.valueOf(runAlertFilter.getFeatureId()), Features.Running_Alert_Management, SubFeatures.EXPORT, "", "NA",
					"SystemAdmin",runAlertFilter.getPublicIp(),runAlertFilter.getBrowser()));
			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<RunningAlertFile>();
				List<SystemConfigListDb> statusList=systemConfigRepo.getByTag("ALERT_STATE");
				for(RunningAlertDb runningAlert : list ) {
					adFm = new RunningAlertFile();
					adFm.setCreatedOn(utility.converedtlocalTime(runningAlert.getCreatedOn()));
					adFm.setAlertId(runningAlert.getAlertId());
					if(runningAlert.getStatus()!=null) {
						for(SystemConfigListDb data:statusList) {
							Integer value=data.getValue();
							if(runningAlert.getStatus()==value) {
								adFm.setStatus(data.getInterp());
							}
						}
					}
					adFm.setDescription(runningAlert.getDescription());
					//System.out.println(adFm.toString());
					fileRecords.add(adFm);
				}
				csvWriter.write(fileRecords);
			}
			else {
				csvWriter.write(new RunningAlertFile());
			}
			log.info("fileName::"+fileName);
			log.info("filePath::::"+filePath);
			log.info("link:::"+dowlonadLink.getValue());
			return new FileDetails(fileName, filePath,dowlonadLink.getValue().replace("$LOCAL_IP",propertiesReader.localIp)+fileName ); 
		
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
