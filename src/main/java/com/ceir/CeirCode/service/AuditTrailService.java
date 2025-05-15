package com.ceir.CeirCode.service;


import com.ceir.CeirCode.model.app.AuditTrailModel;
import com.ceir.CeirCode.model.audit.AuditDB;
import com.ceir.CeirCode.repo.audit.AuditDBRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuditTrailService {

    @Autowired
    AuditDBRepo auditDb;

    private static final Logger logger = LogManager.getLogger(AuditTrailService.class);

    /*private AuditTrailRepository auditTrailRepository;

    public AuditTrailService(AuditTrailRepository auditTrailRepository){
        this.auditTrailRepository=auditTrailRepository;
    }*/

    @Transactional(rollbackOn = {SQLException.class})
    public <T extends AuditTrailModel> void auditTrailOperation(T t, String feature, String subFeature) {
        Optional<T> optional = Optional.ofNullable(t);
        if (optional.isPresent()) {
            T param = optional.get();
            long userId = Objects.isNull(param.getUserId()) ? 0L : param.getUserId();
            String userName = Objects.isNull(param.getUserName()) ? "NA" : param.getUserName();
            Long userTypeId = Objects.isNull(param.getUserTypeId()) ? 0L : param.getUserTypeId();
            String userType = Objects.isNull(param.getUserType()) ? "NA" : param.getUserType();
            Long featureId = Objects.isNull(param.getFeatureId()) ? 0L : param.getFeatureId();
            String roleType = Objects.isNull(param.getRoleType()) ? "NA" : param.getRoleType();
            String publicIp = Objects.isNull(param.getPublicIp()) ? "NA" : param.getPublicIp();
            String browser = Objects.isNull(param.getBrowser()) ? "NA" : param.getBrowser();
            AuditDB auditTrailPayload = new AuditDB(userId, userName, userTypeId, userType, featureId, feature, subFeature, "", "NA", roleType, publicIp, browser);
            logger.info("auditTrail saved for Feature in [" + feature + "] and Sub Feature [" + subFeature + "] with payload " + auditTrailPayload);
            auditDb.save(auditTrailPayload);
        }
    }
}
