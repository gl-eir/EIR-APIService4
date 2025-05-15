package com.ceir.CeirCode.repo.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.report.UserLoginReport;

public interface LoginReportRepo extends JpaRepository<UserLoginReport, Long>,JpaSpecificationExecutor<UserLoginReport>{
}
