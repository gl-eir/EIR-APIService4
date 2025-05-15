package com.ceir.CeirCode.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceir.CeirCode.filtermodel.LoginReportFilter;
import com.ceir.CeirCode.model.report.UserLoginReport;
import com.ceir.CeirCode.service.ReportService;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ReportService reportService;

	@PostMapping("/userLogin")
	public MappingJacksonValue userLoginReport(@RequestBody LoginReportFilter filter){
		MappingJacksonValue mapping = null;
		List<UserLoginReport> userData= reportService.userReportData(filter);
		log.info("		List<UserLoginReport> :::::::"+userData);
		mapping = new MappingJacksonValue(userData);
		return mapping;		
	}
}
