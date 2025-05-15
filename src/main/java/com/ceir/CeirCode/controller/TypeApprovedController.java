//package com.ceir.CeirCode.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ceir.CeirCode.model.FileDetails;
//import com.ceir.CeirCode.model.FilterRequest;
//import com.ceir.CeirCode.model.TypeApproveFilter;
//import com.ceir.CeirCode.model.TypeApprovedDb;
//import com.ceir.CeirCode.model.UserProfile;
//import com.ceir.CeirCode.service.TypeApproveService;
//import com.ceir.CeirCode.util.HttpResponse;
// 
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/TypeApproved")
//public class TypeApprovedController {
//
//	@Autowired
//	TypeApproveService  typeApproveService;
//	
//	/*
//	 * @ApiOperation(value = "pagination View filtered grievance", response =
//	 * Grievance.class)
//	 * 
//	 * @PostMapping("v2/filter/grievance") public MappingJacksonValue
//	 * withPaginationGrievances(@RequestBody GrievanceFilterRequest filterRequest,
//	 * 
//	 * @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
//	 * 
//	 * @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
//	 * 
//	 * @RequestParam(value = "file", defaultValue = "0") Integer file) {
//	 * MappingJacksonValue mapping = null;
//	 * logger.info("Request to view filtered grievance = " +
//	 * filterRequest+", pageNo:["+pageNo+"], pageSize:["+pageSize+"] and file:["
//	 * +file+"]"); if( file == 0) { Page<Grievance> grievance =
//	 * grievanceServiceImpl.getFilterPaginationGrievances(filterRequest, pageNo,
//	 * pageSize); mapping = new MappingJacksonValue(grievance); }else { FileDetails
//	 * fileDetails = grievanceServiceImpl.getFilterGrievancesInFile(filterRequest,
//	 * pageNo, pageSize); mapping = new MappingJacksonValue(fileDetails); }
//	 * logger.info("Response of view filtered Grievances ="+mapping); return
//	 * mapping; }
//	 */
//	
//	@ApiOperation(value = "type approve data", response = TypeApprovedDb.class)
//	@PostMapping("/view") 
//	public MappingJacksonValue viewTypeApproveData(@RequestBody TypeApproveFilter filterRequest,
//			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
//			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
//			@RequestParam(value = "file", defaultValue = "0") Integer file){
//		MappingJacksonValue mapping=null;
//		if( file == 0) {
//		Page<TypeApprovedDb> typeApprovedData  = typeApproveService.viewTypeApprovdeData(filterRequest, pageNo, pageSize);
//		 mapping = new MappingJacksonValue(typeApprovedData);
//		}
//		
//		/*
//		 * else { FileDetails fileDetails =
//		 * typeApproveService.getFilterGrievancesInFile(filterRequest, pageNo,
//		 * pageSize); mapping = new MappingJacksonValue(fileDetails); }
//		 */
//		 
//		 return mapping;
//	}
//	
//	@PostMapping("/add")
//	@ApiOperation( value = "Add type approve data",response =HttpResponse.class)
//	public HttpResponse addTypeApproveData(@RequestBody TypeApprovedDb typeApprovedDb) {
//		return typeApproveService.saveTypeApprove(typeApprovedDb);
//	}
//
//	@ApiOperation( value = "View Approve data by Id",response =HttpResponse.class)
//	@PostMapping("viewById/{id}")
//	public ResponseEntity<?> addTypeApproveData(@PathVariable long id) {
//		return typeApproveService.viewTypeApproveById(id);
//	}
//	
//	@PostMapping("/delete")
//	@ApiOperation( value = "delete type approve data",response =HttpResponse.class)
//	public HttpResponse deleteTypeApproveData(long id) {
//		return typeApproveService.deleteTypeApprove(id);  
//	}
//	
//	@PostMapping("/update")
//	@ApiOperation( value = "update type approve data",response =HttpResponse.class)
//	public HttpResponse  updateTypeApproveData(@RequestBody TypeApprovedDb typeApprovedDb) {
//		return typeApproveService.updateTypeApprove(typeApprovedDb);
//	}
//	
//}
