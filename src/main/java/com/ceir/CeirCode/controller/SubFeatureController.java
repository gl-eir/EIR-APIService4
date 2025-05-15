package com.ceir.CeirCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.service.SubFeatureService;
import com.ceir.CeirCode.util.HttpResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/subFeature")
public class SubFeatureController {

	@Autowired
	SubFeatureService subFeatureService;
	
	@ApiOperation(value = "su feature data", response = HttpResponse.class)
	@CrossOrigin
	@PostMapping("/view") 
	public ResponseEntity<?> getUsertypes(@RequestHeader HttpHeaders headers){
		return subFeatureService.getSubFeatureData();
	}
}
