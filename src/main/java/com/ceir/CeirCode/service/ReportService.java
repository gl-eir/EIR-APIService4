package com.ceir.CeirCode.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.SpecificationBuilder.GenericSpecificationBuilder;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.filtermodel.LoginReportFilter;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.report.UserLoginReport;
import com.ceir.CeirCode.repo.report.LoginReportRepo;
@Service
public class ReportService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	LoginReportRepo loginRepo;
	
	public List<UserLoginReport> userReportData(LoginReportFilter filterRequest){

		GenericSpecificationBuilder<UserLoginReport> uPSB = new GenericSpecificationBuilder<UserLoginReport>(propertiesReader.dialect);	
		if(Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate()!="")
			uPSB.with(new SearchCriteria("createdOn",filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
		if(Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate()!="")
			uPSB.with(new SearchCriteria("createdOn",filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
		
		try {
			List<UserLoginReport> userReport = loginRepo.findAll( uPSB.build(),new Sort(Sort.Direction.ASC, "createdOn"));
			
			return userReport;
			} catch (Exception e) {
			log.info("Exception found ="+e.getMessage());
			log.info(e.getClass().getMethods().toString());
			log.info(e.toString());
			return new ArrayList<UserLoginReport>();
		}
	}

}
