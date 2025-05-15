package com.ceir.CeirCode.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceir.CeirCode.model.app.UserTypeUrlMapping;


@Repository
public interface UserTypeUrlMappingRepository extends JpaRepository<UserTypeUrlMapping, Long>{
	public UserTypeUrlMapping findByUserTypeIdAndUrlPathLike(long userTypeId, String urlPath);
}
