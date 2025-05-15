package com.ceir.CeirCode.repo.oam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.oam.RequestHeaders;

public interface ReqHeadersRepo extends JpaRepository<RequestHeaders, Long> , JpaSpecificationExecutor<RequestHeaders> {

}
