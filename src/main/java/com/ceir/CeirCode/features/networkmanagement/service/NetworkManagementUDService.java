package com.ceir.CeirCode.features.networkmanagement.service;


import com.ceir.CeirCode.enums.FeaturesEnum;
import com.ceir.CeirCode.model.app.NetworkEntity;
import com.ceir.CeirCode.repo.app.NetworkManagementRepository;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.AuditTrailService;
import com.ceir.CeirCode.util.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class NetworkManagementUDService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private NetworkManagementRepository networkManagementRepository;
    private AuditTrailService auditTrailService;

    public NetworkManagementUDService( AuditTrailService auditTrailService, NetworkManagementRepository networkManagementRepository) {
        this.auditTrailService = auditTrailService;
        this.networkManagementRepository = networkManagementRepository;
    }

    @Transactional
    public GenricResponse delete(NetworkEntity networkEntity) {
        String requestType = "NETWORK_MGMT_DELETE";
        boolean isIdExist = networkManagementRepository.existsById(networkEntity.getId());
        logger.info("isIdExist" + isIdExist);
        if (isIdExist) {
            NetworkEntity networkList = networkManagementRepository.findByMnoNameAndNeNameAndNeTypeAndGtAddressAndHostname(networkEntity.getMnoName(), networkEntity.getNeName(), networkEntity.getNeType(), networkEntity.getGtAddress(), networkEntity.getHostname());
            logger.info("networkList " + networkList);

            networkManagementRepository.deleteById(networkEntity.getId());

            auditTrailService.auditTrailOperation(networkEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));

            return new GenricResponse("Record deleted", 1);
        }

        return new GenricResponse("Unable to delete record", 0);
    }


    @Transactional
    public GenricResponse update(NetworkEntity networkEntity) {
        Optional<NetworkEntity> byId = networkManagementRepository.findById(networkEntity.getId());
        logger.info("response {}", byId);
        if (byId.isPresent()) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();
            NetworkEntity result = gson.fromJson(gson.toJson(byId.get()), NetworkEntity.class);

            // Update fields except for the tag
            result.setMnoName(networkEntity.getMnoName());
            result.setNeName(networkEntity.getNeName());
            result.setNeType(networkEntity.getNeType());
            result.setGtAddress(networkEntity.getGtAddress());
            result.setHostname(networkEntity.getHostname());

            logger.info("payload to save {}", result);
            updateNetworkManagement(result);

            String requestType = "NETWORK_MGMT_UPDATE";
            logger.info("requestType [" + requestType + "]");
            auditTrailService.auditTrailOperation(networkEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));

            return new GenricResponse("Record successfully updated", 1);
        }
        return new GenricResponse("No record found for id " + networkEntity.getId(), 0);
    }

    private void updateNetworkManagement(NetworkEntity networkEntity) {
        try {
            networkManagementRepository.save(networkEntity);
            logger.info("address_list_mgmt record has been updated for id {}", networkEntity.getId());
        } catch (Exception e) {
            logger.info("exception occured while updating data {}", e.getMessage());
        }
    }


    public NetworkEntity saveLink(NetworkEntity networkEntity) {
        logger.info("Saved link: {}", networkEntity);
        return networkManagementRepository.save(networkEntity);
    }
}
