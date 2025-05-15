package com.ceir.CeirCode.controller;

import com.ceir.CeirCode.LinkSpecification.CSVHelper;
import com.ceir.CeirCode.LinkSpecification.LinkSpecifications;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filtermodel.LinkMgmtFilter;
import com.ceir.CeirCode.filtermodel.UserMgmtFilter;

import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.LinkDetails;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.repo.app.LinkRepository;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/eir")
public class LinkController {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkRepository linkRepository;



    @PostMapping("/saveLinkState")
    public ResponseEntity<?> saveLinkState(@RequestBody List<LinkDetails> linkDetailsList) {
        try {
            linkService.saveLinkState(linkDetailsList);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error saving link state", e);
            return new ResponseEntity<>("Error saving link state", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Get all data
    @GetMapping("/get-all")
    public List<LinkDetails> getAll() {
        logger.info("Fetching all link details.");
        return linkService.getAllLinks();
    }

    @PostMapping("/view")
    public MappingJacksonValue view(@RequestBody LinkMgmtFilter filter,
                                    @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                    @RequestParam(value = "file", defaultValue = "0") Integer file){
        MappingJacksonValue mapping = null;
        if(file == 0) {
            Page<LinkDetails> userData  = linkService.viewAllRecord(filter, pageNo, pageSize,"view");
            mapping = new MappingJacksonValue(userData);
        }
        else {
            FileDetails fileDetails = linkService.getFile(filter);
            mapping = new MappingJacksonValue(fileDetails);
        }
        return mapping;
    }


    // Add link
    @PostMapping("/add-link")
    public ResponseEntity<?> addLink(@RequestBody LinkDetails link) {
        List<LinkDetails> allLinks = linkRepository.findAll();
        for (LinkDetails linkDetails : allLinks) {
            if (linkDetails.getLinkId().equals(link.getLinkId())) {
                return ResponseEntity.badRequest().body("Link ID already exists.");
            }
        }
        link.setCreatedOn(LocalDateTime.now());
        link.setModifiedOn(LocalDateTime.now());
        LinkDetails linkResponse =linkService.saveLink(link);
        logger.info("Added new link: {}", link);
        return new ResponseEntity<>(linkResponse, HttpStatus.CREATED);
    }

    // Check all for 'enable' status only
    @PostMapping("/current-status")
    public ResponseEntity<List<LinkDetails>> checkAllLinks() {
        try {
            logger.info("Fetching links with 'enable' status.");
            List<LinkDetails> links = linkService.checkAllLinks();
            logger.info("Link is {}" ,links);
            return new ResponseEntity<>(links, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching link statuses", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/connect/{linkId}")
    public ResponseEntity<?> startLink(@PathVariable String linkId) {
        logger.info("Starting link with ID: {}", linkId);

        return ResponseEntity.ok(linkService.startLink(linkId));
    }

    @PostMapping("/shut-down/{linkId}")
    public ResponseEntity<?> stopLink(@PathVariable String linkId) {
        logger.info("Stopping link with ID: {}", linkId);

        return ResponseEntity.ok(linkService.stopLink(linkId));
    }


    // Filter
    @PostMapping("/filter")
    public List<LinkDetails> filterLinks(@RequestParam(required = false) String status,
                                         @RequestParam(required = false) String linkId,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate) {
        Specification<LinkDetails> spec = Specification.where(null);
        if (status != null) {
            spec = spec.and(LinkSpecifications.hasStatus(status));
            logger.debug("Filtering links by status: {}", status);
        }
        if (linkId != null) {
            spec = spec.and(LinkSpecifications.hasLinkId(linkId));
            logger.debug("Filtering links by linkId: {}", linkId);
        }
        if (startDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
            spec = spec.and(LinkSpecifications.createdAfter(start));
            logger.debug("Filtering links created after: {}", startDate);
        }
        if (endDate != null) {
            LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);
            spec = spec.and(LinkSpecifications.createdBefore(end));
            logger.debug("Filtering links created before: {}", endDate);
        }
        logger.info("Filtering links with provided criteria.");
        return linkService.filterLinks(spec);
    }

    // Reset
    @GetMapping("/reset")
    public List<LinkDetails> resetFilters() {
        logger.info("Resetting filters and fetching all links.");
        return linkService.getAllLinks();
    }

    // Export
    @GetMapping("/export")
    public ResponseEntity<Resource> exportLinks() {
        List<LinkDetails> links = linkService.getAllLinks();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);
        CSVHelper.writeLinksToCsv(links, writer);
        writer.flush();
        InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        logger.info("Exporting links to CSV.");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=links.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @DeleteMapping("/delete/{linkId}")
    public ResponseEntity<GenricResponse> deleteLink(@PathVariable String linkId) {
        LinkDetails linkDetails = new LinkDetails();
        linkDetails.setLinkId(linkId);
        GenricResponse response = linkService.deleteById(linkDetails);
        return ResponseEntity.ok(response);
    }
}
