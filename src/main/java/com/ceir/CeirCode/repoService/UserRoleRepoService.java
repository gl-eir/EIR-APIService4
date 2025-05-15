package com.ceir.CeirCode.repoService;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.Userrole;
import com.ceir.CeirCode.repo.app.UserRoleRepo;

@Service
public class UserRoleRepoService {

@Autowired
UserRoleRepo userRoleRepo;
	
private final Logger log = LoggerFactory.getLogger(getClass());
	
	public List<Userrole> findByUserId(long userId) {
		
		try {
			return userRoleRepo.findByUserData_Id(userId);
		}
		catch(Exception e) {
			log.info("user role data failed to find bu this user id: "+userId);
			return new ArrayList<Userrole>();
		}
	}
	public boolean existsByUerIdAndUsertypeId(long userId,long usertypeId)
	{

		try {
			return userRoleRepo.existsByUserData_IdAndUsertypeData_Id(userId, usertypeId);
			
		}
		catch(Exception e) {
			log.info("user role data failed to find bu this user id: "+userId);
			return false;
		}
	}
	
	@Transactional
	public List<Userrole> saveRoleList(List<Userrole> rolesList)
	{

		try {
			return userRoleRepo.saveAll(rolesList);
		}
		catch(Exception e) {
			log.info("error occurs in updation user role data");
			return new ArrayList<Userrole>();
		}
	}
	@Transactional
	public Userrole saveRole(Userrole rolesList)
	{

		try {
			return userRoleRepo.save(rolesList);
		}
		catch(Exception e) {
			log.info("error occurs in updation user role data");
			return null;
		}
	}
	
	@Transactional
	public boolean deletebyUserIdandUsertype(long userid,long usertypeId)
	{

		try {
			 userRoleRepo.deleteByUserData_IdAndUsertypeData_Id(userid, usertypeId);
			 return true;
		}
		catch(Exception e) {
			log.info("error occurs in delete user role data");
			log.info(e.toString());
			return false;
		}
	}
	
}
