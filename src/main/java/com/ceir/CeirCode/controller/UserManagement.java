package com.ceir.CeirCode.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ceir.CeirCode.filtermodel.UserMgmtFilter;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserDetails;
import com.ceir.CeirCode.othermodel.UserPort;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.UserMgmtService;
import com.ceir.CeirCode.util.HttpResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("userMgmt")
@CrossOrigin
public class UserManagement {


	@Autowired
	UserMgmtService userService;


	@ApiOperation(value = "get user by id", response = HttpResponse.class)
	@PostMapping("/getById")
	public GenricResponse findDataByPort(@RequestBody AllRequest request){
		return userService.viewById(request);
	}

	@ApiOperation(value="save user")
	@PostMapping("/save")
	public GenricResponse saveUser(@RequestBody UserDetails user){
		return userService.saveUser(user);
	}

	@ApiOperation(value="update user")
	@PostMapping("/update")
	public GenricResponse updateUser(@RequestBody UserDetails user){
		return userService.updateUser(user);
	}


	@ApiOperation(value="delete by id")
	@PostMapping("/delete")
	public GenricResponse deleteUserById(@RequestBody AllRequest request){
		return userService.deleteById(request);
	}

	@ApiOperation(value = "user data.", response = User.class)
	@PostMapping("/view") 
	public MappingJacksonValue view(@RequestBody UserMgmtFilter filter,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file){
		MappingJacksonValue mapping = null;
		if(file == 0) {
		Page<User> userData  = userService.viewAllRecord(filter, pageNo, pageSize,"view");
		mapping = new MappingJacksonValue(userData);
		}
		else {
			FileDetails fileDetails = userService.getFile(filter);
			mapping = new MappingJacksonValue(fileDetails);
		}
		return mapping;		
	}

	@ApiOperation(value="user data by usertype Id")
	@PostMapping("/databyUserTypeId/{usertypeId}")
	public GenricResponse updateUser(@PathVariable("usertypeId")long userTypeId){
		return userService.DataByUserTypeId(userTypeId);
	}

	@ApiOperation(value="user data by usertype Id")
	@PostMapping("/DataByPortAndUserType/")
	public GenricResponse dataByPortAndUserType(@RequestBody UserPort port){
		return userService.portData(port);
	}



}
