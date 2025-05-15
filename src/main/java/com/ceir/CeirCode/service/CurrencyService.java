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
import com.ceir.CeirCode.filemodel.ExchangeRateFile;
import com.ceir.CeirCode.filtermodel.CurrencyFilter;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.Currency;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.app.CurrencyRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
import com.ceir.CeirCode.repoService.CurrencyRepoService;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.SystemConfigurationDbRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.response.tags.CurrencyTags;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.ceir.CeirCode.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class CurrencyService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	CurrencyRepo currencyrepo;

	@Autowired
	CurrencyRepoService currencyRepoService;

	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	Utility utility;

	@Autowired
	ReqHeaderRepoService headerService;

	@Autowired
	UserService userService;
	
	@Autowired
	SystemConfigurationDbRepoService systemConfigDbRepoService;
	
	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;
	
	@Autowired
	SystemConfigDbListRepository systemConfigRepo;
	
	@Autowired
	SystemConfigDbRepository systemConfigDbRepository;
	public ResponseEntity<?> saveCurrency(Currency currency){
		log.info("inside save Currency controller");
		log.info("currency data going to save:  "+currency);
//		RequestHeaders header=new RequestHeaders(currency.getUserAgent(),currency.getPublicIp(),currency.getUsername());
//		headerService.saveRequestHeader(header);

		if(Objects.isNull(currency.getYear()) ){
			GenricResponse response=new GenricResponse(1,CurrencyTags.Curr_Year_Null.getTag(),CurrencyTags.Curr_Year_Null.getMessage(),"");
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		if(Objects.isNull(currency.getMonth()) ){
			GenricResponse response=new GenricResponse(2,CurrencyTags.Curr_Month_Null.getTag(),CurrencyTags.Curr_Month_Null.getMessage(),"");
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		String monthValue=null;
		if(currency.getMonth()<10&&currency.getMonth()!=0) {
			log.info("if month value less than 10");
			monthValue="0"+currency.getMonth()+"";
		}
		else {
			log.info("if month value greater than 10");
			monthValue=""+currency.getMonth()+"";
		}
		log.info("now month value: "+monthValue);
		String monthDate=currency.getYear()+"-"+monthValue;
		currency.setMonthDate(monthDate);
		userService.saveUserTrail(currency.getUserId(),currency.getUsername(),
				currency.getUserType(),currency.getUserTypeId(),Features.Exchange_Rate_Management,SubFeatures.Add,currency.getFeatureId(),currency.getPublicIp(),currency.getBrowser());
		boolean monthExist=currencyRepoService.existsByMonthDateAndCurrency(currency.getMonthDate(),currency.getCurrency());
		log.info("exist by data= "+monthExist);
		if(monthExist==true) {
			GenricResponse response=new GenricResponse(3,CurrencyTags.Curr_Already_Exist.getTag(),CurrencyTags.Curr_Already_Exist.getMessage(),"");
			return  new ResponseEntity<>(response,HttpStatus.OK);			
		}
		else {
			Currency output=currencyRepoService.save(currency);			
			if(output!=null) {
				GenricResponse response=new GenricResponse(200,CurrencyTags.Curr_Save_Sucess.getTag(),CurrencyTags.Curr_Save_Sucess.getMessage(),"");
				return  new ResponseEntity<>(response,HttpStatus.OK);
			}
			else {
				GenricResponse response=new GenricResponse(500,CurrencyTags.Curr_Save_Fail.getTag(),CurrencyTags.Curr_Save_Fail.getMessage(),"");
				return  new ResponseEntity<>(response,HttpStatus.OK);
			}
		}
	}

	public ResponseEntity<?> viewById(AllRequest request){
		log.info("inside view by Id Currency controller");
		log.info("data given : "+request);
		Currency output=currencyRepoService.getById(request.getDataId());
//		RequestHeaders header=new RequestHeaders(request.getUserAgent(),request.getPublicIp(),request.getUsername());
//		headerService.saveRequestHeader(header);
		userService.saveUserTrail(request.getUserId(),request.getUsername(),
				request.getUserType(),request.getUserTypeId(),Features.Exchange_Rate_Management,SubFeatures.VIEW,request.getFeatureId(),request.getPublicIp(),request.getBrowser());
		if(output!=null) {
				if(output.getMonth()!=null) {
					String  monthInterp=utility.getMonth(output.getMonth());
					if(Objects.nonNull(monthInterp))
					{
						output.setMonthInterp(monthInterp);
					}
					} 
			log.info("Modified by Name of system Admin when viewing  "+output.getModifiedBy());
			GenricResponse response=new GenricResponse(200,"","",output);
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			GenricResponse response=new GenricResponse(500,CurrencyTags.Curr_Data_By_Id_Fail.getTag(),CurrencyTags.Curr_Data_By_Id_Fail.getMessage(),"");
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}

	}

	public ResponseEntity<?> updateCurrency(Currency currency){
		log.info("inside update Currency controller");
		log.info("currency data going to update:  "+currency);
//		RequestHeaders header=new RequestHeaders(currency.getUserAgent(),currency.getPublicIp(),currency.getUsername());
//		headerService.saveRequestHeader(header);
		userService.saveUserTrail(currency.getUserId(),currency.getUsername(),
		currency.getUserType(),currency.getUserTypeId(),Features.Exchange_Rate_Management,SubFeatures.UPDATE,currency.getFeatureId(),currency.getPublicIp(),currency.getBrowser());
		Currency data=currencyRepoService.getById(currency.getId());
		String username = currency.getUsername();
		log.info("Modifeid By User Name "+username);
		if(data!=null) {
			data.setDollar(currency.getDollar());
			data.setRiel(currency.getRiel());
			data.setModifiedBy(username);
		Currency output=currencyRepoService.save(data);
				if(output!=null) {
					GenricResponse response=new GenricResponse(200,CurrencyTags.Curr_Update_Sucess.getTag(),CurrencyTags.Curr_Update_Sucess.getMessage(),"");
					log.info("exit from  update  Currency controller");
					log.info("response send: "+response);
					return  new ResponseEntity<>(response,HttpStatus.OK);
				}
				else {
					log.info("exit from  update  Currency controller");
					GenricResponse response=new GenricResponse(500,CurrencyTags.Curr_Update_Fail.getTag(),CurrencyTags.Curr_Update_Fail.getMessage(),"");
					log.info("response send: "+response);
					return  new ResponseEntity<>(response,HttpStatus.OK);
				}
		}
		else {
			GenricResponse response=new GenricResponse(400,CurrencyTags.Curr_Wrong_Id.getTag(),CurrencyTags.Curr_Wrong_Id.getMessage(),"");
			log.info("response send: "+response);
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
	}

 
	public Page<Currency> currencyData(CurrencyFilter filter, Integer pageNo, Integer pageSize, String operation, String source){
		log.info("inside currency view  controller");
		log.info("currencyInfo : "+filter);
//		RequestHeaders header=new RequestHeaders(filter.getUserAgent(),filter.getPublicIp(),filter.getUsername());
//		headerService.saveRequestHeader(header);
		
		String orderColumn;
		if(operation.equals("Export")) {
			orderColumn = "modifiedOn";
		}else {
		  log.info("column Name :: " + filter.getOrderColumnName()); 
		  orderColumn = "Created On".equalsIgnoreCase(filter.getOrderColumnName()) ? "createdOn" :
			  				   		"Modified On".equalsIgnoreCase(filter.getOrderColumnName()) ? "modifiedOn" :
			  				   			"Month".equalsIgnoreCase(filter.getOrderColumnName()) ? "month" :
			  				   				"Year".equalsIgnoreCase(filter.getOrderColumnName()) ? "year" :
			  				   					"Currency".equalsIgnoreCase(filter.getOrderColumnName()) ? "currency"
			  				   							: "Cambodian Riel".equalsIgnoreCase(filter.getOrderColumnName()) ? "riel" :
			  				   									"US Dollar".equalsIgnoreCase(filter.getOrderColumnName()) ?
					  				   									"dollar" :"modifiedOn"; 
		}
		 Sort.Direction direction = getSortDirection(filter.getOrder() == null ? "desc" : filter.getOrder()); 
		  
		  log.info("orderColumn Name is : "+orderColumn+ "  -------------  direction is : "+direction);
		  
		  Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
		 
		  if(source.equalsIgnoreCase("menu")) {
			  userService.saveUserTrail(filter.getUserId(),filter.getUsername(),
						filter.getUserType(),filter.getUserTypeId(),Features.Exchange_Rate_Management,SubFeatures.VIEW_ALL,filter.getFeatureId(),filter.getPublicIp(),filter.getBrowser());	
          }else if(source.equalsIgnoreCase("filter")) {
        	  userService.saveUserTrail(filter.getUserId(),filter.getUsername(),
  					filter.getUserType(),filter.getUserTypeId(),Features.Exchange_Rate_Management,SubFeatures.FILTER,filter.getFeatureId(),filter.getPublicIp(),filter.getBrowser());
          }else if(source.equalsIgnoreCase("ViewExport")) {
          	log.info("for "+source+" no entries in Audit Trail");
          }
		  
		 
		  
		//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
		GenericSpecificationBuilder<Currency> specification=new GenericSpecificationBuilder<Currency>(propertiesReader.dialect) ;
		if(Objects.nonNull(filter.getStartDate()) && filter.getStartDate()!="")
			specification.with(new SearchCriteria("createdOn",filter.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));

		if(Objects.nonNull(filter.getEndDate()) && filter.getEndDate()!="")
			specification.with(new SearchCriteria("createdOn",filter.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));

		if(Objects.nonNull(filter.getCurrency()))
			specification.with(new SearchCriteria("currency",filter.getCurrency(), SearchOperation.EQUALITY, Datatype.INTEGER));

		if(Objects.nonNull(filter.getYear()))
			specification.with(new SearchCriteria("monthDate",filter.getYear(), SearchOperation.LIKE, Datatype.STRING));
		
		if(Objects.nonNull(filter.getMonth()))
			specification.with(new SearchCriteria("month",filter.getMonth(), SearchOperation.EQUALITY, Datatype.INTEGER));
		
		if(Objects.nonNull(filter.getRiel()))
			specification.with(new SearchCriteria("riel",String.valueOf(filter.getRiel()), SearchOperation.EQUALITY, Datatype.STRING));
		
		if(Objects.nonNull(filter.getDollar()))
			specification.with(new SearchCriteria("dollar",String.valueOf(filter.getDollar()), SearchOperation.EQUALITY, Datatype.STRING));

		if(Objects.nonNull(filter.getSearchString()) && !filter.getSearchString().isEmpty()){
			specification.orSearch(new SearchCriteria("dollar", filter.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			specification.orSearch(new SearchCriteria("riel", filter.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
		}
		return currencyrepo.findAll(specification.build(),pageable);
	}
	
	
	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}
	
	public FileDetails getFile(CurrencyFilter filter,String source) {
		log.info("inside export currency service");
		log.info("filter data:  "+filter);
		String fileName = null;
		Writer writer   = null;
		ExchangeRateFile uPFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigDbRepository.findByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<ExchangeRateFile> builder = null;
		StatefulBeanToCsv<ExchangeRateFile> csvWriter      = null;
		List<ExchangeRateFile> fileRecords       = null;
		MappingStrategy<ExchangeRateFile> mapStrategy = new CustomMappingStrategy<>();
		
		
		try {
			mapStrategy.setType(ExchangeRateFile.class);
			List<SystemConfigListDb> currencyList=systemConfigRepo.getByTag("CURRENCY");
			
			List<Currency> list = currencyData(filter, pageNo, pageSize, "Export",source).getContent();
			for(Currency currency:list) {
				for(SystemConfigListDb systemConfig:currencyList) {
					Integer value=systemConfig.getValue();
					if(currency.getCurrency()==value) {
						currency.setCurrencyInterp(systemConfig.getInterp());
					}
				}
				if(currency.getMonth()!=null) {
				String  monthInterp=utility.getMonth(currency.getMonth());
				if(Objects.nonNull(monthInterp))
				{
					currency.setMonthInterp(monthInterp);
				}
				
				} 
			}
			
			userService.saveUserTrail(filter.getUserId(),filter.getUsername(),
					filter.getUserType(),filter.getUserTypeId(),Features.Exchange_Rate_Management,SubFeatures.EXPORT,filter.getFeatureId(),filter.getPublicIp(),filter.getBrowser());
		

			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_ExchangeRate.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_ExchangeRate.csv";
			}
			log.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<ExchangeRateFile>(); 
				for( Currency currency : list ) {
					uPFm = new ExchangeRateFile();
					uPFm.setCreatedOn(currency.getCreatedOn().format(dtf));
					uPFm.setModifiedOn(currency.getModifiedOn().format(dtf));
					uPFm.setMonthInterp(currency.getMonthInterp());
					uPFm.setYear(currency.getYear());
					uPFm.setCurrencyInterp(currency.getCurrencyInterp());
					uPFm.setRiel(currency.getRiel());
					uPFm.setDollar(currency.getDollar());
					fileRecords.add(uPFm);
				}
				csvWriter.write(fileRecords);
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
