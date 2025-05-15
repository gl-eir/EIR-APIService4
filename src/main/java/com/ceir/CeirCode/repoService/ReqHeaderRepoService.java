package com.ceir.CeirCode.repoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.repo.oam.ReqHeadersRepo;

@Service
public class ReqHeaderRepoService {

	@Autowired
	ReqHeadersRepo reqHeaderRepo;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	
	public RequestHeaders saveRequestHeader(RequestHeaders headers) {
	    try
	    {
        log.info("request headers going to save");	   
	    return 	reqHeaderRepo.save(headers);
	    }
	    catch(Exception e)
	    {
	    	log.info("error occurs while save request headers");
	    	log.info(e.toString());
	    	return null;	
	    }
		
		
	}
	
}
