package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceir.CeirCode.model.app.SubFeature;

public interface SubFeatureRepo extends JpaRepository<SubFeature, Long>{

	public List<SubFeature> findAll();
}
