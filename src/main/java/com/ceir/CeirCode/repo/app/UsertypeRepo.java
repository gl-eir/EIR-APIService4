package com.ceir.CeirCode.repo.app;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ceir.CeirCode.model.app.Usertype;

@Repository
public interface UsertypeRepo extends JpaRepository<Usertype, Long> ,JpaSpecificationExecutor<Usertype>{
	public List<Usertype> findAll();
	public Usertype findById(long id);
	public List<Usertype> findBySelfRegister(Integer id);
	public Usertype findByUsertypeName(String usertype);
	public List<Usertype> findBySelfRegisterOrSelfRegister(Integer id,Integer usertype);
	public List<Usertype> findBySelfRegisterAndStatus(Integer id,Integer status) ;
	public List<Usertype> findBySelfRegisterOrSelfRegisterAndStatus(Integer id,Integer usertype,Integer status);
	
}
