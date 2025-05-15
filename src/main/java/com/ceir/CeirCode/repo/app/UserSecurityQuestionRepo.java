package com.ceir.CeirCode.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.Securityquestion;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserSecurityquestion;

public interface UserSecurityQuestionRepo extends JpaRepository<UserSecurityquestion, Integer>{

	public UserSecurityquestion save(UserSecurityquestion userSecurityquestion); 
	public UserSecurityquestion findByUser_IdAndSecurityQuestion_IdAndAnswer(long userId,long questionId,String answer);
	    
}
