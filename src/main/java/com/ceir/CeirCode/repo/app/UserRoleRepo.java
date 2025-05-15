package com.ceir.CeirCode.repo.app;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.Userrole;
public interface UserRoleRepo extends JpaRepository<Userrole, Integer>{
	public Userrole save(Userrole role);
	public List<Userrole> findAll(); 
	public List<Userrole> findByUserData_Id(long id);   
	public Userrole findByUserData_IdAndUsertypeData_Id(long userid,long usertypeId);
	public List<Userrole> findDistinctUserDataByUsertypeData_IdOrUsertypeData_Id(long usertyp1,long usertype2);
	public boolean existsByUserData_IdAndUsertypeData_Id(long userId,long usertypeId);
	public void deleteByUserData_IdAndUsertypeData_Id(long id,long usertypeId);
}
 