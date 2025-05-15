package com.ceir.CeirCode.features.networkmanagement.service;


import com.ceir.CeirCode.enums.FeaturesEnum;
import com.ceir.CeirCode.features.networkmanagement.export.NetworkManagementExport;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.NetworkEntity;
import com.ceir.CeirCode.service.AuditTrailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class NetworkManagementExportService {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private NetworkManagementExport networkManagementExport;

    private AuditTrailService auditTrailService;

    public NetworkManagementExportService(NetworkManagementExport networkManagementExport, AuditTrailService auditTrailService) {
        this.networkManagementExport = networkManagementExport;
        this.auditTrailService = auditTrailService;
    }

    public MappingJacksonValue export(NetworkEntity networkEntity) {

        String requestType = "NETWORK_MGMT_EXPORT";
        FileDetails export = networkManagementExport.export(networkEntity, FeaturesEnum.getFeatureName(requestType).replace(" ", "_"));
        logger.info("requestType [" + requestType + "]");
        auditTrailService.auditTrailOperation(networkEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(export);
    }
}
