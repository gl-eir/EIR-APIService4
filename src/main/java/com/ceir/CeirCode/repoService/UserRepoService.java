package com.ceir.CeirCode.repoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.repo.app.UserRepo;

@Service
public class UserRepoService {

	
	@Autowired
	UserRepo userRepo;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public User findByUSerId(long userId) {
		
		try {
			return userRepo.findById(userId);
		}
		catch(Exception e) {
			log.info("user data failed to find bu this id: "+userId);
			return null;
		}
	}
	
public long countByUsertypeId(long userTypeId) {
		
		try {
			return userRepo.countByUsertype_Id(userTypeId);
		}
		catch(Exception e) {
			log.info("user count failed to find by usertype id: "+userTypeId);
			return 0;
		}
	}
}
