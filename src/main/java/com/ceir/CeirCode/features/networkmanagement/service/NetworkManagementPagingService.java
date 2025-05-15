package com.ceir.CeirCode.features.networkmanagement.service;


import com.ceir.CeirCode.enums.FeaturesEnum;
import com.ceir.CeirCode.features.networkmanagement.paging.NetworkManagementPaging;
import com.ceir.CeirCode.model.app.NetworkEntity;
import com.ceir.CeirCode.repo.app.NetworkManagementRepository;
import com.ceir.CeirCode.service.AuditTrailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class NetworkManagementPagingService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private NetworkManagementPaging networkManagementPaging;

    private AuditTrailService auditTrailService;
    private NetworkManagementRepository networkManagementRepository;

    public NetworkManagementPagingService(NetworkManagementPaging networkManagementPaging, AuditTrailService auditTrailService, NetworkManagementRepository networkManagementRepository) {
        this.networkManagementPaging = networkManagementPaging;
        this.auditTrailService = auditTrailService;
        this.networkManagementRepository = networkManagementRepository;
    }

    public MappingJacksonValue paging(NetworkEntity networkEntity, int pageNo, int pageSize) {
        Page<NetworkEntity> page = networkManagementPaging.findAll(networkEntity, pageNo, pageSize);
        String requestType = "NETWORK_MGMT_VIEWALL";
        logger.info("requestType [" + requestType + "]");
        auditTrailService.auditTrailOperation(networkEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(page);
    }


    public MappingJacksonValue find(NetworkEntity networkEntity) {
        Optional<NetworkEntity> optional = networkManagementRepository.findById(networkEntity.getId());
        if (optional.isPresent()) {
            return new MappingJacksonValue(optional.get());
        }
        return new MappingJacksonValue(null);
    }
}
