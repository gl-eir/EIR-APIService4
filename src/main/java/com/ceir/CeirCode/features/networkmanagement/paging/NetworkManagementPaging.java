package com.ceir.CeirCode.features.networkmanagement.paging;


import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.SpecificationBuilder.GenericSpecificationBuilder;
import com.ceir.CeirCode.configuration.PropertiesReader;
import com.ceir.CeirCode.configuration.SortDirection;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.model.app.AuditTrailModel;
import com.ceir.CeirCode.model.app.NetworkEntity;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.repo.app.NetworkManagementRepository;
import com.ceir.CeirCode.util.HelperUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NetworkManagementPaging { private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PropertiesReader propertiesReader;
    private NetworkManagementRepository networkManagementRepository;
    private HelperUtility helperUtility;

    public NetworkManagementPaging(NetworkManagementRepository networkManagementRepository, HelperUtility helperUtility) {
        this.networkManagementRepository = networkManagementRepository;
        this.helperUtility = helperUtility;
    }


    public Page<NetworkEntity> findAll(NetworkEntity networkEntity, int pageNo, int pageSize) {

        try {
            logger.info("request received : " + networkEntity + " [pageNo] ...." + pageNo + " [pageSize]...." + pageSize);
            String sort = null, orderColumn = null;
            if (Objects.nonNull(networkEntity.getAuditTrailModel())) {
                sort = Objects.nonNull(networkEntity.getAuditTrailModel().getSort()) ? networkEntity.getAuditTrailModel().getSort() : "desc";
                orderColumn = Objects.nonNull(networkEntity.getAuditTrailModel().getColumnName()) ? networkEntity.getAuditTrailModel().getColumnName() : "Modified On";
            } else {
                sort = "desc";
                orderColumn = "Modified On";
            }
            orderColumn = validateColumnName(orderColumn);
            /*orderColumn = sortColumnName(orderColumn);*/
            Sort.Direction direction = SortDirection.getSortDirection(sort);
            logger.info("request received : " + networkEntity + " [pageNo] ...." + pageNo + " [pageSize]...." + pageSize);

            logger.info("orderColumn is : " + orderColumn + " & direction is : " + direction);

            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            Page<NetworkEntity> page = networkManagementRepository.findAll(buildSpecification(networkEntity).build(), pageable);
            logger.info("paging API response [" + page + "]");
            return page;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    private String validateColumnName(String columnName) {
        // List of valid columns, ideally you should fetch this list from the database schema
        List<String> validColumns = Arrays.asList("id", "mnoName", "neName", "neType", "gtAddress", "hostname");

        // Default column if the provided column is invalid
        String defaultColumn = "id";

        if (validColumns.contains(columnName)) {
            return columnName;
        } else {
            logger.warn("Invalid column name provided: " + columnName + ". Using default column: " + defaultColumn);
            return defaultColumn;
        }
    }

    public String sortColumnName(String columnName) {
        Map<String, String> map = new HashMap<>();
        if (Objects.nonNull(columnName) && !columnName.isEmpty()) {
            map.put("id", "id");
            map.put("MNO Name", "mnoName");
            map.put("NE Name", "neName");
            map.put("NE Type", "neType");
            map.put("GT Address", "gtAddress");
            map.put("hostname", "hostname");
        }
        return map.get(columnName);
    }

    private GenericSpecificationBuilder<NetworkEntity> buildSpecification(NetworkEntity networkEntity) {
        logger.info("FilterRequest payload : [" + networkEntity + "]");
        GenericSpecificationBuilder<NetworkEntity> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);

        Optional<AuditTrailModel> optional = Optional.ofNullable(networkEntity.getAuditTrailModel());
     /*   if (optional.isPresent()) {
            cmsb.with(new SearchCriteria("createdOn", optional.get().getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));
            cmsb.with(new SearchCriteria("createdOn", optional.get().getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));
        }*/
        cmsb.with(new SearchCriteria("id", networkEntity.getId(), SearchOperation.LIKE, Datatype.LONG));
        cmsb.with(new SearchCriteria("mnoName", networkEntity.getMnoName(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("neName", networkEntity.getNeName(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("neType", networkEntity.getNeType(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("gtAddress", networkEntity.getGtAddress(), SearchOperation.LIKE, Datatype.STRING));
        cmsb.with(new SearchCriteria("hostname", networkEntity.getHostname(), SearchOperation.LIKE, Datatype.STRING));
        return cmsb;
    }

/*    public Optional<EIRSListManagementEntity> find(Long id) {
        return eirsListManagementRepository.findById(id);
    }*/
}
