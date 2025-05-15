package com.ceir.CeirCode.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.model.app.RuleEngineMapping;
import com.ceir.CeirCode.repo.app.RuleEngineMappingRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class RuleEngineMappingDropdown {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired RuleEngineMappingRepo ruleEngineMappingRepo;
	
	@ApiOperation(value = "getDistinctFeature")
	@GetMapping("/getDistinctFeature")
	public ResponseEntity<?> getDistinctFeatureList() {
		Optional<List<String>> list = Optional.ofNullable(ruleEngineMappingRepo.findDistinctFeature());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	  @ApiOperation(value = "getDistinctUserType")
	  
	  @GetMapping("/getDistinctUserType") public ResponseEntity<?>
	  getDistinctUserType() { Optional<List<String>> list =
	  Optional.ofNullable(ruleEngineMappingRepo.findDistinctUserType()); return new
	  ResponseEntity<>(list, HttpStatus.OK); }
	 
	
	

}
