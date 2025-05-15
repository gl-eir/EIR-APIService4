package com.ceir.CeirCode.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.SubFeature;
import com.ceir.CeirCode.repo.app.SubFeatureRepo;
import com.ceir.CeirCode.util.HttpResponse;

@Service
public class SubFeatureService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	SubFeatureRepo subFeatureRepo;
	
	
	public ResponseEntity<?> getSubFeatureData(){
		try {
			List<SubFeature> subFeature=subFeatureRepo.findAll();
			return new ResponseEntity<>(subFeature, HttpStatus.OK);
		}
		catch(Exception e){
			log.info("exception occurs");
			log.info(e.toString());
			HttpResponse response=new HttpResponse();
			response.setResponse("Oop something wrong happened");
			response.setStatusCode(409);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
	}
}
