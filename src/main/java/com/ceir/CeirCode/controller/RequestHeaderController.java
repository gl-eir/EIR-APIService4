package com.ceir.CeirCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.filtermodel.AlertDbFilter;
import com.ceir.CeirCode.filtermodel.ReqHeaderFilter;
import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.service.ReqHeadersService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class RequestHeaderController {

	@Autowired
	ReqHeadersService reqHeadersService;

	@PostMapping("/userHeaders")
	public ResponseEntity<?> reqHeaders(@RequestBody RequestHeaders header){
		return reqHeadersService.saveRequestHeaders(header);
	}

	@ApiOperation(value = "request header db  data.", response = AlertDb.class)
	@PostMapping("/viewAll") 
	public MappingJacksonValue viewRequestHeaders(@RequestBody ReqHeaderFilter filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file,
			@RequestParam(name = "source", defaultValue = "menu", required = false) String source){
		MappingJacksonValue mapping = null;
		if( file == 0) {
			Page<RequestHeaders> alertDbReponse  = reqHeadersService.viewAllHeadersData(filterRequest, pageNo, pageSize,source);
			mapping = new MappingJacksonValue(alertDbReponse);
			
		}else {
			FileDetails fileDetails = reqHeadersService.getHeadersInFile(filterRequest,source);
			mapping = new MappingJacksonValue(fileDetails);
		}
		return mapping;
	}
	
}
