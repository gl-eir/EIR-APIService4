//package com.ceir.CeirCode.service;
//import java.io.IOException;
//import java.io.Writer;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.ceir.CeirCode.SpecificationBuilder.TypeApprovedSpecificationBuilder;
//import com.ceir.CeirCode.configuration.FileStorageProperties;
//import com.ceir.CeirCode.configuration.PropertiesReaders;
//import com.ceir.CeirCode.exceptions.ResourceServicesException;
//import com.ceir.CeirCode.model.SearchCriteria;
//import com.ceir.CeirCode.model.TypeApproveFileModel;
//import com.ceir.CeirCode.model.TypeApproveFilter;
//import com.ceir.CeirCode.model.TypeApprovedDb;
//import com.ceir.CeirCode.Constants.Datatype;
//import com.ceir.CeirCode.Constants.SearchOperation;
//import com.ceir.CeirCode.model.constants.ApproveStatus;
//import com.ceir.CeirCode.repo.TypeApproveRepo;
//import com.ceir.CeirCode.util.HttpResponse;
//import com.opencsv.CSVWriter;
//import com.opencsv.bean.ColumnPositionMappingStrategy;
//import com.opencsv.bean.StatefulBeanToCsv;
//import com.opencsv.bean.StatefulBeanToCsvBuilder;
//import  com.ceir.CeirCode.model.FileDetails;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.SimpleDateFormat;
//@Service
//public class TypeApproveService {
//
//	private final Logger log =LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	
//	TypeApproveRepo typeApproveRepo;
//	
//	@Autowired
//	PropertiesReaders propertiesReader;
//
//	@Autowired
//	FileStorageProperties fileStorageProperties;
//	
//	
//	public HttpResponse saveTypeApprove(TypeApprovedDb typeApprove) {
//		log.info("inside type approved db controller");
//		typeApprove.setCreatedOn(new Date());
//		typeApprove.setModifiedOn(new Date());  
//		log.info("typeApprove data:  "+typeApprove);
//
//		try 
//		{   
//			TypeApprovedDb output=typeApproveRepo.save(typeApprove);
//			if(output!=null) {
//				HttpResponse response=new HttpResponse();
//				response.setStatusCode(200);
//				response.setResponse("Type Approved data has been sucessfully saved");
//				return response; 
//			}
//			else {
//				HttpResponse response=new HttpResponse();
//				response.setStatusCode(500);
//				response.setResponse("Type Approved data failed to saved");
//				return response;
//			} 
//		}
//		catch(Exception e) {
//			HttpResponse response=new HttpResponse();
//			response.setStatusCode(409);
//			response.setResponse("Oops something wrong happened");
//			return response;
//		}
//	}
//	public ResponseEntity<?> viewTypeApproveById(long id) {
//		log.info("inside type approve view data by Id");
//		try {
//			log.info("type approve id: "+id);
//			TypeApprovedDb output=typeApproveRepo.findById(id);
//			if(output==null) {
//				HttpResponse response=new HttpResponse();
//				response.setResponse("please enter correct Id");
//				response.setStatusCode(204);
//				return new ResponseEntity<>(response,HttpStatus.OK);
//			}
//			else {
//				return new ResponseEntity<>(output,HttpStatus.OK);
//			}
//		}
//		catch(Exception e) {
//			HttpResponse response=new HttpResponse();
//			response.setResponse("Oops something wrong happened");
//			response.setStatusCode(409);     
//			return new ResponseEntity<>(response,HttpStatus.OK);
//		}
//
//	
//	}
//	
//	
//	public HttpResponse deleteTypeApprove(long id) {
//		log.info("inside type approve db controller");
//		try 
//		{   
//            log.info("type approve id:  "+id); 
//			TypeApprovedDb typeApprovedDb=typeApproveRepo.findById(id);
//			if(typeApprovedDb!=null) {
//				typeApprovedDb.setStatus(0);
//				TypeApprovedDb output=typeApproveRepo.save(typeApprovedDb);
//				if(output!=null) {
//					HttpResponse response=new HttpResponse();
//					response.setStatusCode(200);
//					response.setResponse("Type approve data has been sucessfully delete");
//					return response;	
//				}
//				else {
//					HttpResponse response=new HttpResponse();
//					response.setStatusCode(500);
//					response.setResponse("Type approve data failed to delete");
//					return response;
//				}
//			}
//			else {
//				HttpResponse response=new HttpResponse();
//				response.setStatusCode(204);
//				response.setResponse("type approve id is wrong");
//				return response;
//			} 
//		}
//		catch(Exception e) {
//			HttpResponse response=new HttpResponse();
//			response.setStatusCode(409);
//			response.setResponse("Oops something wrong happened");
//			return response;
//		}
//	}
//	
//	public HttpResponse updateTypeApprove(TypeApprovedDb typeApprove) {
//		log.info("inside update type approved db controller");
//		typeApprove.setModifiedOn(new Date());  
//		log.info("typeApprove data:  "+typeApprove);
//
//		try 
//		{   
//			TypeApprovedDb typeApprovedDb=typeApproveRepo.findById(typeApprove.getId());
//			if(typeApprovedDb==null) {
//				HttpResponse response=new HttpResponse();
//				response.setResponse("please enter correct Id");
//				response.setStatusCode(204);
//				return response;
//			}
//			else {
//				typeApprove.setStatus(typeApprovedDb.getStatus());
//				typeApprove.setCreatedOn(typeApprovedDb.getCreatedOn());
//				typeApprove.setModifiedOn(new Date());
//			TypeApprovedDb output=typeApproveRepo.save(typeApprove);
//			if(output!=null) {
//				HttpResponse response=new HttpResponse();
//				response.setStatusCode(200);
//				response.setResponse("Type Approved data has been sucessfully update");
//				return response; 
//			}
//			else {
//				HttpResponse response=new HttpResponse();
//				response.setStatusCode(500);
//				response.setResponse("Type Approved data failed to update");
//				return response;
//			} 
//		}
//		}
//		catch(Exception e) {
//			HttpResponse response=new HttpResponse();
//			response.setStatusCode(409);
//			response.setResponse("Oops something wrong happened");
//			return response;
//		}
//	}
//	
//	public Page<TypeApprovedDb>  viewTypeApprovdeData(TypeApproveFilter filterRequest, Integer pageNo, Integer pageSize){
//		log.info("inside type approve db view  data controller");
//		try {
//             log.info("TypeApproveFilter data: "+filterRequest );
//             log.info("pageNo= "+pageNo +"and pageSize="+pageSize);
//			Pageable pageable = PageRequest.of(pageNo, pageSize);
//
//			TypeApprovedSpecificationBuilder uPSB = new TypeApprovedSpecificationBuilder(propertiesReader.dialect);	
//
//			if(Objects.nonNull(filterRequest.getStartDate()))
//				uPSB.with(new SearchCriteria("requestDate",filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
//
//			if(Objects.nonNull(filterRequest.getEndDate()))
//				uPSB.with(new SearchCriteria("requestDate",filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
//
//			if(Objects.nonNull(filterRequest.getTac()))
//				uPSB.with(new SearchCriteria("tac",filterRequest.getTac(), SearchOperation.EQUALITY, Datatype.STRING));
//			
//			if(Objects.nonNull(filterRequest.getStatus()))
//				uPSB.with(new SearchCriteria("approveStatus",filterRequest.getStatus(), SearchOperation.EQUALITY, Datatype.INT));
//            log.info("going to fetch TypeApprovedDb data");
//			return typeApproveRepo.findAll(uPSB.build(),pageable);
//
//		} catch (Exception e) {
//			log.info("Exception found ="+e.getMessage());
//			return null;
//		}
//	}
//	
////	public FileDetails getFilterGrievancesInFile(TypeApproveFilter filterData, Integer pageNo, Integer pageSize) {
////		String fileName = null;
////		Writer writer   = null; 
////		String[] columns = new String[]{"grievanceId","userId","userType","grievanceStatus","txnId","categoryId","fileName","createdOn","modifiedOn","remarks"};
////		TypeApproveFileModel gfm = null;
////		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
////		String filePath  = fileStorageProperties.getGrievanceDownloadDir();
////		StatefulBeanToCsvBuilder<TypeApproveFileModel> builder = null;
////		StatefulBeanToCsv<TypeApproveFileModel> csvWriter      = null;
////		List< TypeApproveFileModel> fileRecords       = null;
////		ColumnPositionMappingStrategy<Grievance> mapStrategy = null;
////		HeaderColumnNameTranslateMappingStrategy<GrievanceFileModel> mapStrategy = null;
////		try {
////			List<TypeApprovedDb> typeApprovedDb = this.viewTypeApprovdeData(filterData, pageNo, pageSize).getContent();
////			if( typeApprovedDb.size() > 0 ) {
////				if(Objects.nonNull(filterData.getUserId()) && (filterData.getUserId() != -1 && filterData.getUserId() != 0)) {
////					fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_"+typeApprovedDb.get(0).getUserForTypeApprove().getUsername()+"_Typr.csv";
////				}else {
////					fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_Grievances.csv";
////				}
////			}else {
////				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_Grievances.csv";
////			}
////			/*Map<String, String> columnMapping = new HashMap<String, String>();
////			columnMapping.put("grievanceId", "grievanceId");
////			columnMapping.put("userId", "userId");
////			columnMapping.put("userType", "userType");
////			columnMapping.put("grievanceStatus", "grievanceStatus");
////			columnMapping.put("txnId", "txnId");
////			columnMapping.put("categoryId", "categoryId");
////			columnMapping.put("fileName", "fileName");
////			columnMapping.put("createdOn", "createdOn");
////			columnMapping.put("modifiedOn", "modifiedOn");
////			columnMapping.put("remarks", "remarks");
////			mapStrategy = new ColumnPositionMappingStrategy<Grievance>();
////			mapStrategy = new HeaderColumnNameTranslateMappingStrategy<GrievanceFileModel>();
////			mapStrategy.setType( GrievanceFileModel.class );
////			mapStrategy.setColumnMapping(columnMapping);
////			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
////			builder = new StatefulBeanToCsvBuilder<>(writer);
////			csvWriter = builder.withMappingStrategy(mapStrategy)
////                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
////                    .build();
////			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
////			builder = new StatefulBeanToCsvBuilder<TypeApproveFileModel>(writer);
////			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
////			if( typeApprovedDb.size() > 0 ) {
////				List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
////				fileRecords = new ArrayList<TypeApproveFileModel>(); 
////				for( TypeApprovedDb gr : typeApprovedDb ) {
////					gfm = new TypeApproveFileModel();
////					/*
////					 * gfm.setGrievanceId( gr.getGrievanceId() ); gfm.setGrievanceStatus(
////					 * GrievanceStatus.getActionNames( gr.getGrievanceStatus()).toString()); for(
////					 * SystemConfigListDb config : systemConfigListDbs ) { if(
////					 * config.getValue().equals( (Integer)gr.getCategoryId()) ) { gfm.setCategoryId(
////					 * config.getInterp()); } }
////					 */
////					gfm.setTxnId( gr.getTxnId());
////					gfm.setUsername( gr.getUserForTypeApprove().getUsername());
////					gfm.setCreatedOn(formatter.format(gr.getCreatedOn()));
////					gfm.setModifiedOn( formatter.format(gr.getModifiedOn()));
////					gfm.setFileName( gr.getFile());
////					gfm.setManufacturerName(gr.getManufacturerName());
////					gfm.setManufacturerName(gr.getManufacturerName());
////					gfm.setApproveOrRejectDate(formatter2.format(gr.getApproveDisapproveDate()));
////					gfm.setRequestDate(formatter2.format(gr.getRequestDate()));
////					gfm.setRemark( gr.getRemark());
////					gfm.setStatus(ApproveStatus.getUserStatusByCode(gr.getApproveStatus()).getDescription());
////					System.out.println(gfm.toString());
////					fileRecords.add(gfm);
////				}
////				csvWriter.write(fileRecords);
////			}
////			return new FileDetails( fileName, filePath, fileStorageProperties.getGrievanceDownloadLink()+fileName ); 
////		} catch (Exception e) {
////			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
////		}finally {
////			try {
////
////				if( writer != null )
////				writer.close();
////			} catch (IOException e) {}
////		}
////
////	}
//	
//}
//
//
//
