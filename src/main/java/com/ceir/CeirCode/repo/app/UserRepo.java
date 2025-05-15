package com.ceir.CeirCode.repo.app;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ceir.CeirCode.model.app.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long> ,JpaSpecificationExecutor<User>{
    
	@Transactional
	public User save(User u); 
	public User findById(long id);
	public User findByUsernameAndPassword(String username,String password);
	public User findByUsername(String user); 
	public User findByUserProfile_Id(long id);
	long countByUsertype_Id(long id);
	public User deleteById(long id);
	public List<User> findByUsertype_IdAndCurrentStatus(long usertypeId,Integer status); 	
	public List<User> findByUsertype_IdAndCurrentStatusAndUserProfile_ArrivalPortAndUserProfile_PortAddress(long usertypeId,Integer status,Integer port,Integer address); 	
	
	
	@Transactional
	@Modifying
	@Query(value="update user  set MODIFIED_ON = CURRENT_TIMESTAMP, current_Status = :currentStatus ,previous_status = :previousStatus where username = :username",nativeQuery = true)
	public int setStatusForUser(int currentStatus, int previousStatus,String username);
	
	@Transactional
	@Modifying
	@Query(value="update user  set MODIFIED_ON = CURRENT_TIMESTAMP, current_Status = :currentStatus ,previous_status = :previousStatus where id = :id",nativeQuery = true)
	public int setStatusForUser(int currentStatus, int previousStatus,long id);
	
	Optional<User> getByUsername(String userName);
}
