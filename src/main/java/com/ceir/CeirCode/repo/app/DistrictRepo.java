package com.ceir.CeirCode.repo.app;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ceir.CeirCode.model.app.District;
public interface DistrictRepo extends JpaRepository<District, Long>{
	public List<District> findByProvince(String province);
	public List<District> findByDistrictAndProvince(String district,String province);
	public boolean existsByProvinceAndDistrict(String province,String district);
	/*
	 * @Query("select PROVINCE,DISTRICT,COMMUNE,VILLAGE from district_db d INNER JOIN commune_db c ON d.ID=c.DISTRICT_ID INNER JOIN village_db v ON c.ID=v.COMMUNE_ID"
	 * ) List<AddressObject> fetchRecordInnerJoin();
	 */
	@Transactional
	@Modifying
	@Query(value = "update district_db  set district =:currentDistrictName  where district =:district and province =:province", nativeQuery = true)
	public int updateDistrictName(String currentDistrictName,String district,String province);

	public void deleteByDistrict(String district);
	
	@Transactional
	@Modifying
	@Query(value = "update district_db set province =:province  where district =:district and province =:oldprovince ", nativeQuery = true)
	public int updateProvinceInDistrict(String province,String district,String oldprovince);

}
