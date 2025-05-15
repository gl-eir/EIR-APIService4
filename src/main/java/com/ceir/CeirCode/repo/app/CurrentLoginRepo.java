package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceir.CeirCode.model.app.CurrentLogin;
import com.ceir.CeirCode.model.app.LoginTracking;

@Repository
public interface CurrentLoginRepo extends JpaRepository<CurrentLogin,Long>{
	public CurrentLogin save(CurrentLogin currentLogin);
	public boolean existsByCurrentUserLogin_Id(long id);
	public List<CurrentLogin> findByCurrentUserLogin_Id(long id);
	public void deleteByCurrentUserLogin_Id(long id);
}
