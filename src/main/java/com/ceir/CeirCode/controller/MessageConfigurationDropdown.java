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

import com.ceir.CeirCode.repo.app.AlertDbRepo;
import com.ceir.CeirCode.repo.app.MessageConfigurationDbRepository;
import com.ceir.CeirCode.repo.app.SystemConfigurationDbRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class MessageConfigurationDropdown {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	MessageConfigurationDbRepository messageConfigurationDbRepository;
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;
	
	@Autowired
	AlertDbRepo alertDbRepo;

	@ApiOperation(value = "getDistinctFeatureName")
	@GetMapping("/getDistinctFeatureName")
	public ResponseEntity<?> getDistinctFeatureNameList() {
		Optional<List<String>> list = Optional.ofNullable(messageConfigurationDbRepository.findDistinctFeatureName());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "getDistinctSystemFeatureName")
	@GetMapping("/getDistinctSystemFeatureName")
	public ResponseEntity<?> getDistinctSystemFeatureName() {
		Optional<List<String>> list = Optional.ofNullable(systemConfigurationDbRepository.findDistinctFeatureName());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getDistinctAuditFeatureName")
	@GetMapping("/getDistinctAuditFeatureName")
	public ResponseEntity<?> getDistinctAuditFeatureName() {
		Optional<List<String>> list = Optional.ofNullable(alertDbRepo.findDistinctFeature());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "getDistinctAlertFeatureName")
	@GetMapping("/getDistinctAlertFeatureName")
	public ResponseEntity<?> getDistinctAlertFeatureName() {
		Optional<List<String>> list = Optional.ofNullable(alertDbRepo.findDistinctFeature());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
