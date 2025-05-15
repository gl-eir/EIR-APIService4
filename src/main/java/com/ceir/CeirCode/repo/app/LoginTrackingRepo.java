package com.ceir.CeirCode.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.LoginTracking;

public interface LoginTrackingRepo extends JpaRepository<LoginTracking, Integer>{

	public LoginTracking save(LoginTracking loginTracking);
}
