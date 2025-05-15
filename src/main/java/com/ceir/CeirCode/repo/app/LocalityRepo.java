package com.ceir.CeirCode.repo.app;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.Locality;


public interface LocalityRepo extends JpaRepository<Locality, Long>,JpaSpecificationExecutor<Locality>{
	
	@Transactional
	@Modifying
	@Query(value = "update locality_db set MODIFIED_ON=CURRENT_TIMESTAMP, province =:currentProvinceName, MODIFIED_BY = :modifiedBy  where province=:province", nativeQuery = true)
	public int updateProvince(String currentProvinceName,String province,String modifiedBy);
	
	@Transactional
	@Modifying
	@Query(value = "update locality_db set MODIFIED_ON=CURRENT_TIMESTAMP, district =:currentDistrictName, MODIFIED_BY = :modifiedBy  where district =:district and province =:province", nativeQuery = true)
	public int updateDistrict(String currentDistrictName,String district,String province,String modifiedBy);

	@Transactional
	@Modifying
	@Query(value = "update locality_db  set MODIFIED_ON=CURRENT_TIMESTAMP, commune =:currentCommuneName, MODIFIED_BY = :modifiedBy where district =:district and commune =:commune and province=:province", nativeQuery = true)
	public int updateCommune(String currentCommuneName,String district,String commune,String province,String modifiedBy);

	
	@Transactional
	@Modifying
	@Query(value = "update locality_db  set MODIFIED_ON=CURRENT_TIMESTAMP,village =:village, MODIFIED_BY = :modifiedBy  where district =:district and commune =:commune and id =:id", nativeQuery = true)
	public int updateVillage(String village,String district,String commune,long id,String modifiedBy);

	public List<Locality> findByProvinceAndDistrictAndCommuneAndVillage(String province,String district,String commune,String village);
	
	public List<Locality> findByProvinceAndDistrictAndCommune(String province,String district,String commune);
	
	public List<Locality> findByProvinceAndDistrict(String province,String district);
	
	public List<Locality> findByCountryAndProvince(String country,String province);
	
	public void deleteById(long id);
	
	
}
