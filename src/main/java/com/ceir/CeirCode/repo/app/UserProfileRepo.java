package com.ceir.CeirCode.repo.app;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.app.UserProfile;
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> ,JpaSpecificationExecutor<UserProfile>{

	public UserProfile findById(long id); 
	public UserProfile findByUser_Id(long id);                            
    public UserProfile findByPhoneNo(String phoneNo);
    public UserProfile findByEmail(String email);
	public boolean existsByEmailAndUser_CurrentStatusNot(String email,int currentStatus);
	public boolean existsByPhoneNoAndUser_CurrentStatusNot(String phoneNo,int currentStatus);

} 
