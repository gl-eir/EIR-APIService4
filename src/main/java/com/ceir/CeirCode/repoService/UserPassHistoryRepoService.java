package com.ceir.CeirCode.repoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.UserPasswordHistory;
import com.ceir.CeirCode.repo.app.UserPasswordHistoryRepo;

@Service
public class UserPassHistoryRepoService {

	@Autowired
    UserPasswordHistoryRepo userPassHistoryRepo;
	
	public UserPasswordHistory saveUserPassword(UserPasswordHistory data) {
		try {
			UserPasswordHistory userPasswordHistory=userPassHistoryRepo.save(data);
			return userPasswordHistory;
		}
		catch(Exception e) {
			return null;
		}
	}
	public boolean passwordExist(String password,long id) {
		try {
			return userPassHistoryRepo.existsByPasswordAndUserPassword_Id(password, id);
		}
		catch(Exception e) {
			boolean b=false;
			return b;
		}
	}
	
	public long countByUserId(long id) {
		try {
			return userPassHistoryRepo.countByUserPassword_Id(id);
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public UserPasswordHistory getPasswordHistory(long id) {
		try {
			UserPasswordHistory userPasswordHistory=userPassHistoryRepo.findTopByUserPassword_IdOrderByIdAsc(id);
			return userPasswordHistory;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	
	
}
