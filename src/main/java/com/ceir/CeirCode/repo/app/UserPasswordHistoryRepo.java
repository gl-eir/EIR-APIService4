package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.UserPasswordHistory;

public interface UserPasswordHistoryRepo extends JpaRepository<UserPasswordHistory, Long>{
	public boolean existsByPasswordAndUserPassword_Id(String password,long id);
	public UserPasswordHistory findTopByUserPassword_IdOrderByIdAsc(long id);
	public long countByUserPassword_Id(long id);
	
	/*
	 * @Query(value ="delete  FROM  UserPasswordHistory where id=oid", nativeQuery =
	 * true) public void deleteId (long oid );
	 */
}
