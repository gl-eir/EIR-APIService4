package com.ceir.CeirCode.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.app.AlertDb;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.app.UserProfile;

public interface RunningAlertDbRepo extends JpaRepository<RunningAlertDb, Long>,JpaSpecificationExecutor<RunningAlertDb>{

	public RunningAlertDb save(RunningAlertDb alertDb);
}
