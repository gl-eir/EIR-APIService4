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
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filemodel.SlaFile;
import com.ceir.CeirCode.filemodel.SlaFile;
import com.ceir.CeirCode.filtermodel.RunningAlertFilter;
import com.ceir.CeirCode.filtermodel.SlaFilter;
import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SlaReport;
import com.ceir.CeirCode.model.app.StakeholderFeature;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.app.SlaRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.SlaRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserRepoService;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.ceir.CeirCode.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
@Service
public class SlaService {

	
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
	SlaRepo slaRepo;
	
	@Autowired
	SlaRepoService slaRepoService;
	
	@Autowired
	FeatureService featureService;
	
	@Autowired
	ReqHeaderRepoService headerService;
	
	@Autowired
	SystemConfigDbRepository systemConfigDbRepository;
	
	private GenericSpecificationBuilder<SlaReport> buildSpecification(SlaFilter filterRequest){
		
		log.info("sla report filters function");;
		GenericSpecificationBuilder<SlaReport> uPSB = new GenericSpecificationBuilder<SlaReport>(propertiesReader.dialect);	
        log.info("sla object created");
		if(Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate()!="")
			uPSB.with(new SearchCriteria("createdOn",filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
        log.info("start date");
		if(Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate()!="")
			uPSB.with(new SearchCriteria("createdOn",filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
        log.info("end date");
        if(Objects.nonNull(filterRequest.getFilteredUsername()) && !filterRequest.getFilteredUsername().isEmpty()){
        	log.info("username --- " +filterRequest.getFilteredUsername());
    		uPSB.orSearch(new SearchCriteria("username", filterRequest.getFilteredUsername(), SearchOperation.LIKE, Datatype.STRING));
    		}
        if(Objects.nonNull(filterRequest.getTxnId()) && !filterRequest.getTxnId().isEmpty()) {
        	log.info("txnId --- " +filterRequest.getTxnId());
        	uPSB.with(new SearchCriteria("txnId", filterRequest.getTxnId(), SearchOperation.EQUALITY_CASE_INSENSITIVE, Datatype.STRING));
        }
        if(Objects.nonNull(filterRequest.getFeature()) && filterRequest.getFeature()!=-1)
			uPSB.with(new SearchCriteria("featureId",filterRequest.getFeature(), SearchOperation.EQUALITY, Datatype.INTEGER));
        log.info("feature");
		if(Objects.nonNull(filterRequest.getUsertype()) && filterRequest.getUsertype()!=-1)
			uPSB.with(new SearchCriteria("usertypeId",filterRequest.getUsertype(), SearchOperation.EQUALITY, Datatype.INTEGER));
        log.info("user typr data");
		
		if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
		log.info("search sTring not emppty");
		uPSB.orSearch(new SearchCriteria("username", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		uPSB.orSearch(new SearchCriteria("stateInterp", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		uPSB.orSearch(new SearchCriteria("txnId", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		}
       log.info("filter data added");
		return uPSB;
	}
	
	public List<SlaReport> getAll(SlaFilter filterRequest) {

		try {
			List<SlaReport> systemConfigListDbs = slaRepo.findAll( buildSpecification(filterRequest).build(), new Sort(Sort.Direction.DESC, "modifiedOn"));

			return systemConfigListDbs;

		} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return null;
		}

	}
	
	
	public Page<SlaReport>  viewAllSlaData(SlaFilter filterRequest, Integer pageNo, Integer pageSize){
		try { 
			log.info("filter data:  "+filterRequest);
//			RequestHeaders header=new RequestHeaders(filterRequest.getUserAgent(),filterRequest.getPublicIp(),filterRequest.getUsername());
//			headerService.saveRequestHeader(header);
			userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.SLA_Management,SubFeatures.VIEW_ALL,filterRequest.getFeatureId(),
					filterRequest.getPublicIp(),filterRequest.getBrowser());
            log.info("now going to fetch sla data");
            String orderColumn = "Created On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "createdOn"
					: "User ID".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "username"
						:"Transaction ID".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "txnId"
							: "User Type".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "usertypeId"
									: "Feature Name".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "featureId"
											: "Status".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "stateInterp"
													:"modifiedOn";
			
			Sort.Direction direction = getSortDirection(filterRequest.getOrder() == null ? "desc" : filterRequest.getOrder()); 
			  
			log.info("orderColumn Name is : "+orderColumn+ "  -------------  direction is : "+direction);
			  
			Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
            
			log.info("page data set");
            Page<SlaReport> page=slaRepo.findAll(buildSpecification(filterRequest).build(),pageable);
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

	public FileDetails getSlaInFile(SlaFilter filterRequest) {
		log.info("filter data:  "+filterRequest);
		String fileName = null;
		Writer writer   = null;
		SlaFile adFm  = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigDbRepository.findByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<SlaFile> builder = null;
		StatefulBeanToCsv<SlaFile> csvWriter      = null;
		List<SlaFile> fileRecords       = null;
		MappingStrategy<SlaFile> mapStrategy = new CustomMappingStrategy<>();
		
		
		try {
			mapStrategy.setType(SlaFile.class);
			List<SlaReport> list = viewAllSlaData(filterRequest, pageNo, pageSize).getContent();
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_slaReport.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_slaReport.csv";
			}
			log.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.SLA_Management,SubFeatures.EXPORT,filterRequest.getFeatureId(),
					filterRequest.getPublicIp(),filterRequest.getBrowser());
			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<SlaFile>();
				List<StakeholderFeature> featureData=featureService.featureAllData();
				for( SlaReport sla : list ) {
					adFm = new SlaFile();
					adFm.setCreatedOn(utility.converedtlocalTime(sla.getCreatedOn()));
					adFm.setModifiedOn(utility.converedtlocalTime(sla.getModifiedOn()));
					adFm.setState(sla.getStateInterp());
					adFm.setUsername(sla.getUsername());
					adFm.setTransactionId(sla.getTxnId());
					if(Objects.nonNull(featureData)) {
                    	for(StakeholderFeature feature:featureData) {
                    		if(feature.getId()==sla.getFeatureId())
                    			adFm.setFeature(feature.getName());	
                    	}
                     }
					adFm.setUsertype(sla.getUserSlaReport().getUsertype().getUsertypeName());
					System.out.println(adFm.toString());
					fileRecords.add(adFm);
				}
				csvWriter.write(fileRecords);
			}
			else {
				csvWriter.write(new SlaFile());
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
