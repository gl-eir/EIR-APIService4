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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.SpecificationBuilder.GenericSpecificationBuilder;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filemodel.AlertDbFile;
import com.ceir.CeirCode.filemodel.LocalityFile;
import com.ceir.CeirCode.filemodel.ReqHeaderFile;
import com.ceir.CeirCode.filemodel.ReqHeaderFile;
import com.ceir.CeirCode.filtermodel.AlertDbFilter;
import com.ceir.CeirCode.filtermodel.ReqHeaderFilter;
import com.ceir.CeirCode.filtermodel.SlaFilter;
import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SlaReport;
import com.ceir.CeirCode.model.app.StakeholderFeature;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.UserProfileFileModel;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.oam.ReqHeadersRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserRepoService;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.ceir.CeirCode.util.HttpResponse;
import com.ceir.CeirCode.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class ReqHeadersService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ReqHeaderRepoService headerService;
	
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
	ReqHeadersRepo reqHeaderRepo;
	
	@Autowired
	Utility utility;
	
	@Autowired
	SystemConfigDbRepository systemConfigDbRepository;
	
	public ResponseEntity<?> saveRequestHeaders(RequestHeaders requestHeaders){
      log.info("inside request headers controller");
	  log.info("Request headers data given: "+requestHeaders);
      RequestHeaders output=headerService.saveRequestHeader(requestHeaders);
      if(output!=null) {
             HttpResponse response=new HttpResponse("Request Headers data sucessfully save",200);
              return  new ResponseEntity<>(response,HttpStatus.OK);
      }
      else {
          HttpResponse response=new HttpResponse("Request Headers data fails to save",409);
          return  new ResponseEntity<>(response,HttpStatus.OK);
      }
	}
	
	
	private GenericSpecificationBuilder<RequestHeaders> buildSpecification(ReqHeaderFilter filterRequest){

		GenericSpecificationBuilder<RequestHeaders> uPSB = new GenericSpecificationBuilder<RequestHeaders>(propertiesReader.dialect);	

		if(Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate()!="")
			uPSB.with(new SearchCriteria("createdOn",filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));

		if(Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate()!="")
			uPSB.with(new SearchCriteria("createdOn",filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));


		if(Objects.nonNull(filterRequest.getFilteredUsername()) && !filterRequest.getFilteredUsername().isEmpty()){
		uPSB.orSearch(new SearchCriteria("username", filterRequest.getFilteredUsername(), SearchOperation.LIKE, Datatype.STRING));
		}
		
		if(filterRequest.getFilterPublicIp()==null) {
			log.info("FilterPublicIp Recieved in IF ===" +filterRequest.getFilterPublicIp());
		}
		else{
			log.info("FilterPublicIp Recieved in Else ===" +filterRequest.getFilterPublicIp());
			uPSB.with(new SearchCriteria("publicIp",filterRequest.getFilterPublicIp(), SearchOperation.LIKE, Datatype.STRING));
		}
		
		if(filterRequest.getFilterBrowser()==null) {
			log.info("FilterBrowser Recieved in IF ===" +filterRequest.getFilterBrowser());
		}
		else{
			log.info("FilterBrowser Recieved in Else ===" +filterRequest.getFilterBrowser());
			uPSB.with(new SearchCriteria("browser",filterRequest.getFilterBrowser(), SearchOperation.LIKE, Datatype.STRING));
		}
		
		
			
		if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
		uPSB.orSearch(new SearchCriteria("publicIp", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		uPSB.orSearch(new SearchCriteria("userAgent", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		uPSB.orSearch(new SearchCriteria("username", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		}

		return uPSB;
	}
	
	public List<RequestHeaders> getAll(ReqHeaderFilter filterRequest) {

		try {
			List<RequestHeaders> systemConfigListDbs = reqHeaderRepo.findAll( buildSpecification(filterRequest).build(), new Sort(Sort.Direction.DESC, "modifiedOn"));

			return systemConfigListDbs;

		} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return null;
		}

	}
	
	public Page<RequestHeaders>  viewAllHeadersData(ReqHeaderFilter filterRequest, Integer pageNo, Integer pageSize, String source){
		try { 
            log.info("filter data: "+filterRequest+" source : "+source);
//			RequestHeaders header=new RequestHeaders(filterRequest.getUserAgent(),filterRequest.getPublicIp(),filterRequest.getUsername());
//			headerService.saveRequestHeader(header);
		
			
			String orderColumn = "Created On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "createdOn"
					: "User ID".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "username"
						:"Public IP".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "publicIp"
							: "Browser".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "userAgent"
									:"modifiedOn";
			
			Sort.Direction direction = getSortDirection(filterRequest.getOrder() == null ? "desc" : filterRequest.getOrder()); 
			  
			log.info("orderColumn Name is : "+orderColumn+ "  -------------  direction is : "+direction);
			  
			Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
			
			Page<RequestHeaders> page = reqHeaderRepo.findAll( buildSpecification(filterRequest).build(), pageable );
			
			if(source.equalsIgnoreCase("menu")) {
			userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.IP_Log_Management,SubFeatures.VIEW_ALL,filterRequest.getFeatureId()
					,filterRequest.getPublicIp(),filterRequest.getBrowser());
			}else if (source.equalsIgnoreCase("filter")) {
			userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
						filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.IP_Log_Management,SubFeatures.FILTER,filterRequest.getFeatureId()
						, filterRequest.getPublicIp(),filterRequest.getBrowser());
			}else if (source.equalsIgnoreCase("ViewExport")) {
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
	
	public FileDetails getHeadersInFile(ReqHeaderFilter filterRequest,String source) {
		log.info("filter data:  "+filterRequest);
		String fileName = null;
		Writer writer   = null;
		ReqHeaderFile adFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigDbRepository.findByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<ReqHeaderFile> builder = null;
		StatefulBeanToCsv<ReqHeaderFile> csvWriter      = null;
		List<ReqHeaderFile> fileRecords       = null;
		MappingStrategy<ReqHeaderFile> mapStrategy = new CustomMappingStrategy<>();
		
		
		try {
			mapStrategy.setType(ReqHeaderFile.class);
			List<RequestHeaders> list = viewAllHeadersData(filterRequest, pageNo, pageSize,source).getContent();
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
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.IP_Log_Management,SubFeatures.EXPORT,filterRequest.getFeatureId(),
					filterRequest.getPublicIp(),filterRequest.getBrowser());
			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<ReqHeaderFile>(); 
				for( RequestHeaders req : list ) { 
					adFm = new ReqHeaderFile();
					adFm.setCreatedOn(utility.converedtlocalTime(req.getCreatedOn()));
					adFm.setUsername(req.getUsername());
					adFm.setPublicIp(req.getPublicIp());
					adFm.setBrowser(req.getBrowser());
					//adFm.setUserAgent(req.getUserAgent());
					
					
					fileRecords.add(adFm);
				}
				csvWriter.write(fileRecords);
			}else {
				csvWriter.write(new ReqHeaderFile());
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
