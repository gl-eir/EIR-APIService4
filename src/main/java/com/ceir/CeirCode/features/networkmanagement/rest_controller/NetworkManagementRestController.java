package com.ceir.CeirCode.features.networkmanagement.rest_controller;


import com.ceir.CeirCode.features.networkmanagement.service.NetworkManagementExportService;
import com.ceir.CeirCode.features.networkmanagement.service.NetworkManagementPagingService;
import com.ceir.CeirCode.features.networkmanagement.service.NetworkManagementUDService;

import com.ceir.CeirCode.model.app.NetworkEntity;
import com.ceir.CeirCode.response.GenricResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/v1/network-management")
public class NetworkManagementRestController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final NetworkManagementPagingService networkManagementPagingService;
    private final NetworkManagementExportService networkManagementExportService;
    private final NetworkManagementUDService networkManagementUDService;

    public NetworkManagementRestController(NetworkManagementUDService networkManagementUDService, NetworkManagementPagingService networkManagementPagingService, NetworkManagementExportService networkManagementExportService) {
        this.networkManagementUDService = networkManagementUDService;
        this.networkManagementPagingService = networkManagementPagingService;
        this.networkManagementExportService = networkManagementExportService;
    }


    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody NetworkEntity networkEntity, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("Network Entity payload for paging [" + networkEntity + "]");
        return networkManagementPagingService.paging(networkEntity, pageNo, pageSize);
    }

    @PostMapping
    public MappingJacksonValue findByID(@RequestBody NetworkEntity networkEntity) {
        return new MappingJacksonValue(networkManagementPagingService.find(networkEntity));
    }

    @PostMapping("/export")
    public MappingJacksonValue export(@RequestBody NetworkEntity networkEntity) {
        logger.info("Network Entity payload for export [" + networkEntity + "]");
        return networkManagementExportService.export(networkEntity);
    }

    @DeleteMapping("/delete")
    public GenricResponse delete(@RequestBody NetworkEntity networkEntity) {
        logger.info("Network Entity payload for delete operation [" + networkEntity + "]");
        return networkManagementUDService.delete(networkEntity);
    }

    @PutMapping("/update")
    public GenricResponse update(@RequestBody NetworkEntity networkEntity) {
        logger.info("updateProvince request :  " + networkEntity);
        return networkManagementUDService.update(networkEntity);
    }

    @PostMapping("/add-network")
    public ResponseEntity<?> addLink(@RequestBody NetworkEntity networkEntity) {
        NetworkEntity linkResponse =networkManagementUDService.saveLink(networkEntity);
        logger.info("Added new link: {}", networkEntity);
        return new ResponseEntity<>(linkResponse, HttpStatus.CREATED);
    }


}
