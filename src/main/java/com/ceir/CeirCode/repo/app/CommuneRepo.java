package com.ceir.CeirCode.repo.app;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.Commune;
import com.ceir.CeirCode.model.app.District;

public interface CommuneRepo extends JpaRepository<Commune, Long>{
	public List<Commune> findByDistrictID(Long districtID);
	public List<Commune> findByDistrictIDAndCommune(Long districtID,String commune);
	public boolean existsByDistrictIDAndCommune(Long districtID,String commune); 
	public Commune findByDistrictIDAndCommune(long districtID,String currentCommuneName);
	public Commune findByCommune(String commune);
	
	@Transactional
	@Modifying
	@Query(value = "update commune_db  set commune =:currentCommuneName  where DISTRICT_ID =:districtID and commune =:commune", nativeQuery = true)
	public int updateCommuneName(String currentCommuneName,long districtID,String commune);
	
	public void deleteByCommune(String commune);
}
