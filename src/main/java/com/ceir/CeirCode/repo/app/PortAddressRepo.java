package com.ceir.CeirCode.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.model.app.UserProfile;

public interface PortAddressRepo extends JpaRepository<PortAddress, Long> ,JpaSpecificationExecutor<PortAddress>{

	public List<PortAddress> findByPort(Integer port);
	public PortAddress findById(long id);
	public PortAddress save(PortAddress portAddress);
	public void deleteById(long id);
}
