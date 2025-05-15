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
import com.ceir.CeirCode.SpecificationBuilder.SpecificationBuilder;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.configuration.SortDirection;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filemodel.CustomPortFile;
import com.ceir.CeirCode.filemodel.CustomPortFile;
import com.ceir.CeirCode.filtermodel.PortAddressFilter;
import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.model.app.AddressObject;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.Locality;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.app.LocalityRepo;
import com.ceir.CeirCode.repo.app.PortAddressRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.SystemConfigDbRepository;
import com.ceir.CeirCode.repoService.PortAddressRepoService;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.SystemConfigurationDbRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.response.tags.PortAddsTags;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.ceir.CeirCode.util.HttpResponse;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


@Service
public class PortAddressService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	PortAddressRepoService portService;
	
	@Autowired
	PropertiesReaders propertiesReader;
	
	@Autowired
	PortAddressRepo portAddressRepo;
	
	@Autowired
	SystemConfigurationDbRepoService systemConfigDbRepoService;

	@Autowired
	ReqHeaderRepoService headerService;

	@Autowired
	UserService userService;
	
	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;
	
	@Autowired
	SystemConfigDbListRepository systemConfigRepo;
	@Autowired
	SystemConfigDbRepository systemConfigDbRepository;
	public ResponseEntity<?> getDataByPort(Integer port){
		log.info("inside getDataByPort controller");

		List<PortAddress> portAddressList=portService.getByPort(port);
		if(portAddressList!=null) {
			log.info("exit from getDataByPort controller");
			return  new ResponseEntity<>(portAddressList,HttpStatus.OK);

		}
		else {
			HttpResponse response=new HttpResponse("Address data failed to find by port",204);
			log.info("exit from getDataByPort controller");
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
	public ResponseEntity<?> saveAddressPort(PortAddress portAddress){
		log.info("inside save address port controller and data is portAddress");
		log.info("port data: "+portAddress);
//		RequestHeaders header=new RequestHeaders(portAddress.getUserAgent(),portAddress.getPublicIp(),portAddress.getUsername());
//		headerService.saveRequestHeader(header);
		userService.saveUserTrail(portAddress.getUserId(),portAddress.getUsername(),
				portAddress.getUserType(),portAddress.getUserTypeId(),Features.Port_Management,SubFeatures.Add,portAddress.getFeatureId(),portAddress.getPublicIp(),portAddress.getBrowser());
		PortAddress output=portService.save(portAddress);

		if(output!=null) {
			GenricResponse response=new GenricResponse(200,PortAddsTags.PAdd_Save_Sucess.getMessage(),PortAddsTags.PAdd_Save_Sucess.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			GenricResponse response=new GenricResponse(500,PortAddsTags.PAdd_Save_Fail.getMessage(),PortAddsTags.PAdd_Save_Fail.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}

	}
	
	public ResponseEntity<?> viewById(long id){
		log.info("inside view by address port controller");
		log.info("id given : "+id);
		PortAddress output=portService.getById(id);
		if(output!=null) {
			List<SystemConfigListDb> asTypeList=systemConfigDbRepoService.getDataByTag("CUSTOMS_PORT");	
					for(SystemConfigListDb asType:asTypeList) {
						Integer value=asType.getValue();
				if(output.getPort()==value) {
					output.setPortInterp(asType.getInterp());
				}
				
					}
					GenricResponse response=new GenricResponse(200,"","",output);		
		
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			GenricResponse response=new GenricResponse(500,PortAddsTags.PAdd_Data_By_Id_Fail.getMessage(),PortAddsTags.PAdd_Data_By_Id_Fail.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}

	}

	
	public ResponseEntity<?> viewPortById(AllRequest request){
		log.info("inside view by address port controller");
		log.info("data given : "+request);
//		RequestHeaders header=new RequestHeaders(request.getUserAgent(),request.getPublicIp(),request.getUsername());
//		headerService.saveRequestHeader(header);
		userService.saveUserTrail(request.getUserId(),request.getUsername(),
				request.getUserType(),request.getUserTypeId(),Features.Port_Management,SubFeatures.VIEW,request.getFeatureId(),request.getPublicIp(),request.getBrowser());
		PortAddress output=portService.getById(request.getDataId());
		if(output!=null) {
			List<SystemConfigListDb> asTypeList=systemConfigDbRepoService.getDataByTag("CUSTOMS_PORT");	
					for(SystemConfigListDb asType:asTypeList) {
						Integer value=asType.getValue();
				if(output.getPort()==value) {
					output.setPortInterp(asType.getInterp());
				}
				
					}
					log.info("User Name of system Admin when viewing  "+output.getApprovedBy());
					GenricResponse response=new GenricResponse(200,"","",output);		
		
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			GenricResponse response=new GenricResponse(500,PortAddsTags.PAdd_Data_By_Id_Fail.getMessage(),PortAddsTags.PAdd_Data_By_Id_Fail.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}

	}
	
	public ResponseEntity<?> updateAddressPort(PortAddress portAddress){
		log.info("inside update address port controller");
		log.info("address port data "+portAddress);
//		RequestHeaders header=new RequestHeaders(portAddress.getUserAgent(),portAddress.getPublicIp(),portAddress.getUsername());
//		headerService.saveRequestHeader(header);
		userService.saveUserTrail(portAddress.getUserId(),portAddress.getUsername(),
				portAddress.getUserType(),portAddress.getUserTypeId(),Features.Port_Management,SubFeatures.UPDATE,portAddress.getFeatureId(),portAddress.getPublicIp(),portAddress.getBrowser());
        
		PortAddress data=portService.getById(portAddress.getId());
		String username = portAddress.getUsername();
		log.info("Modifeid By Username "+username);
		if(data!=null) {
			portAddress.setCreatedOn(data.getCreatedOn());
			portAddress.setApprovedBy(username);
		PortAddress output=portService.save(portAddress);
		
		if(output!=null) {
			GenricResponse response=new GenricResponse(200,PortAddsTags.PAdd_Update_Sucess.getMessage(),PortAddsTags.PAdd_Save_Sucess.getTag());
			log.info("exit from  update address port controller");
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			GenricResponse response=new GenricResponse(500,PortAddsTags.PAdd_Update_Fail.getMessage(),PortAddsTags.PAdd_Save_Fail.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		}
		else {
			GenricResponse response=new GenricResponse(500,PortAddsTags.PAdd_Wrong_PORT_ID.getMessage(),PortAddsTags.PAdd_Wrong_PORT_ID.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);		
		}

	}
	
	public ResponseEntity<?> deleteAddressPort(AllRequest request){
		log.info("inside save address port controller");
//		RequestHeaders header=new RequestHeaders(request.getUserAgent(),request.getPublicIp(),request.getUsername());
//		headerService.saveRequestHeader(header);
		userService.saveUserTrail(request.getUserId(),request.getUsername(),
				request.getUserType(),request.getUserTypeId(),Features.Port_Management,SubFeatures.DELETE,request.getFeatureId(),request.getPublicIp(),request.getBrowser());

		int output=portService.deleteById(request.getDataId());
		if(output==1) {
			GenricResponse response=new GenricResponse(200,PortAddsTags.PAdd_Del_Sucess.getMessage(),PortAddsTags.PAdd_Save_Sucess.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			GenricResponse response=new GenricResponse(500,PortAddsTags.PAdd_Del_Fail.getMessage(),PortAddsTags.PAdd_Save_Fail.getTag());
			return  new ResponseEntity<>(response,HttpStatus.OK);
		}

	}
	
	
	public Page<PortAddress> portAddressInfo(PortAddressFilter filter, Integer pageNo, Integer pageSize, String export){
		log.info("inside portAddress view  controller");
		log.info("portAddressInfo : "+filter);
		
		String orderColumn;
		
		if(export.equals("Export")) {
			orderColumn = "modifiedOn";
			
		}else {
			orderColumn = "Created On".equalsIgnoreCase(filter.getOrderColumnName()) ? "createdOn"
				: "Modified On".equalsIgnoreCase(filter.getOrderColumnName()) ? "modifiedOn"
					: "Port Address".equalsIgnoreCase(filter.getOrderColumnName()) ? "address"
						:"Port Type".equalsIgnoreCase(filter.getOrderColumnName()) ? "port"
						: "modifiedOn";
		}
		Sort.Direction direction;

		direction= SortDirection.getSortDirection(filter.getOrder() == null ? "desc" : filter.getOrder());
		
		log.info("orderColumn is : "+orderColumn+ " & direction is : "+direction);
		Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
		
		
//		RequestHeaders header=new RequestHeaders(filter.getUserAgent(),filter.getPublicIp(),filter.getUsername());
//		headerService.saveRequestHeader(header);
		String operationType = "Export".equalsIgnoreCase(export) ? SubFeatures.EXPORT :SubFeatures.VIEW_ALL ;
		
		userService.saveUserTrail(filter.getUserId(),filter.getUsername(),
				filter.getUserType(),filter.getUserTypeId(),Features.Port_Management,operationType,filter.getFeatureId(),filter.getPublicIp(),filter.getBrowser());

		//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
		GenericSpecificationBuilder<PortAddress> specification=new GenericSpecificationBuilder<PortAddress>(propertiesReader.dialect) ;
		
		if(Objects.nonNull(filter.getPort()))
			specification.with(new SearchCriteria("port",filter.getPort(), SearchOperation.EQUALITY, Datatype.INTEGER));

		
		if(Objects.nonNull(filter.getStartDate()) && filter.getStartDate()!="")
			specification.with(new SearchCriteria("createdOn",filter.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));

		if(Objects.nonNull(filter.getEndDate()) && filter.getEndDate()!="")
			specification.with(new SearchCriteria("createdOn",filter.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
		if(Objects.nonNull(filter.getAddress()) && !filter.getAddress().isEmpty()){
			specification.orSearch(new SearchCriteria("address", filter.getAddress(), SearchOperation.LIKE, Datatype.STRING));
		}
		


		return portAddressRepo.findAll(specification.build(),pageable);
		//return userProfileRepo.findAll(specification.build());

	}
	
	
	public FileDetails getFile(PortAddressFilter filter) {
		log.info("inside export custom Port service");
		log.info("filter data:  "+filter);
		String fileName = null;
		Writer writer   = null;
		CustomPortFile uPFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigDbRepository.findByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<CustomPortFile> builder = null;
		StatefulBeanToCsv<CustomPortFile> csvWriter      = null;
		List<CustomPortFile> fileRecords       = null;
		CustomMappingStrategy<CustomPortFile> mapStrategy = new CustomMappingStrategy<>();
		
		
		try {
			
			mapStrategy.setType(CustomPortFile.class);
			List<SystemConfigListDb> portList=systemConfigDbRepoService.getDataByTag("CUSTOMS_PORT");
			List<PortAddress> list = portAddressInfo(filter, pageNo, pageSize,"Export").getContent();
			for(PortAddress portAddress:list) {
				for(SystemConfigListDb asType:portList) {
					Integer value=asType.getValue();
					if(portAddress.getPort()==value) {
						portAddress.setPortInterp(asType.getInterp());
					}
				}
			}

			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_CustomPort.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_CustomPort.csv";
			}
			log.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<CustomPortFile>(); 
				for( PortAddress portAddress : list ) {
					uPFm = new CustomPortFile();
					uPFm.setCreatedOn(portAddress.getCreatedOn().format(dtf));
					uPFm.setModifiedOn(portAddress.getModifiedOn().format(dtf));
					uPFm.setPortInterp(portAddress.getPortInterp());
					uPFm.setAddress(portAddress.getAddress());
					
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
