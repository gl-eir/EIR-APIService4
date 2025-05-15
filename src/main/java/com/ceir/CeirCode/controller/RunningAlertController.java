package com.ceir.CeirCode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.filtermodel.AlertDbFilter;
import com.ceir.CeirCode.filtermodel.RunningAlertFilter;
import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.repo.app.RunningAlertDbRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.service.AlertDbService;
import com.ceir.CeirCode.service.RunningAlertDbService;

import io.swagger.annotations.ApiOperation;
@RestController
@CrossOrigin
@RequestMapping("/runningAlert")
public class RunningAlertController {

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;

	@Autowired
	RunningAlertDbService runAlertService;
	
	@ApiOperation(value = "running alert db  data.", response = RunningAlertDb.class)
	@PostMapping("/viewAll") 
	public MappingJacksonValue viewRecord(@RequestBody RunningAlertFilter filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file,
			@RequestParam(value = "source", defaultValue = "menu") String source){
		MappingJacksonValue mapping = null;
		if( file == 0) {
			Page<RunningAlertDb> alertResponse  = runAlertService.viewRunningAlertData(filterRequest, pageNo, pageSize,"View",source);
			List<SystemConfigListDb> statusList=systemConfigRepo.getByTag("ALERT_STATE");
			if(alertResponse!=null) {
			for(RunningAlertDb alert:alertResponse.getContent()) {
				for(SystemConfigListDb data:statusList) {
					Integer value=data.getValue();
					if(alert.getStatus()==value) {
						alert.setStatusInterp(data.getInterp());
					}
				}
			}
			}
			mapping = new MappingJacksonValue(alertResponse);
			
			
		}else {
			FileDetails fileDetails = runAlertService.getRunningAlertInFile(filterRequest,source);
			mapping = new MappingJacksonValue(fileDetails);
		}
		return mapping;
	}
}
