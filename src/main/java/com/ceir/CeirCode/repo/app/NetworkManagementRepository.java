package com.ceir.CeirCode.repo.app;


import com.ceir.CeirCode.model.app.NetworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.SQLException;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface NetworkManagementRepository extends JpaRepository<NetworkEntity, Long>, JpaSpecificationExecutor<NetworkEntity> {

    NetworkEntity findByMnoNameAndNeNameAndNeTypeAndGtAddressAndHostname(String mnoName, String neName, String neType, String gtAddress, String hostname);
    @Modifying
    @Query("UPDATE NetworkEntity SET mnoName =:#{#e.mno_name}, neName =:#{#e.neName}, neType =:#{#e.neType}, gtAddress =:#{#e.gtAddress}, hostname =:#{#e.hostname} WHERE id =:#{#e.id}")
    int updateColumns(@Param("e") NetworkEntity networkEntity);
}
