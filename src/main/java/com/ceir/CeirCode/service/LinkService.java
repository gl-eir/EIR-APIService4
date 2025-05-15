package com.ceir.CeirCode.service;

import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.SpecificationBuilder.GenericSpecificationBuilder;
import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.filemodel.LinkModel;
import com.ceir.CeirCode.filtermodel.LinkMgmtFilter;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.LinkDetails;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.app.LinkRepository;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ceir.CeirCode.configuration.SortDirection.getSortDirection;



@Service
@Transactional
public class LinkService {

	private static final Logger logger = LoggerFactory.getLogger(LinkService.class);

	@Autowired
	private LinkRepository linkRepository;

	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;

	public LinkDetails saveLink(LinkDetails link) {
		logger.info("Saved link: {}", link);
		return linkRepository.save(link);
	}

	public void saveLinkState(List<LinkDetails> linkDetailsList) {

		for (LinkDetails linkDetails : linkDetailsList) {
			// Validate linkDetails before saving
			if (linkDetails.getLinkState() == null) {
				linkDetails.setStatus("disabled");
				linkRepository.updateStatus(linkDetails);
			}
			else{
				linkDetails.setStatus("enabled");
				linkRepository.update(linkDetails);
			}


		}
		Set<String> providedLinkIds = linkDetailsList.stream()
				.map(LinkDetails::getLinkId)
				.collect(Collectors.toSet());

		// Fetch all link IDs from the database
		List<LinkDetails> allLinks = linkRepository.findAll();
		for (LinkDetails link : allLinks) {
			if (!providedLinkIds.contains(link.getLinkId())) {
				link.setStatus("disabled");
				linkRepository.updateStatus(link);
			}
		}

	}

	public List<LinkDetails> getAllLinks() {
		logger.info("Fetching all links.");
		return linkRepository.findAll();
	}

	public LinkDetails startLink(String linkId) {
		LinkDetails link = linkRepository.findById(linkId)
				.orElseThrow(() -> new EntityNotFoundException("Link not found with ID: " + linkId));
		link.setStatus("enabled");
		link.setModifiedOn(LocalDateTime.now());
		return  linkRepository.save(link);

	}

	public LinkDetails stopLink(String linkId) {
		LinkDetails link = linkRepository.findById(linkId)
				.orElseThrow(() -> new EntityNotFoundException("Link not found with ID: " + linkId));
		link.setStatus("disabled");
		link.setModifiedOn(LocalDateTime.now());
		logger.info("Stopped link with ID: {}", linkId);
		return linkRepository.save(link);

	}


	public List<LinkDetails> filterLinks(Specification<LinkDetails> spec) {
		logger.info("Filtering links.");
		return linkRepository.findAll(spec);
	}

	public List<LinkDetails> checkAllLinks() {
		logger.info("Fetching links with 'enabled' status.");
		return linkRepository.findByStatus("enabled");
	}
	public Page<LinkDetails> viewAllRecord(LinkMgmtFilter details, Integer pageNo, Integer pageSize, String operation) {
		try {
			logger.info("filter data:  " + details);
			// RequestHeaders header=new
			// RequestHeaders(details.getUserAgent(),details.getPublicIp(),details.getUsername());
			// headerService.saveRequestHeader(header);
			String orderColumn;
			if(operation.equals("Export")) {
				orderColumn = "modifiedOn";
			}else {
				logger.info("column Name :: " + details.getOrderColumnName());
				orderColumn = "Created On".equalsIgnoreCase(details.getOrderColumnName()) ? "createdOn" :
						"Modified On".equalsIgnoreCase(details.getOrderColumnName()) ? "modifiedOn" :
								"Link ID".equalsIgnoreCase(details.getOrderColumnName()) ? "linkId" :
										"Status".equalsIgnoreCase(details.getOrderColumnName()) ? "status" :
												 "modifiedOn";
			}
			Sort.Direction direction = getSortDirection(details.getOrder() == null ? "desc" : details.getOrder());

			logger.info("orderColumn Name is : "+orderColumn+ "  -------------  direction is : "+direction);

			Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));


			Page<LinkDetails> page = linkRepository.findAll(buildSpecification(details).build(), pageable);

			String operationType = "view".equalsIgnoreCase(operation) ? SubFeatures.VIEW_ALL : SubFeatures.EXPORT;
//			userService.saveUserTrail(details.getUserId(), details.getUsername(), details.getUserType(),
//       details.getUserTypeId(), Features.User_Management, operationType, details.getFeatureId(),details.getPublicIp(),details.getBrowser());
			return page;

		} catch (Exception e) {
			logger.info("Exception found =" + e.getMessage());
			logger.info(e.toString());
			return null;

		}
	}
	private GenericSpecificationBuilder<LinkDetails> buildSpecification(LinkMgmtFilter filterRequest) {

		GenericSpecificationBuilder<LinkDetails> uPSB = new GenericSpecificationBuilder<LinkDetails>(propertiesReader.dialect);
		if (Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate() != "")
			uPSB.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));
		if (Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate() != "")
			uPSB.with(new SearchCriteria("modifiedOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));
//		if (Objects.nonNull(filterRequest.getUsertypeId()) && filterRequest.getUsertypeId() != -1)
//			uPSB.with(new SearchCriteria("usertype", filterRequest.getUsertypeId(), SearchOperation.EQUALITY,Datatype.INT));

		/*
		 * if(Objects.nonNull(filterRequest.getEmail()) && filterRequest.getEmail()!=
		 * "") { log.info("1::::::::"+filterRequest.getEmail());
		 * uPSB.joinWithUserProfile(new SearchCriteria("email",filterRequest.getEmail(),
		 * SearchOperation.EQUALITY, Datatype.STRING)); }
		 * if(Objects.nonNull(filterRequest.getPhoneNo()) &&
		 * filterRequest.getPhoneNo()!= "") {
		 * log.info("2::::::::"+filterRequest.getPhoneNo());
		 * uPSB.joinWithUserProfile(new
		 * SearchCriteria("phoneNo",filterRequest.getPhoneNo(),
		 * SearchOperation.EQUALITY, Datatype.STRING)); }
		 */

		if(Objects.nonNull(filterRequest.getLinkId()) && filterRequest.getLinkId()!=""){
			logger.info("3::::::::"+filterRequest.getLinkId());
			uPSB.with(new SearchCriteria("linkId", filterRequest.getLinkId(),SearchOperation.LIKE, Datatype.STRING));
		}
		if(Objects.nonNull(filterRequest.getLinkStatus()) && filterRequest.getLinkStatus()!= "") {
			logger.info("4::::::::"+filterRequest.getLinkStatus());
			uPSB.with(new SearchCriteria("status", filterRequest.getLinkStatus(),SearchOperation.LIKE, Datatype.STRING));
		}



//		if (Objects.nonNull(filterRequest.getFilteredUsername()) && filterRequest.getFilteredUsername() != "")
//			uPSB.with(new SearchCriteria("username", filterRequest.getFilteredUsername(), SearchOperation.LIKE,
//					Datatype.STRING));
//


		//uPSB.with(new SearchCriteria("currentStatus", 3, SearchOperation.EQUALITY, Datatype.INT));


		/*
		 * uPSB.addSpecification( uPSB.joinWithUserType(new
		 * SearchCriteria("selfRegister", 0, SearchOperation.EQUALITY, Datatype.INT)));
		 */

//		uPSB.addSpecification(
//				uPSB.joinWithUserType(new SearchCriteria("selfRegister", 0, SearchOperation.EQUALITY, Datatype.INT)));
//


		// uPSB.addSpecification(uPSB.joinWithUserProfile());

		if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
			logger.info("filterRequest.getSearchString()::::::::" + filterRequest.getSearchString());
			uPSB.orSearch(new SearchCriteria("linkId", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			uPSB.orSearch(new SearchCriteria("status", filterRequest.getSearchString(),
					SearchOperation.LIKE, Datatype.STRING));
			uPSB.orSearch(new SearchCriteria("createdOn", filterRequest.getSearchString(), SearchOperation.EQUALITY,
					Datatype.DATE));
			uPSB.orSearch(new SearchCriteria("modifiedOn", filterRequest.getSearchString(), SearchOperation.EQUALITY,
					Datatype.DATE));

		}

		return uPSB;

	}


	public FileDetails getFile(LinkMgmtFilter filter) {
		// TODO Auto-generated method stub

		logger.info("inside export link management service");
		logger.info("filter data:  " + filter);
		String fileName = null;
		Writer writer = null;
		LinkModel uPFm = null;
		SystemConfigurationDb dowlonadDir = systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink = systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Integer pageNo = 0;
		Integer pageSize = Integer
				.valueOf(systemConfigurationDbRepoImpl.getDataByTag("file.max-file-record").getValue());
		String filePath = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<LinkModel> builder = null;
		StatefulBeanToCsv<LinkModel> csvWriter = null;
		List<LinkModel> fileRecords = null;
		MappingStrategy<LinkModel> mapStrategy = new CustomMappingStrategy<>();

		try {

			mapStrategy.setType(LinkModel.class);
			List<LinkDetails> list = viewAllRecord(filter, pageNo, pageSize, "Export").getContent();

			if (list.size() > 0) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_") + "_LinkManagement.csv";
			} else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_") + "_LinkManagement.csv";
			}
			logger.info(" file path plus file name: " + Paths.get(filePath + fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//
			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if (list.size() > 0) {
				// List<SystemConfigListDb> systemConfigListDbs =
				// configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<LinkModel>();
				for (LinkDetails linkDetails : list) {
					uPFm = new LinkModel();
					uPFm.setCreatedOn(linkDetails.getCreatedOn().format(String.valueOf(dtf)));
					uPFm.setModifiedOn(linkDetails.getModifiedOn().format(String.valueOf(dtf)));
					uPFm.setLinkId(linkDetails.getLinkId());
					uPFm.setStatus(linkDetails.getStatus());

					fileRecords.add(uPFm);
				}
				csvWriter.write(fileRecords);
			}
			logger.info("fileName::" + fileName);
			logger.info("filePath::::" + filePath);
			logger.info("link:::" + dowlonadLink.getValue());
			return new FileDetails(fileName, filePath,
					dowlonadLink.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		} finally {
			try {

				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

	}

	public GenricResponse deleteById(LinkDetails link) {
		try {
			// Fetch the link by linkId, throw exception if not found
			LinkDetails existingLink = linkRepository.findById(link.getLinkId())
					.orElseThrow(() -> new EntityNotFoundException("Link not found with ID: " + link.getLinkId()));

			// Proceed to delete the link
			linkRepository.delete(existingLink);

			logger.info("Successfully deleted link with ID: {}", link.getLinkId());
			return new GenricResponse(200,"Link deleted successfully", "");

		} catch (EntityNotFoundException e) {
			logger.error("Error: {}", e.getMessage());
			return new GenricResponse(201,"Error: Link not found with ID: " + link.getLinkId(), "");

		} catch (Exception e) {
			logger.error("Error occurred while deleting link with ID: {}. Message: {}", link.getLinkId(), e.getMessage());
			return new GenricResponse(500,"Error occurred while deleting link. Please try again.", "");
		}
	}
}
