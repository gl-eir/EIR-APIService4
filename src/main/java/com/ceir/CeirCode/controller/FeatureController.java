package com.ceir.CeirCode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.model.app.PeriodValidate;
import com.ceir.CeirCode.model.app.StakeholderFeature;
import com.ceir.CeirCode.model.app.UserLogin;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.FeatureService;
import com.ceir.CeirCode.util.HttpResponse;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class FeatureController{
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	FeatureService featureService;

	@ApiOperation(value = "user feature list", response = HttpResponse.class)
	/*
	 * @PostMapping("/featureList") public ResponseEntity<?>
	 * featureList(@RequestParam Integer userid,UserLogin userLogin){ return
	 * featureService.featureData(userid,userLogin) ; }
	 */
	@PostMapping("/featureList/{userid}")     
	public ResponseEntity<?> featureList(@PathVariable Integer userid){
		return featureService.featureData(userid) ;
	}
	@ApiOperation(value = "period validate", response = HttpResponse.class)
	@PostMapping("/periodValidate")     
	public MappingJacksonValue  periodValidate(@RequestBody PeriodValidate periodValidate){
		HttpResponse response =new HttpResponse();
		log.info(" periodValidate Request:::::::::"+periodValidate);
		response=featureService.periodValidation(periodValidate);
		MappingJacksonValue mapping=new MappingJacksonValue(response);
		return mapping;
	} 


	@ApiOperation(value = "all features", response = StakeholderFeature.class)
	@CrossOrigin
	@PostMapping("/getAllFeatures") 
	public ResponseEntity<?> featureData(){
		return featureService.featureData();
	}
	
	@ApiOperation(value = "feature name by Id", response = GenricResponse.class)
	@CrossOrigin
	@PostMapping("/nameById/{id}") 
	public GenricResponse featureNameById(@PathVariable("id")long id){
		return featureService.featureNameById(id);
	}
	



}