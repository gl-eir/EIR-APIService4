package com.ceir.CeirCode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.filtermodel.PortAddressFilter;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.FilterRequest;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.SystemConfigurationDbRepoService;
import com.ceir.CeirCode.service.PortAddressService;
import com.ceir.CeirCode.util.HttpResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/portAddress")
public class PortAddressController {

	@Autowired
	PortAddressService portAddressService;
	
	@Autowired
	SystemConfigurationDbRepoService systemConfigDbRepoService;
	
	@ApiOperation(value = "get data By Port", response = HttpResponse.class)
	@PostMapping("/getByPort/{arrivalPort}")
	public ResponseEntity<?> findDataByPort(@PathVariable("arrivalPort") Integer port){
		return portAddressService.getDataByPort(port);
	}
	
	@ApiOperation(value="save port Address")
	@PostMapping("/save")
	public ResponseEntity<?> saveAddressPort(@RequestBody PortAddress portAddress){
		return portAddressService.saveAddressPort(portAddress);
	}
	
	@ApiOperation(value="update port Address")
	@PostMapping("/update")
	public ResponseEntity<?> updateAddressPort(@RequestBody PortAddress portAddress){
		return portAddressService.updateAddressPort(portAddress);
	}
	
	@ApiOperation(value="delete port Address")
	@PostMapping("/delete")
	public ResponseEntity<?> deleteAddressPort(@RequestBody AllRequest request){
		return portAddressService.deleteAddressPort(request);
	}
	
	@ApiOperation(value = "port address data.", response = PortAddress.class)
	@PostMapping("/view")
	public MappingJacksonValue view(@RequestBody PortAddressFilter filter,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file){
		MappingJacksonValue mapping = null;
		if (file == 0) {
			Page<PortAddress> portAddressData  = portAddressService.portAddressInfo(filter, pageNo, pageSize,"View");
			mapping = new MappingJacksonValue(portAddressData);
			
				List<SystemConfigListDb> portList=systemConfigDbRepoService.getDataByTag("CUSTOMS_PORT");
				for(PortAddress portAddress:portAddressData.getContent()) {
					for(SystemConfigListDb asType:portList) {
						Integer value=asType.getValue();
						if(portAddress.getPort()==value) {
							portAddress.setPortInterp(asType.getInterp());
						}
					}
				}
				mapping = new MappingJacksonValue(portAddressData);
		}else {
			FileDetails fileDetails = portAddressService.getFile(filter);
			mapping = new MappingJacksonValue(fileDetails);
		}	
		
		
			return mapping;		
	}
	
	@ApiOperation(value="view port Address data  by id, Audi trail entry here")
	@PostMapping("/viewDataById")
	public ResponseEntity<?> viewByPortId(@RequestBody AllRequest request){
		return portAddressService.viewPortById(request);
	}
	
	@ApiOperation(value="view port Address by id , without audi trail entry")
	@PostMapping("/viewById/{id}")
	public ResponseEntity<?> viewById(@PathVariable("id")long id){
		return portAddressService.viewById(id);
	}

	
	
	
}
