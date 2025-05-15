package com.ceir.CeirCode.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.model.app.AddressObject;
import com.ceir.CeirCode.model.app.Commune;
import com.ceir.CeirCode.model.app.District;
import com.ceir.CeirCode.model.app.FileDetails;
import com.ceir.CeirCode.model.app.FilterRequest;
import com.ceir.CeirCode.model.app.Locality;
import com.ceir.CeirCode.model.app.Province;
import com.ceir.CeirCode.model.app.Village;
import com.ceir.CeirCode.model.audit.AuditTrail;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.repo.audit.AuditTrailRepo;
import com.ceir.CeirCode.repo.app.CommuneRepo;
import com.ceir.CeirCode.repo.app.DistrictRepo;
import com.ceir.CeirCode.repo.app.LocalityRepo;
import com.ceir.CeirCode.repo.app.ProvinceRepo;
import com.ceir.CeirCode.repo.app.VillageRepo;
import com.ceir.CeirCode.response.tags.RegistrationTags;
import com.ceir.CeirCode.service.LocalityService;
import com.ceir.CeirCode.service.UserService;
import com.ceir.CeirCode.util.HttpResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
public class AddressManagementController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	DistrictRepo districtRepo;
	@Autowired
	CommuneRepo communeRepo;
	@Autowired
	VillageRepo villageRepo;
	@Autowired
	ProvinceRepo provinceRepo;
	@Autowired
	LocalityRepo localityRepo;
	@Autowired
	LocalityService localityService;
	@Autowired
	AuditTrailRepo auditTrailRepo;
	@Autowired
	UserService userService;
	

	/*************************** get Records ***************/

	@ApiOperation(value = "Locality data.")
	@PostMapping("/viewAllLocality")
	public MappingJacksonValue viewRecord(@RequestBody AddressObject filterRequest,
			@RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0",required = false) Integer file) {
		MappingJacksonValue mapping = null;
		long localityID = filterRequest.getLocalityId();
		if (file == 0) {
			if (localityID > 0) {
				log.info("findById: " + localityID);
				mapping = new MappingJacksonValue(localityService.viewAll(filterRequest, pageNo, pageSize,true).getContent()
						.stream().filter(localityObject -> localityObject.getId() == localityID).findFirst());
			} else {
				Page<Locality> response = localityService.viewAll(filterRequest, pageNo, pageSize,false);
				mapping = new MappingJacksonValue(response);
			}
		} else {
			FileDetails fileDetails = localityService.getFile(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}
		return mapping;
	} 

	/************************** SAVE ************************/

	@ApiOperation(value = "saveProvince")
	@PostMapping("/saveProvince")
	public ResponseEntity<?> saveProvince(@RequestBody Province province) {
		HttpResponse response;
		try {
			Boolean isExistInDB = provinceRepo.existsByCountryAndProvince(province.getCountry(),
					province.getProvince());
			if (isExistInDB) {
				response = new HttpResponse(RegistrationTags.exist.getMessage(), 409, RegistrationTags.exist.getTag());

			} else {
				userService.saveUserTrail(province.getUserId(),province.getUsername(),
						province.getUserType(),province.getUserTypeId(),Features.Address_Management,SubFeatures.SAVE,province.getFeatureId(),province.getPublicIp(),province.getBrowser());
				provinceRepo.save(province);
				response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
						RegistrationTags.Success_Save.getTag());

			}
		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "saveDistrict")
	@PostMapping("/saveDistrict")
	public ResponseEntity<?> saveDistrict(@RequestBody District district) {
		HttpResponse response;
		try {
			Boolean isExistInDB = districtRepo.existsByProvinceAndDistrict(district.getProvince(),
					district.getDistrict());
			if (isExistInDB) {
				response = new HttpResponse(RegistrationTags.exist.getMessage(), 409, RegistrationTags.exist.getTag());

			} else {
				userService.saveUserTrail(district.getUserId(),district.getUsername(),
						district.getUserType(),district.getUserTypeId(),Features.Address_Management,SubFeatures.SAVE,district.getFeatureId(),district.getPublicIp(),district.getBrowser());
				districtRepo.save(district);
				response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
						RegistrationTags.Success_Save.getTag());
				// audit(auditTrail);

			}
		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "saveCommune")
	@PostMapping("/saveCommune")
	public ResponseEntity<?> saveCommune(@RequestBody Commune commune) {
		HttpResponse response;
		try {
			Boolean isExistInDB = communeRepo.existsByDistrictIDAndCommune(commune.getDistrictID(),
					commune.getCommune());
			if (isExistInDB) {
				response = new HttpResponse(RegistrationTags.exist.getMessage(), 409, RegistrationTags.exist.getTag());

			} else {
				userService.saveUserTrail(commune.getUserId(),commune.getUsername(),
						commune.getUserType(),commune.getUserTypeId(),Features.Address_Management,SubFeatures.SAVE,commune.getFeatureId(),commune.getPublicIp(),commune.getBrowser());
				communeRepo.save(commune);
				response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
						RegistrationTags.Success_Save.getTag());

			}
		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "saveVillage")
	@PostMapping("/saveVillage")
	public ResponseEntity<?> saveVillage(@RequestBody Village village) {
		HttpResponse response;
		try {
			Boolean isExistInDB = villageRepo.existsByCommuneIDAndVillage(village.getCommuneID(), village.getVillage());
			if (isExistInDB) {
				response = new HttpResponse(RegistrationTags.exist.getMessage(), 409, RegistrationTags.exist.getTag());

			} else {
				userService.saveUserTrail(village.getUserId(),village.getUsername(),
						village.getUserType(),village.getUserTypeId(),Features.Address_Management,SubFeatures.SAVE,village.getFeatureId(),village.getPublicIp(),village.getBrowser());
				villageRepo.save(village);
				Locality locality = new Locality();
				Optional<Commune> communeDetail = communeRepo.findById(village.getCommuneID());
				if (communeDetail.isPresent()) {
					Commune getCommuneDetail = communeDetail.get();
					locality.setCommune(getCommuneDetail.getCommune());

					Optional<District> districtDetail = districtRepo.findById(getCommuneDetail.getDistrictID());
					if (districtDetail.isPresent()) {
						District getDistrictDetail = districtDetail.get();
						locality.setDistrict(getDistrictDetail.getDistrict());

						Province provinceDetail = provinceRepo.findByProvince(getDistrictDetail.getProvince());
						locality.setCountry(provinceDetail.getCountry());
						locality.setProvince(provinceDetail.getProvince());
						locality.setVillage(village.getVillage());
						localityRepo.save(locality);

						log.info("successfully save village : +" + village.getVillage() + "+  in Master table");

					}
				}
				response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
						RegistrationTags.Success_Save.getTag());

			}
		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/************************** Dropdown ******************/

	@ApiOperation(value = "getDistrict")
	@PostMapping("/getDistrict")
	public ResponseEntity<?> getDistrictList(@RequestBody AddressObject addressObject) {
		List<District> sortList = districtRepo.findByProvince(addressObject.getProvince());
		sortList.sort((u1, u2) -> u1.getDistrict().compareTo(u2.getDistrict()));
		Optional<List<District>> list = Optional.ofNullable(sortList);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "getCommune")
	@PostMapping("/getCommune")
	public ResponseEntity<?> getCommuneList(@RequestBody AddressObject addressObject) {
		List<Commune> sortList = communeRepo.findByDistrictID(addressObject.getDistrictID());
		sortList.sort((u1, u2) -> u1.getCommune().compareTo(u2.getCommune()));
		Optional<List<Commune>> list = Optional.ofNullable(sortList);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "getVillage")
	@PostMapping("/getVillage")
	public ResponseEntity<?> getVillageList(@RequestBody AddressObject addressObject) {
		List<Village> sortList = villageRepo.findByCommuneID(addressObject.getCommuneID());
		sortList.sort((u1, u2) -> u1.getVillage().compareTo(u2.getVillage()));
		Optional<List<Village>> list = Optional.ofNullable(sortList);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "getProvince")
	@GetMapping("/getProvince")
	public ResponseEntity<?> getProvinceList(
			@RequestParam(name = "country", required = false, defaultValue = "Cambodia") String country) {
		List<Province> sortList = provinceRepo.findByCountry(country);
		sortList.sort((u1, u2) -> u1.getProvince().compareTo(u2.getProvince()));
		Optional<List<Province>> list = Optional.ofNullable(sortList);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/******************** UPDATE API ************/

	@ApiOperation(value = "updateProvince")
	@PutMapping("/updateProvince")
	public ResponseEntity<?> updateProvince(@RequestBody Province province) {
		log.info("updateProvince request :  " + province);
		HttpResponse response = null;
		HttpStatus status = HttpStatus.OK;
		try {
			int pro = provinceRepo.updateProvinceName(province.getUpdatingProvinceName(), province.getProvince());
			log.info("province : " + pro);
			log.info("province_db updated with new province ");

			/*
			 * Boolean isExistInDB =
			 * provinceRepo.existsByCountryAndProvince(province.getCountry(),
			 * province.getUpdatingProvinceName()); if (isExistInDB) {
			 */
			Optional<Locality> localityDetail = localityRepo.findById(province.getId());
			Locality local = localityDetail.get();
			log.info(local + "------updated province in district_db with " + province.getUpdatingProvinceName());
			districtRepo.updateProvinceInDistrict(province.getUpdatingProvinceName(), local.getDistrict(),
					local.getProvince());
			userService.saveUserTrail(province.getUserId(),province.getUsername(),
					province.getUserType(),province.getUserTypeId(),Features.Address_Management,SubFeatures.UPDATE,province.getFeatureId(),province.getPublicIp(),province.getBrowser());
			log.info("------updated localityDB with Modified By "+ province.getUsername());
			//local.setModifiedBy(province.getUsername());
			localityRepo.updateProvince(province.getUpdatingProvinceName(), local.getProvince(),province.getUsername());
			response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
					RegistrationTags.Success_Save.getTag());
			log.info("locality_db updated with new province ");
//TODO update locality_db also 
			/*
			 * } else {
			 * 
			 * response = new HttpResponse(RegistrationTags.exist.getMessage(), 404,
			 * RegistrationTags.exist.getTag()); status = HttpStatus.EXPECTATION_FAILED; }
			 */

		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, status);
	}

	@ApiOperation(value = "updateDistrict")
	@PutMapping("/updateDistrict")
	public ResponseEntity<?> updateDistrict(@RequestBody District district) {
		log.info("district request :  " + district);
		HttpResponse response = null;
		HttpStatus status = HttpStatus.OK;
		try {
			districtRepo.updateDistrictName(district.getCurrentDistrictName(), district.getDistrict(),
					district.getProvince());

			log.info("district_db updated with new district ");
			/*
			 * Boolean isExistInDB =
			 * districtRepo.existsByProvinceAndDistrict(district.getProvince(),
			 * district.getCurrentDistrictName()); if (isExistInDB) {
			 */
			userService.saveUserTrail(district.getUserId(),district.getUsername(),
					district.getUserType(),district.getUserTypeId(),Features.Address_Management,SubFeatures.UPDATE,district.getFeatureId(),district.getPublicIp(),district.getBrowser());
			log.info("------updated localityDB with Modified By "+ district.getUsername());
			localityRepo.updateDistrict(district.getCurrentDistrictName(), district.getDistrict(),
					district.getProvince(),district.getUsername());
			response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
					RegistrationTags.Success_Save.getTag());
			log.info("locality_db updated with new district ");

			/*
			 * } else {
			 * 
			 * log.info("not exist in DB "); response = new
			 * HttpResponse(RegistrationTags.exist.getMessage(), 404,
			 * RegistrationTags.exist.getTag()); status = HttpStatus.EXPECTATION_FAILED; }
			 */

		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, status);

	}

	@ApiOperation(value = "updateCommune")
	@PutMapping("/updateCommune")
	public ResponseEntity<?> updateCommune(@RequestBody Commune commune) {
		log.info("commune request :  " + commune);
		HttpResponse response = null;

		HttpStatus status = HttpStatus.OK;

		try {
			List<District> districtObject = districtRepo.findByDistrictAndProvince(commune.getDistrictName(),
					commune.getProvince());
			for (District d : districtObject) {
				long districtID = d.getId();
				
				Commune request = communeRepo.findByDistrictIDAndCommune(districtID,commune.getCommune());
				log.info("request : "+request);
				request.setCommune(commune.getCurrentCommuneName());
				communeRepo.save(request);
				//communeRepo.updateCommuneName(commune.getCurrentCommuneName(), districtID, commune.getCommune());
				
				log.info("commune_db updated with new commune ");

				/*
				 * Boolean isExistInDB = communeRepo.existsByDistrictIDAndCommune(districtID,
				 * commune.getCurrentCommuneName()); if (isExistInDB) {
				 */
				userService.saveUserTrail(commune.getUserId(),commune.getUsername(),
						commune.getUserType(),commune.getUserTypeId(),Features.Address_Management,SubFeatures.UPDATE,commune.getFeatureId(),commune.getPublicIp(),commune.getBrowser());
				log.info("------updated localityDB with Modified By "+ commune.getUsername());
				localityRepo.updateCommune(commune.getCurrentCommuneName(), commune.getDistrictName(),
						commune.getCommune(), commune.getProvince(),commune.getUsername());
				response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
						RegistrationTags.Success_Save.getTag());
				log.info("locality_db updated with new commune ");
				/*
				 * } else { response = new HttpResponse(RegistrationTags.exist.getMessage(),
				 * 404, RegistrationTags.exist.getTag()); status =
				 * HttpStatus.EXPECTATION_FAILED; }
				 */

			}

		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, status);

	}

	@ApiOperation(value = "updateVillage")
	@PutMapping("/updateVillage")
	public ResponseEntity<?> updateVillage(@RequestBody Village village) {
		log.info("village request :  " + village);
		HttpResponse response = null;

		HttpStatus status = HttpStatus.OK;

		try {

			Commune com = communeRepo.findByCommune(village.getCommune());
			long communeID = com.getId();
			Village request = villageRepo.findByVillageAndCommuneID(village.getVillage(), communeID);
			log.info("request :"+request);
			request.setVillage(village.getCurrentVillage());
			//villageRepo.updateVillageName(village.getCurrentVillage(), communeID, village.getVillage());
			villageRepo.save(request);
			log.info("village_db updated with new village ");

			/*
			 * Boolean isExistInDB = communeRepo.existsByDistrictIDAndCommune(communeID,
			 * village.getVillage()); if (isExistInDB) {
			 */
			userService.saveUserTrail(village.getUserId(),village.getUsername(),
					village.getUserType(),village.getUserTypeId(),Features.Address_Management,SubFeatures.UPDATE,village.getFeatureId(),village.getPublicIp(),village.getBrowser());
			log.info("------ updated localityDB with Modified By "+ village.getUsername());
			localityRepo.updateVillage(village.getCurrentVillage(), village.getDistrictName(), village.getCommune(),
					village.getId(),village.getUsername());
			response = new HttpResponse(RegistrationTags.Success_Save.getMessage(), 200,
					RegistrationTags.Success_Save.getTag());
			log.info("locality_db updated with new village");
			/*
			 * } else {
			 * 
			 * response = new HttpResponse(RegistrationTags.exist.getMessage(), 404,
			 * RegistrationTags.exist.getTag()); status = HttpStatus.EXPECTATION_FAILED; }
			 */

		} catch (Exception e) {
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, status);

	}

	/******************** Delete Row *******************/
	@Transactional
	@ApiOperation(value = "deleteRow")
	@DeleteMapping("/deleteRow")
	public ResponseEntity<?> deleteRow(@RequestBody FilterRequest filterRequest) {
		log.info("for delete request :  " + filterRequest);
		HttpResponse response;
		try {

			Optional<Locality> localityDetail = localityRepo.findById(filterRequest.getId());
			if (localityDetail.isPresent()) {
				Locality locality = localityDetail.get();

				List<Locality> villageList = localityRepo.findByProvinceAndDistrictAndCommuneAndVillage(
						locality.getProvince(), locality.getDistrict(), locality.getCommune(), locality.getVillage());

				if (villageList.size() < 2) {
					villageRepo.deleteByVillage(locality.getVillage());
					log.info("Village Successfully deleted from village_db");
				}
				List<Locality> communeList = localityRepo.findByProvinceAndDistrictAndCommune(locality.getProvince(),
						locality.getDistrict(), locality.getCommune());
				if (communeList.size() < 2) {
					/*
					 * List<District> findDistrictID =
					 * districtRepo.findByDistrictAndProvince(locality.getDistrict(),
					 * locality.getProvince()); for (District d : findDistrictID) {
					 * 
					 * if (communeRepo.findByDistrictID(d.getId()).size() < 2) {
					 */
					communeRepo.deleteByCommune(locality.getCommune());
					log.info("Commune Successfully deleted from commune_db");

					List<Locality> districtList = localityRepo.findByProvinceAndDistrict(locality.getProvince(),
							locality.getDistrict());
					if (districtList.size() < 2) {
						districtRepo.deleteByDistrict(locality.getDistrict());
						log.info("District Successfully deleted from district_db");
					}

					List<Locality> provinceList = localityRepo.findByCountryAndProvince(locality.getCountry(),
							locality.getProvince());
					if (provinceList.size() < 2) {
						provinceRepo.deleteByProvince(locality.getProvince());
						log.info("Province Successfully deleted from province_db");
					}

					/*
					 * } }
					 */
				}
				
			}
			userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername(),
					filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.Address_Management,SubFeatures.DELETE,filterRequest.getFeatureId(),filterRequest.getPublicIp(),filterRequest.getBrowser());
			log.info("Address ID in locality DB to delete "+filterRequest.getId());
			localityRepo.deleteById(filterRequest.getId());
			response = new HttpResponse(RegistrationTags.delete_successfully.getMessage(), 200,
					RegistrationTags.delete_successfully.getTag());
			
		} catch (Exception e) {
			log.info("exception" + e.toString());
			response = new HttpResponse(RegistrationTags.Exception.getMessage(), 500,
					RegistrationTags.Exception.getTag());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public void audit(AuditTrail auditTrail) {
		/*
		 * AuditTrail auditTrail = new AuditTrail(userId, username, userTypeId,
		 * userType, featureId, feature, subFeature, "0", "NA", userType);
		 */
		auditTrailRepo.save(auditTrail);
	}
}
