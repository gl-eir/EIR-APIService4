package com.ceir.CeirCode.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.configuration.PropertiesReaders;
import com.ceir.CeirCode.model.app.AllRequest;
import com.ceir.CeirCode.model.app.ChangePassword;
import com.ceir.CeirCode.model.app.ChangeUserStatus;
import com.ceir.CeirCode.model.app.MessageConfigurationDb;
import com.ceir.CeirCode.model.app.Otp;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.model.app.QuestionPair;
import com.ceir.CeirCode.model.app.ResendOtp;
import com.ceir.CeirCode.model.app.RunningAlertDb;
import com.ceir.CeirCode.model.app.Securityquestion;
import com.ceir.CeirCode.model.app.SystemConfigListDb;
import com.ceir.CeirCode.model.app.SystemConfigurationDb;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserHeader;
import com.ceir.CeirCode.model.app.UserPasswordHistory;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.UserSecurityquestion;
import com.ceir.CeirCode.model.app.UserStatusRequest;

import com.ceir.CeirCode.model.app.Userrole;
import com.ceir.CeirCode.model.app.Usertype;
import com.ceir.CeirCode.model.audit.AuditDB;
import com.ceir.CeirCode.model.audit.AuditTrail;
import com.ceir.CeirCode.model.oam.RequestHeaders;
import com.ceir.CeirCode.model.constants.AlertStatus;
import com.ceir.CeirCode.model.constants.ChannelType;
import com.ceir.CeirCode.model.constants.Features;
import com.ceir.CeirCode.model.constants.SelfRegistration;
import com.ceir.CeirCode.model.constants.SubFeatures;
import com.ceir.CeirCode.model.constants.UserStatus;
import com.ceir.CeirCode.model.constants.UserTypeStatusFlag;
import com.ceir.CeirCode.model.constants.UsertypeData;
import com.ceir.CeirCode.othermodel.RolesData;
import com.ceir.CeirCode.repo.app.AlertDbRepo;
import com.ceir.CeirCode.repo.app.MessageConfigurationDbRepository;
import com.ceir.CeirCode.repo.app.NotificationRepository;
import com.ceir.CeirCode.repo.app.SecurityQuestionRepo;
import com.ceir.CeirCode.repo.app.SystemConfigDbListRepository;
import com.ceir.CeirCode.repo.app.UserPasswordHistoryRepo;
import com.ceir.CeirCode.repo.app.UserProfileRepo;
import com.ceir.CeirCode.repo.app.UserRepo;
import com.ceir.CeirCode.repo.app.UserRoleRepo;
import com.ceir.CeirCode.repo.app.UserSecurityQuestionRepo;

import com.ceir.CeirCode.repo.app.UsertypeRepo;
import com.ceir.CeirCode.repo.audit.AuditDBRepo;
import com.ceir.CeirCode.repoService.AudiTrailRepoService;
import com.ceir.CeirCode.repoService.PortAddressRepoService;
import com.ceir.CeirCode.repoService.ReqHeaderRepoService;
import com.ceir.CeirCode.repoService.RunningAlertRepoService;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.repoService.UserPassHistoryRepoService;
import com.ceir.CeirCode.repoService.UserRepoService;
import com.ceir.CeirCode.repoService.UserRoleRepoService;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.response.UpdateProfileResponse;
import com.ceir.CeirCode.response.tags.ProfileTags;
import com.ceir.CeirCode.response.tags.RegistrationTags;
import com.ceir.CeirCode.response.tags.UpdateUserStatusTags;
import com.ceir.CeirCode.response.tags.UserRoleTags;
import com.ceir.CeirCode.util.GenerateRandomDigits;
import com.ceir.CeirCode.util.HttpResponse;
import com.ceir.CeirCode.util.NotificationUtil;
import com.ceir.CeirCode.util.OtpResponse;
import com.ceir.CeirCode.util.Utility;
import com.google.common.primitives.Longs;

@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	UserRoleRepo userRoleRepo;
	@Autowired
	UserSecurityQuestionRepo userSecurityQuestionRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	UserProfileRepo userProfileRepo;
	@Autowired
	OtpService otpService;
	@Autowired
	UsertypeRepo usertypeRepo;
	@Autowired
	SecurityQuestionRepo securityQuestionRepo;
	@Autowired
	GenerateRandomDigits randomDigits;

	@Autowired
	SystemConfigDbListRepository systemConfigRepo;

	@Autowired
	SystemConfigDbRepoService systemConfigurationRepo;

	@Autowired
	NotificationUtil emailUtils;

	

	@Autowired
	NotificationRepository notificationRepo;

	@Autowired
	UserPassHistoryRepoService userPassHistoryRepoImpl;

	@Autowired
	UserPasswordHistoryRepo userPasswordHistoryRepo;

	@Autowired
	Utility utitlity;

	@Autowired
	AudiTrailRepoService audiTrailRepoService;

	@Autowired
	ReqHeaderRepoService headerService;

	@Autowired
	PortAddressRepoService portAddressRepoService;

	@Autowired
	UserRepoService userRepoService;

	@Autowired
	SystemConfigDbRepoService systemConfigurationDbRepoImpl;

	@Autowired
	RunningAlertRepoService alertDbRepo;

	@Autowired
	UserRoleRepoService userRoleRepoService;

	@Autowired
	MessageConfigurationDbRepository messageConfigurationDbRepository;

	@Autowired
	Utility utility;

	@Autowired
	PropertiesReaders propertiesReader;

	@Autowired
	AlertDbRepo alertDb;

	@Autowired
	AuditDBRepo auditDb;
	
	@Autowired
	PooledPBEStringEncryptor pooledPBEStringEncryptor;

	public ResponseEntity<?> getUsertypeData(int type) {
		try {

			log.info("type= " + type);
			List<Usertype> usertypeData = new ArrayList<Usertype>();
			if (type == 0) {
				// usertypeData=usertypeRepo.findBySelfRegister(SelfRegistration.INTERNAL.getCode());
				usertypeData = usertypeRepo.findBySelfRegisterAndStatus(SelfRegistration.INTERNAL.getCode(),
						SelfRegistration.Enable.getCode());
				usertypeData.sort((u1, u2) -> u1.getUsertypeName().compareTo(u2.getUsertypeName()));

			} else if (type == 1) {
				// usertypeData=usertypeRepo.findBySelfRegister(SelfRegistration.EXTERNAL.getCode());
				usertypeData = usertypeRepo.findBySelfRegisterAndStatus(SelfRegistration.EXTERNAL.getCode(),
						SelfRegistration.Enable.getCode());
				// log.info("usertypeData::::::::::::: "+usertypeData);

				usertypeData.sort((u1, u2) -> u1.getUsertypeName().compareTo(u2.getUsertypeName()));

			} else if (type == 2) {
				usertypeData = usertypeRepo.findAll();
				usertypeData.sort((u1, u2) -> u1.getUsertypeName().compareTo(u2.getUsertypeName()));
			} else if (type == 3) {
				usertypeData = usertypeRepo.findBySelfRegisterOrSelfRegisterAndStatus(
						SelfRegistration.EXTERNAL.getCode(), SelfRegistration.OTHER.getCode(),
						SelfRegistration.Enable.getCode());
				// usertypeData=usertypeRepo.findBySelfRegisterOrSelfRegister(SelfRegistration.EXTERNAL.getCode(),SelfRegistration.OTHER.getCode());
				// log.info("usertypeData::::::::::::: "+usertypeData);

				usertypeData.sort((u1, u2) -> u1.getUsertypeName().compareTo(u2.getUsertypeName()));

			}
			return new ResponseEntity<>(usertypeData, HttpStatus.OK);
		} catch (Exception e) {
			HttpResponse response = new HttpResponse();
			response.setResponse("Oop something wrong happened");
			response.setStatusCode(409);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getInternalUsertype() {
		try {

			List<Usertype> usertypeData = usertypeRepo.findBySelfRegister(SelfRegistration.INTERNAL.getCode());
			usertypeData.sort((u1, u2) -> u1.getUsertypeName().compareTo(u2.getUsertypeName()));
			return new ResponseEntity<>(usertypeData, HttpStatus.OK);
		} catch (Exception e) {
			HttpResponse response = new HttpResponse();
			response.setResponse("Oop something wrong happened");
			response.setStatusCode(409);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> usertypeIdByName(String usertype) {

		try {
			Usertype userType = usertypeRepo.findByUsertypeName(usertype);
			if (userType != null) {
				return new ResponseEntity<>(userType, HttpStatus.OK);
			} else {
				HttpResponse response = new HttpResponse();
				response.setResponse("wrong usertype");
				response.setStatusCode(204);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			HttpResponse response = new HttpResponse();
			response.setResponse("Oop something wrong happened");
			response.setStatusCode(409);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getSecurityQuestion(String username) {
		try {
			log.info("username:  " + username);
			User user = userRepo.findByUsername(username);
			if (user != null) {
				List<Securityquestion> securityQuestionList = new ArrayList<Securityquestion>();
				if (user.getUserSecurityquestion().isEmpty()) {
					GenricResponse response = new GenricResponse(409, RegistrationTags.No_Question_Mapped.getTag(), "",
							null);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				if (user.getUserSecurityquestion().isEmpty() == false) {
					for (UserSecurityquestion securityQues : user.getUserSecurityquestion()) {
						Securityquestion ques = new Securityquestion(securityQues.getSecurityQuestion().getId(),
								securityQues.getSecurityQuestion().getQuestion(),
								securityQues.getSecurityQuestion().getCategory());
						securityQuestionList.add(ques);
					}
				}
				GenricResponse response = new GenricResponse(200, "data is found", "", securityQuestionList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				GenricResponse response = new GenricResponse(409, RegistrationTags.REG_WRONG_USER_ID.getTag(),
						RegistrationTags.REG_WRONG_USER_ID.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			GenricResponse response = new GenricResponse(409, RegistrationTags.COMMAN_FAIL_MSG.getTag(),
					RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getSecurityQuestion2() {

		try {
			List<Securityquestion> securityQuestionList = securityQuestionRepo.findAll();
			return new ResponseEntity<>(securityQuestionList, HttpStatus.OK);

		} catch (Exception e) {
			HttpResponse response = new HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 409,
					RegistrationTags.COMMAN_FAIL_MSG.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public int saveRoles(long usertypes[], long userid) {
		try {
			for (long usertypeId : usertypes) {
				Userrole role = new Userrole();
				User user = new User();
				user.setId(userid);
				role.setUserData(user);
				Usertype userType = new Usertype();
				userType.setId(usertypeId);
				role.setUsertypeData(userType);
				Userrole roleOutput = userRoleRepo.save(role);
				log.info("save roles output :  " + roleOutput);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int updateRoles(long usertypes[], long userid) {
		try {
			log.info("going to update user roles");
			for (long usertypeId : usertypes) {
				Userrole role = new Userrole();
				User user = new User();
				user.setId(userid);
				role.setUserData(user);
				Usertype userType = new Usertype();
				userType.setId(usertypeId);
				role.setUsertypeData(userType);
				Userrole roles = userRoleRepo.findByUserData_IdAndUsertypeData_Id(userid, usertypeId);
				log.info("user roles by userid and user type id :  " + roles);
				if (roles != null) {
					role.setId(roles.getId());
				}
				Userrole roleOutput = userRoleRepo.save(role);
				log.info("save roles output :  " + roleOutput);
			}
			return 1;
		} catch (Exception e) {
			log.info("excpetion occur in updating user role");
			e.printStackTrace();
			return 0;
		}
	}

	public long roleCheck(long usertypes[]) {
		try {
			log.info("inside add roles");
			log.info("usertypes= " + Arrays.toString(usertypes));
			log.info("usertypes length is" + usertypes.length);
			if (usertypes.length == 1) {
				long usertypeId = 0;
				for (long id : usertypes) {
					usertypeId = id;
				}
				return usertypeId;
			} else if (usertypes.length > 1) {
				Arrays.sort(usertypes);
				long usertypeArray1[] = { 4, 5, 6 };
				long usertypeArray2[] = { 4, 5 };
				long usertypeArray3[] = { 4, 6 };
				long usertypeArray4[] = { 5, 6 };
				Arrays.sort(usertypeArray3);
				log.info("usertype array1= " + Arrays.toString(usertypeArray1));
				log.info("usertype array2= " + Arrays.toString(usertypeArray2));
				log.info("usertype array3= " + Arrays.toString(usertypeArray3));
				log.info("usertype array4= " + Arrays.toString(usertypeArray4));
				boolean b = Arrays.equals(usertypes, usertypeArray4);
				log.info("if roles are DR:  " + b);
				if (Arrays.equals(usertypes, usertypeArray1)) {
					log.info("if roles are IDR");
					return 4;
				} else if (Arrays.equals(usertypes, usertypeArray2)) {
					log.info("if roles are ID");
					return 4;
				} else if (Arrays.equals(usertypes, usertypeArray3)) {
					log.info("if roles are IR");
					return 4;
				} else if (Arrays.equals(usertypes, usertypeArray4)) {
					log.info("if roles are DR");
					return 5;
				} else {
					log.info("if role set not found");
					return 0;

				}

			}
			return 0;
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
	}

	public long roleCheck2(long usertypes[], long mainRole) {
		try {
			log.info("inside add roles");
			log.info("usertypes= " + Arrays.toString(usertypes));
			log.info("usertypes length is" + usertypes.length);
			List<Long> roles = new ArrayList<Long>();
			roles.add(4l);
			roles.add(5l);
			roles.add(6l);
			if (roles.contains(mainRole)) {
				if (usertypes.length == 1) {
					long usertypeId = 0;
					for (long id : usertypes) {
						usertypeId = id;
					}
					return usertypeId;
				} else if (usertypes.length > 1) {
					Arrays.sort(usertypes);
					long usertypeArray1[] = { 4, 5, 6 };
					long usertypeArray2[] = { 4, 5 };
					long usertypeArray3[] = { 4, 6 };
					long usertypeArray4[] = { 5, 6 };
					Arrays.sort(usertypeArray3);
					log.info("usertype array1= " + Arrays.toString(usertypeArray1));
					log.info("usertype array2= " + Arrays.toString(usertypeArray2));
					log.info("usertype array3= " + Arrays.toString(usertypeArray3));
					log.info("usertype array4= " + Arrays.toString(usertypeArray4));
					boolean b = Arrays.equals(usertypes, usertypeArray4);
					log.info("if roles are DR:  " + b);
					if (Arrays.equals(usertypes, usertypeArray1)) {
						log.info("if roles are IDR");
						return 4;
					} else if (Arrays.equals(usertypes, usertypeArray2)) {
						log.info("if roles are ID");
						return 4;
					} else if (Arrays.equals(usertypes, usertypeArray3)) {
						log.info("if roles are IR");
						return 4;
					} else if (Arrays.equals(usertypes, usertypeArray4)) {
						log.info("if roles are DR");
						return 5;
					} else {
						log.info("if role set not found");
						return 0;

					}

				}
				return 0;
			} else {

				return mainRole;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
	}

	public int saveUserSecurityquestion(UserProfile profile) {
		try {
			for (QuestionPair questionPair : profile.getQuestionList()) {
				UserSecurityquestion userSecurityQuestion = new UserSecurityquestion();
				Securityquestion securityQuestion = new Securityquestion();
				securityQuestion.setId(questionPair.getQuestionId());
				userSecurityQuestion.setAnswer(questionPair.getAnswer());
				userSecurityQuestion.setUser(profile.getUser());
				userSecurityQuestion.setSecurityQuestion(securityQuestion);
				userSecurityQuestionRepo.save(userSecurityQuestion);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return 0;
		}
	}

	;

	public int updateUserSecurityquestion(UserProfile profile) {
		try {
			log.info("inside update user security question function");
			log.info("question list from profile:  " + profile.getQuestionList());
			log.info("userid from profile:  " + profile.getUser().getId());
			for (QuestionPair questionPair : profile.getQuestionList()) {
				log.info("inside for loop");
				UserSecurityquestion userSecurityQuestion = new UserSecurityquestion();
				Securityquestion securityQuestion = new Securityquestion();
				log.info("going to find  userQuestionData ");
				log.info("userid is:  " + profile.getUser().getId() + " question id is " + questionPair.getQuestionId()
						+ " and answer is " + questionPair.getAnswer());
				securityQuestion.setId(questionPair.getQuestionId());
				userSecurityQuestion.setId(questionPair.getId());
				userSecurityQuestion.setAnswer(questionPair.getAnswer());
				userSecurityQuestion.setUser(profile.getUser());
				userSecurityQuestion.setSecurityQuestion(securityQuestion);
				UserSecurityquestion output = userSecurityQuestionRepo.save(userSecurityQuestion);
				log.info("updating user security question output:  " + output);
			}
			return 1;
		} catch (Exception e) {
			log.info("inside user security question update error");
			log.info(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	;

	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> userRegistration(UserProfile userDetails) {
		try {
			log.info("user details----::::   " + userDetails);
			Usertype userType = usertypeRepo.findByUsertypeName(userDetails.getUsertypeName());
			if (userType != null) {
				if (userDetails.getRoles() == null) {
					long data[] = { userType.getId() };
					userDetails.setRoles(data);
				}
			}
			long rolesOutput = roleCheck(userDetails.getRoles());
			boolean emailExist = userProfileRepo.existsByEmailAndUser_CurrentStatusNot(userDetails.getEmail(), 21);
			if (emailExist) {
				HttpResponse response = new HttpResponse(RegistrationTags.Email_Exist.getMessage(), 409,
						RegistrationTags.Email_Exist.getTag());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			boolean phoneExist = userProfileRepo.existsByPhoneNoAndUser_CurrentStatusNot(userDetails.getPhoneNo(), 21);
			if (phoneExist) {

				HttpResponse response = new HttpResponse(RegistrationTags.Phone_Exist.getMessage(), 409,
						RegistrationTags.Phone_Exist.getTag());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			log.info("roles output:" + rolesOutput);
			if (rolesOutput > 0) {
				List<Long> usertypeList = usertypeCheck();
				log.info("primary usertypeId is:  " + rolesOutput);
				Usertype userTypeData = new Usertype();
				userTypeData.setId(rolesOutput);
				userTypeData.setUsertypeName(userDetails.getUsertypeName());
				User userData = new User(userDetails.getUsername(), userDetails.getPassword(), UserStatus.NEW.getCode(),
						null, userTypeData);
				userData.setUserLanguage(userDetails.getUserLanguage());
				User userOutput = userRepo.save(userData);

				if (userOutput != null) {
					RequestHeaders header = new RequestHeaders(userDetails.getUserAgent(), userDetails.getPublicIp(),
							userOutput.getUsername());
					headerService.saveRequestHeader(header);
					int rolesAddStatus = saveRoles(userDetails.getRoles(), userOutput.getId());
					saveUserTrail(userOutput, "User Management", "Register User", 41, header.getPublicIp(),
							userDetails.getBrowser());
					
					
					log.info("role addition status:  " + rolesAddStatus);
					long userid = userOutput.getId();
					log.info("userid= " + userid);
					User users = new User();
					users.setId(userid);
					userDetails.setUser(userData);

					if (usertypeList.contains(userType.getId())) {
						if (userDetails.getType() == 0) {
							String displayName = "";
							if (Objects.nonNull(userDetails.getMiddleName())) {
								displayName = userDetails.getFirstName() + " " + userDetails.getMiddleName() + " "
										+ userDetails.getLastName();

							} else {

								displayName = userDetails.getFirstName() + " " + userDetails.getLastName();

							}
							userDetails.setDisplayName(displayName);
						} else {
							String displayName = userDetails.getCompanyName();
							userDetails.setDisplayName(displayName);
						}
					} else {
						String displayName = userDetails.getFirstName() + " " + userDetails.getMiddleName() + " "
								+ userDetails.getLastName();
						userDetails.setDisplayName(displayName);
					}
					UserProfile output = userProfileRepo.save(userDetails);
					if (output != null) {
						UserPasswordHistory userPassHistory = new UserPasswordHistory(userOutput,
								userDetails.getPassword());
						log.info("going to save user passowrd in user_password_history table");
						UserPasswordHistory userPasswordOtput = userPassHistoryRepoImpl
								.saveUserPassword(userPassHistory);
						if (userPasswordOtput != null) {
							log.info("user passowrd sucessfully save");
						}
						User dataForProfile = new User();
						dataForProfile.setId(userOutput.getId());
						String phoneOtp = otpService.phoneOtp(output.getPhoneNo());
						output.setPhoneOtp(phoneOtp);
						userProfileRepo.save(output);
						int outputGot = saveUserSecurityquestion(output);
						log.info("user security question output:  " + outputGot);
						String emailOtp = randomDigits.getNumericString(6);
						output.setEmailOtp(emailOtp);
						output.setSource("Self");
						userProfileRepo.save(output);
						boolean notificationStatus = emailUtils.saveNotification("REG_VERIFY_OTP_EMAIL_MSG", output, 41,
								"User Management", "Register User", userOutput.getUsername(), emailOtp,
								ChannelType.EMAIL, "users", 0);
						log.info("notification save:  " + notificationStatus);
						boolean notificationStatusForSms = emailUtils.saveNotification("REG_VERIFY_OTP_SMS_MSG", output,
								41, "User Management", "Register User", userOutput.getUsername(), phoneOtp,
								ChannelType.SMS, "users", 0);
						log.info("notificationStatusForSms save:  " + notificationStatusForSms);

						userOutput.setPreviousStatus(UserStatus.NEW.getCode());
						userOutput.setCurrentStatus(UserStatus.OTP_VERIFICATION_PENDING.getCode());
						userRepo.save(userOutput);
						OtpResponse response = new OtpResponse(RegistrationTags.REG_SUCESS_RESP.getMessage(), 200,
								userOutput.getId(), RegistrationTags.REG_SUCESS_RESP.getTag());
						log.info("response:   " + response);
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						HttpResponse response = new HttpResponse(RegistrationTags.REG_PROFILE_FAIL_RESP.getMessage(),
								409, RegistrationTags.REG_PROFILE_FAIL_RESP.getTag());
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
				} else {
					HttpResponse response = new HttpResponse(RegistrationTags.REG_USER_FAIL_RESP.getMessage(), 409,
							RegistrationTags.REG_USER_FAIL_RESP.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			} else {

				HttpResponse response = new HttpResponse(RegistrationTags.REG_FAIL_ROLES_RESP.getMessage(), 409,
						RegistrationTags.REG_FAIL_ROLES_RESP.getTag());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info(e.toString());
			HttpResponse response = new HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 409,
					RegistrationTags.COMMAN_FAIL_MSG.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public List<Long> usertypeCheck() {
		List<Long> usertypeId = new ArrayList<Long>();
		usertypeId.add(4l);
		usertypeId.add(5l);
		usertypeId.add(6l);
		return usertypeId;
	}

	/*
	 * public ResponseEntity<?> validateUser(Otp otp) { try {
	 * log.info("inside validate otp "); log.info("otp data:  " + otp);
	 * 
	 * User output = userRepo.findById(otp.getUserid()); if (output != null) { //
	 * RequestHeaders header=new //
	 * RequestHeaders(otp.getUserAgent(),otp.getPublicIp(),output.getUsername()); //
	 * headerService.saveRequestHeader(header); // return validateNewUser(
	 * output,otp); if (output.getPreviousStatus() == UserStatus.APPROVED.getCode())
	 * { if (otp.getForgotPassword() == 1) { saveUserTrail(output,
	 * "User Management", "OTP", 41, otp.getPublicIp(), otp.getBrowser()); return
	 * validateNewUser(output, otp); } else { saveUserTrail(output,
	 * "User Management", "OTP", 41, otp.getPublicIp(), otp.getBrowser()); return
	 * validateOldUser(output, otp); }
	 * 
	 * } else { saveUserTrail(output, "User Management", "OTP", 41,
	 * otp.getPublicIp(), otp.getBrowser()); return validateNewUser(output, otp); }
	 * } else { HttpResponse response = new
	 * HttpResponse(RegistrationTags.REG_WRONG_USER_ID.getMessage(), 409,
	 * RegistrationTags.REG_WRONG_USER_ID.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } } catch (Exception e) {
	 * e.printStackTrace(); log.info("error occure"); log.info(e.toString());
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 409,
	 * RegistrationTags.COMMAN_FAIL_MSG.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } }
	 */
	/*
	 * public ResponseEntity<?> validateOldUser(User user, Otp otp) { //
	 * UserTemporarydetails details = //
	 * userTemporarydetailsRepo.findByUserDetails_id(user.getId()); long
	 * diffInMinutes = utitlity.timeDifferenceInMinutes(details.getModifiedOn());
	 * log.info("differnce in minuted between two times: " + diffInMinutes); if
	 * (diffInMinutes <= 10) { if (otp.getEmailOtp().equals(details.getEmailIdOtp())
	 * && otp.getPhoneOtp().equals(details.getMobileOtp())) {
	 * user.setCurrentStatus(UserStatus.APPROVED.getCode());
	 * user.setPreviousStatus(UserStatus.OTP_VERIFICATION_PENDING.getCode());
	 * log.info("going to save user status"); userRepo.save(user);
	 * log.info("user status is saved"); UserProfile userProfileData =
	 * user.getUserProfile(); userProfileData.setPhoneNo(details.getMobileNo());
	 * userProfileData.setEmail(details.getEmailId());
	 * log.info("now going to save email and phone data in profile data");
	 * userProfileRepo.save(userProfileData);
	 * log.info("user profile data now save"); HttpResponse response = new
	 * HttpResponse(RegistrationTags.OTP_SUCESS_For_UpdateUSer.getMessage(), 200,
	 * RegistrationTags.OTP_SUCESS_For_UpdateUSer.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } else {
	 * log.info("if otp are incorrect"); HttpResponse response = new
	 * HttpResponse(RegistrationTags.REG_WRONG_OTP.getMessage(), 409,
	 * RegistrationTags.REG_WRONG_OTP.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } } else {
	 * log.info("if Otp time is expired"); HttpResponse response = new
	 * HttpResponse(RegistrationTags.REG_EXPIRE_OTP.getMessage(), 409,
	 * RegistrationTags.REG_EXPIRE_OTP.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 * }
	 */

	public ResponseEntity<?> validateNewUser(User output, Otp otp) {
		Map<String, String> mapEmail = new HashMap<String, String>();
		log.info("user profile data from db:  " + output);
		long diffInMinutes = utitlity.timeDifferenceInMinutes(output.getUserProfile().getModifiedOn());
		log.info("differnce in minuted between two times: " + diffInMinutes);

		if (diffInMinutes <= 10) {
			if (otp.getEmailOtp().equals(output.getUserProfile().getEmailOtp())
					&& otp.getPhoneOtp().equals(output.getUserProfile().getPhoneOtp())) {
				log.info("if otp are correct");
				HttpResponse response = new HttpResponse(RegistrationTags.OTP_SUCESS_RESP.getMessage(), 200,
						RegistrationTags.OTP_SUCESS_RESP.getTag());
				User user = userRepo.findById(output.getId());
				user.setPreviousStatus(UserStatus.OTP_VERIFICATION_PENDING.getCode());
				log.info(":::::::::username::::::::" + user.getUsername());
				mapEmail.put("<userID>", user.getUsername());

				if (otp.getForgotPassword() == 1) {
					user.setCurrentStatus(UserStatus.APPROVED.getCode());
				} else {
					user.setCurrentStatus(UserStatus.PENDING_ADMIN_APPROVAL.getCode());
					List<Long> usertypes = new ArrayList<Long>();
					usertypes.add(4l);
					usertypes.add(5l);
					usertypes.add(6l);
					int authorityStatus = 0;
					if (!usertypes.contains(user.getUsertype().getId())) {
						authorityStatus = 1;
					}
					List<User> adminUsers = userRepo.findByUsertype_IdAndCurrentStatus(8, 3);
					if (Objects.nonNull(adminUsers) && !adminUsers.isEmpty()) {
						for (User adminUser : adminUsers) {

							log.info(":::::::::::<First name>:::::" + adminUser.getUserProfile().getFirstName());
							mapEmail.put("<First name>", adminUser.getUserProfile().getFirstName());
							// String username=user.getUsername();
							// adminUser.setUsername(username);

							/*
							 * MessageConfigurationDb messageDB = new MessageConfigurationDb();
							 * 
							 * messageDB = messageConfigurationDbRepository.getByTag(
							 * "REG_NOTIFY_CEIR_ADMIN_TO_VERIFY_USER");
							 * log.info("messageDB data by tag: "+messageDB); String
							 * emailBody=emailContent(messageDB, user.getUserProfile(), ""); String
							 * subject=getsubject(messageDB, user.getUserProfile(), "");
							 * 
							 * log.info(":::::::::::emailBody:::::"+emailBody);
							 * 
							 * log.info(":::::::::::subject:::::"+subject);
							 * 
							 * Notification noti=new
							 * Notification(ChannelType.EMAIL,emailBody,adminUser.getId(),
							 * 41l,adminUser.getUsername(),"User Management",
							 * "user phone and email details validated", 1,subject,0,"users","CEIRAdmin",0);
							 * boolean adminNotification= emailUtils.saveNoti(noti);
							 * 
							 * log.info("notification save:  "+adminNotification);
							 */
							emailUtils.saveNotification("REG_NOTIFY_CEIR_ADMIN_TO_VERIFY_USER",
									adminUser.getUserProfile(), 8, "Registration Request",
									// "user phone and email details validated", String.valueOf(user.getId()), "NA",
									// mapEmail, "CEIRAdmin",
									"user phone and email details validated", user.getUsername(), "NA", mapEmail,
									"CEIRAdmin", "CEIRAdmin", "Users");

						}
					}

					boolean notificationStatus2 = emailUtils.saveNotification("REG_WAIT_USER_FOR_APPROV_STATUS",
							user.getUserProfile(), 41, "User Management", "user phone and email details validated",
							user.getUsername(), "", ChannelType.EMAIL, "users", authorityStatus);
					log.info("notification save:  " + notificationStatus2);

				}

				userRepo.save(user);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				log.info("if otp are incorrect");
				HttpResponse response = new HttpResponse(RegistrationTags.REG_WRONG_OTP.getMessage(), 409,
						RegistrationTags.REG_WRONG_OTP.getTag());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} else {
			log.info("if Otp time is expired");
			HttpResponse response = new HttpResponse(RegistrationTags.REG_EXPIRE_OTP.getMessage(), 409,
					RegistrationTags.REG_EXPIRE_OTP.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> resendOtp(ResendOtp otp) {
		log.info("inside resend otp controller");
		UserProfile profile = userProfileRepo.findByUser_Id(otp.getUserId());
		saveUserTrail(profile.getUser(), "User Management", "Resend OTP", 41, otp.getPublicIp(), otp.getBrowser());
		String smsOtp = otpService.phoneOtp(profile.getPhoneNo());
		String emailOtp = randomDigits.getNumericString(6);
		profile.setEmailOtp(emailOtp);
		profile.setPhoneOtp(smsOtp);
		UserProfile output = userProfileRepo.save(profile);
		boolean notificationStatus = emailUtils.saveNotification("REG_VERIFY_OTP_EMAIL_MSG", profile, 41,
				"User Management", "Resend Email OTP", profile.getUser().getUsername(), emailOtp, ChannelType.EMAIL,
				"users", 0);
		log.info("notification save:  " + notificationStatus);
		boolean notificationStatusForSms = emailUtils.saveNotification("REG_VERIFY_OTP_SMS_MSG", profile, 41,
				"User Management", "Resend SMS OTP", profile.getUser().getUsername(), smsOtp, ChannelType.SMS, "users",
				0);
		log.info("notificationStatusForSms save:  " + notificationStatusForSms);
		if (output != null) {
			RequestHeaders header = new RequestHeaders(otp.getUserAgent(), otp.getPublicIp(),
					output.getUser().getUsername());
			headerService.saveRequestHeader(header);
			User user = userRepo.findById(output.getUser().getId());
			userRepo.save(user);
			HttpResponse response = new HttpResponse(RegistrationTags.REOTP_SUCESS_RESP.getMessage(), 200,
					RegistrationTags.REOTP_SUCESS_RESP.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			HttpResponse response = new HttpResponse(RegistrationTags.REG_REOTP_FAIL.getMessage(), 409,
					RegistrationTags.REG_REOTP_FAIL.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	/*
	 * public ResponseEntity<?> profileResendOtp(ResendOtp otp) {
	 * log.info("inside resend otp controller"); UserProfile profile =
	 * userProfileRepo.findByUser_Id(otp.getUserId());
	 * saveUserTrail(profile.getUser(), "User Management", "Resend OTP", 41); String
	 * phoneOtp = randomDigits.getNumericString(6); String emailOtpData =
	 * randomDigits.getNumericString(6); boolean notificationStatus =
	 * emailUtils.saveNotification("PRO_VERIFY_OTP_EMAIL_MSG", profile, 41,
	 * "User Management", "Update Profile Resend Email OTP",
	 * profile.getUser().getUsername(), emailOtpData, ChannelType.EMAIL,
	 * "user_temp", 0); log.info("notification save:  " + notificationStatus);
	 * boolean notificationStatusForSms =
	 * emailUtils.saveNotification("PRO_VERIFY_OTP__MSG", profile, 41,
	 * "User Management", "Update Profile Resend SMS OTP",
	 * profile.getUser().getUsername(), phoneOtp, ChannelType.SMS, "user_temp", 0);
	 * log.info("notificationStatusForSms save:  " + notificationStatusForSms);
	 * UserTemporarydetails details = new UserTemporarydetails(profile.getUser(),
	 * profile.getEmail(), profile.getPhoneNo(), emailOtpData, phoneOtp);
	 * UserTemporarydetails dataByProfileId =
	 * userTemporarydetailsRepo.findByUserDetails_id(profile.getUser().getId());
	 * log.
	 * info("temporary email and phone no profile details by user profile table id "
	 * + profile.getId()); if (dataByProfileId != null) {
	 * details.setId(dataByProfileId.getId());
	 * details.setCreatedOn(dataByProfileId.getCreatedOn());
	 * details.setModifiedOn(dataByProfileId.getModifiedOn()); }
	 * log.info("going to add data to user profile temporary data table");
	 * UserTemporarydetails output = userTemporarydetailsRepo.save(details);
	 * log.info("after adding data to temporaray table"); if (output != null) {
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.REOTP_SUCESS_RESP.getMessage(), 200,
	 * RegistrationTags.REOTP_SUCESS_RESP.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } else { HttpResponse response =
	 * new HttpResponse(RegistrationTags.REG_REOTP_FAIL.getMessage(), 409,
	 * RegistrationTags.REG_REOTP_FAIL.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 * }
	 */

	public ResponseEntity<?> checkRegistration(Integer usertypeId) {

		try {
			log.info("inside check registration method");
			log.info("usertypeId value " + usertypeId);
			Usertype usertype = usertypeRepo.findById(usertypeId);
			log.info("now going to check this usertype is available for registration or not");
			if (usertype.getStatus() == UserTypeStatusFlag.on.getCode()) {
				log.info("usertype is available for trgistration");
				HashMap<Integer, String> map = new HashMap<Integer, String>();
				map.put(UsertypeData.Importer.getCode(), "imp_user_limit");
				map.put(UsertypeData.Distributor.getCode(), "dist_user_limit");
				map.put(UsertypeData.Retailer.getCode(), "ret_user_limit");
				map.put(UsertypeData.Custom.getCode(), "cus_user_limit");
				map.put(UsertypeData.Operator.getCode(), "ope_user_limit");
				map.put(UsertypeData.TRC.getCode(), "trc_user_limit");
				map.put(UsertypeData.Manufacturer.getCode(), "man_user_limit");
				map.put(UsertypeData.Lawful_Agency.getCode(), "law_user_limit");
				map.put(UsertypeData.End_User.getCode(), "end_user_limit");
				map.put(UsertypeData.Immigration.getCode(), "imm_user_limit");
				map.put(UsertypeData.Customer_Care.getCode(), "customer_user_limit");
				map.put(UsertypeData.DRT.getCode(), "drt_user_limit");

				log.info("then going to fetch data from system configuration db by tag " + map.get(usertypeId));
				SystemConfigurationDb systemConfigData = systemConfigurationDbRepoImpl
						.getDataByTag(map.get(usertypeId));

				long userLimit = 0;
				if (systemConfigData != null) {
					try {
						log.info("value got by tag= " + systemConfigData.getValue());
						userLimit = Long.parseLong(systemConfigData.getValue());
					} catch (Exception e) {

						log.info(e.toString());
					}
				}

				long count = userRepoService.countByUsertypeId(usertypeId);
				log.info("total users find by this usertype= " + count);

				log.info("usertype count greater than total users limit then we don't able to create new user now");
				if (count >= userLimit) {
					log.info("user creation limit is exceeded for " + usertype.getUsertypeName());
					RunningAlertDb alertDb = new RunningAlertDb("alert001",
							" user creation limit is exceeded for " + usertype.getUsertypeName() + " usertype",
							AlertStatus.Init.getCode());
					alertDbRepo.saveAlertDb(alertDb);
					HttpResponse response = new HttpResponse(RegistrationTags.user_exceed_limit.getMessage(), 409,
							RegistrationTags.user_exceed_limit.getTag());
					log.info("response after alert generation " + response);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					log.info(
							"if usertype count less than total users limit then so happily we able to create new user now");
					HttpResponse response = new HttpResponse(RegistrationTags.Reg_allowed.getMessage(), 200,
							RegistrationTags.Reg_allowed.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);

				}

			} else {
				log.info("usertype status is disable now so registration is not available as of now");
				HttpResponse response = new HttpResponse(RegistrationTags.Reg_flag_off.getMessage(), 409,
						RegistrationTags.Reg_flag_off.getTag());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.info("something wrong happened here now");
			HttpResponse response = new HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 500,
					RegistrationTags.COMMAN_FAIL_MSG.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> changePassword(ChangePassword password) {
		try {
			log.info("inside change password controller");
			log.info("ChangePassword data from form: " + password);
			User user = userRepo.findById(password.getUserid());
			saveUserTrail(user, "User Management", "Change Password", 41, password.getPublicIp(),
					password.getBrowser());
			return changePasswordMethod(user, password);
		} catch (Exception e) {
			log.info("exception occur");
			e.printStackTrace();
			HttpResponse response = new HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 409,
					RegistrationTags.COMMAN_FAIL_MSG.getTag());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> updateExpirePassword(ChangePassword password) {
		try {
			log.info("inside change password controller");
			log.info("ChangePassword data from form: " + password);
			User user = userRepo.findById(password.getUserid());
			// user.setPasswordDate(LocalDateTime.now());
			saveUserTrail(user, "User Management", "Reset", 41, password.getPublicIp(), password.getBrowser());
			return changeExpiryPasswordMethod(user, password);
		} catch (Exception e) {
			log.info("exception occur");
			e.printStackTrace();
			HttpResponse response = new HttpResponse();
			response.setStatusCode(409);
			response.setResponse("Oops something wrong happened");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> changePasswordMethod(User user, ChangePassword password) {
		log.info("password details==" + password);
		
		String userPassword = pooledPBEStringEncryptor.decrypt(user.getPassword());
		if (password.getOldPassword().equals(userPassword)) {
			boolean passwordExist = userPassHistoryRepoImpl.passwordExist(password.getPassword(), password.getUserid());
			if (passwordExist == true) {
				log.info("if this password exist");
				HttpResponse response = new HttpResponse(ProfileTags.PRO_CPASS_LAST_3PASS_ERROR.getMessage(), 204,
						ProfileTags.PRO_CPASS_LAST_3PASS_ERROR.getTag());
				log.info("exit from change password");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				log.info("if this password does not exist");
				long count = userPassHistoryRepoImpl.countByUserId(password.getUserid());
				log.info("password count: " + count);
				if (count != 0) {
					if (count >= 3) {
						log.info("going to delete password history greater than 3 " + password.getUserid());
						UserPasswordHistory passHistory = userPassHistoryRepoImpl
								.getPasswordHistory(password.getUserid());
						log.info("old passs history-- " + passHistory);
						userPasswordHistoryRepo.deleteById(passHistory.getId());
						// userPasswordHistoryRepo.deleteId(passHistory.getId());
						log.info("---- ");
						//user.setPassword(password.getPassword());
						user.setPassword(pooledPBEStringEncryptor.encrypt(password.getPassword()));
						return setPassword(user);
					} else {
						log.info("if password history less than 3");
						//user.setPassword(password.getPassword());
						user.setPassword(pooledPBEStringEncryptor.encrypt(password.getPassword()));
						return setPassword(user);
					}
				} else {
					//user.setPassword(password.getPassword());
					user.setPassword(pooledPBEStringEncryptor.encrypt(password.getPassword()));
					return setPassword(user);
				}

			}

		} else {
			HttpResponse response = new HttpResponse(ProfileTags.PRO_OldPass_Error.getMessage(), 204,
					ProfileTags.PRO_OldPass_Error.getTag());
			log.info("exit from change password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> changeExpiryPasswordMethod(User user, ChangePassword password) {
		if (password.getOldPassword().equals(user.getPassword())) {
			boolean passwordExist = userPassHistoryRepoImpl.passwordExist(password.getPassword(), password.getUserid());
			if (passwordExist == true) {
				log.info("if this password exist");
				HttpResponse response = new HttpResponse(ProfileTags.PRO_CPASS_LAST_3PASS_ERROR.getMessage(), 204,
						ProfileTags.PRO_CPASS_LAST_3PASS_ERROR.getTag());
				log.info("exit from change password");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				log.info("if this password does not exist");
				long count = userPassHistoryRepoImpl.countByUserId(password.getUserid());
				log.info("password count: " + count);
				if (count != 0) {
					if (count >= 3) {
						log.info("going to delete password history greater than 3");
						UserPasswordHistory passHistory = userPassHistoryRepoImpl
								.getPasswordHistory(password.getUserid());
						userPasswordHistoryRepo.deleteById(passHistory.getId());
						user.setPassword(password.getPassword());
						return setPassword2(user);
					} else {
						log.info("if password history less than 3");
						user.setPassword(password.getPassword());
						return setPassword2(user);
					}
				} else {
					user.setPassword(password.getPassword());
					return setPassword2(user);
				}

			}

		} else {
			HttpResponse response = new HttpResponse(ProfileTags.PRO_OldPass_Error.getMessage(), 204,
					ProfileTags.PRO_OldPass_Error.getTag());
			log.info("exit from change password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> setPassword(User user) {
		User output = userRepo.save(user);
		if (output != null) {
			UserPasswordHistory userPassHistory = new UserPasswordHistory();
			userPassHistory.setPassword(user.getPassword());
			userPassHistory.setUserPassword(output);
			userPassHistory.setCreatedOn(LocalDateTime.now());
			userPassHistory.setModifiedOn(LocalDateTime.now());
			log.info("going to save user passowrd in user_password_history table");
			UserPasswordHistory userPasswordOtput = userPassHistoryRepoImpl.saveUserPassword(userPassHistory);
			if (userPasswordOtput != null) {
				log.info("user passowrd sucessfully save");
			}
			List<Long> usertypes = new ArrayList<Long>();
			usertypes.add(4l);
			usertypes.add(5l);
			usertypes.add(6l);
			int authorityStatus = 0;
			if (!usertypes.contains(user.getUsertype().getId())) {
				authorityStatus = 1;
			}

			boolean notificationStatus = emailUtils.saveNotification("PRO_CHANGE_PASSWORD_BY_USER",
					output.getUserProfile(), 41, "User Management", "Change Password", output.getUsername(), "",
					ChannelType.EMAIL, "users", authorityStatus);
			log.info("notification save: " + notificationStatus);
			HttpResponse response = new HttpResponse(ProfileTags.PRO_CPASS_SUCESS.getMessage(), 200,
					ProfileTags.PRO_CPASS_SUCESS.getTag());
			log.info("exit from change password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			HttpResponse response = new HttpResponse(ProfileTags.PRO_CPASS_FAIL.getMessage(), 500,
					ProfileTags.PRO_CPASS_FAIL.getTag());
			log.info("exit from change password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> setPassword2(User user) {
		SystemConfigurationDb systemConfiguration = systemConfigurationRepo.getDataByTag("USER_PASS_EXPIRY_DAYS");
		log.info("system config data by tag: USER_PASS_EXPIRY_DAYS" + systemConfiguration.getValue());
		Integer days = 0;
		if (systemConfiguration != null) {
			days = Integer.parseInt(systemConfiguration.getValue());
		}
		log.info("days going to add" + days);
		LocalDateTime date = user.getPasswordDate();
		date = date.plusDays(days);
		log.info("now password expiry date: " + date);
		user.setPasswordDate(date);
		User output = userRepo.save(user);
		if (output != null) {
			UserPasswordHistory userPassHistory = new UserPasswordHistory();
			userPassHistory.setPassword(user.getPassword());
			userPassHistory.setUserPassword(output);
			userPassHistory.setCreatedOn(LocalDateTime.now());
			userPassHistory.setModifiedOn(LocalDateTime.now());

			log.info("going to save user passowrd in user_password_history table");
			UserPasswordHistory userPasswordOtput = userPassHistoryRepoImpl.saveUserPassword(userPassHistory);
			if (userPasswordOtput != null) {
				log.info("user passowrd sucessfully save");
			}
			List<Long> usertypes = new ArrayList<Long>();
			usertypes.add(4l);
			usertypes.add(5l);
			usertypes.add(6l);
			int authorityStatus = 0;
			if (!usertypes.contains(user.getUsertype().getId())) {
				authorityStatus = 1;
			}

			boolean notificationStatus = emailUtils.saveNotification("PRO_CHANGE_PASSWORD_BY_USER",
					output.getUserProfile(), 41, "User Management", "Change Password", output.getUsername(), "",
					ChannelType.EMAIL, "users", authorityStatus);
			log.info("notification save: " + notificationStatus);
			HttpResponse response = new HttpResponse(ProfileTags.PRO_CPASS_SUCESS.getMessage(), 200,
					ProfileTags.PRO_CPASS_SUCESS.getTag());
			log.info("exit from change password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			HttpResponse response = new HttpResponse(ProfileTags.PRO_CPASS_FAIL.getMessage(), 500,
					ProfileTags.PRO_CPASS_FAIL.getTag());
			log.info("exit from change password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> setNewPassword(User user) {
		User output = userRepo.save(user);
		if (output != null) {
			UserPasswordHistory userPassHistory = new UserPasswordHistory();
			userPassHistory.setPassword(user.getPassword());
			userPassHistory.setUserPassword(output);
			userPassHistory.setCreatedOn(LocalDateTime.now());
			userPassHistory.setModifiedOn(LocalDateTime.now());
			log.info("going to save user passowrd in user_password_history table");
			UserPasswordHistory userPasswordOtput = userPassHistoryRepoImpl.saveUserPassword(userPassHistory);
			if (userPasswordOtput != null) {
				log.info("user passowrd sucessfully save");
			}
			List<Long> usertypes = new ArrayList<Long>();
			usertypes.add(4l);
			usertypes.add(5l);
			usertypes.add(6l);
			int authorityStatus = 0;
			if (!usertypes.contains(output.getUsertype().getId())) {
				authorityStatus = 1;
			}

			boolean notificationStatus = emailUtils.saveNotification("FORGOT_PASSWORD_EMAIL", output.getUserProfile(),
					41, "User Management", "Forgot Password", output.getUsername(), "", ChannelType.EMAIL, "users",
					authorityStatus);
			log.info("notification save: " + notificationStatus);
			HttpResponse response = new HttpResponse(ProfileTags.NEW_PASS_SUC.getMessage(), 200,
					ProfileTags.NEW_PASS_SUC.getTag());
			log.info("exit from update new password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			HttpResponse response = new HttpResponse(ProfileTags.NEW_PASS_FAIL.getMessage(), 500,
					ProfileTags.NEW_PASS_FAIL.getTag());
			log.info("exit from  update New password");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> updateUserStatus(UserStatusRequest userStatus) {
		log.info("inside  update User status  controller");
		log.info(" userStatus data:  " + userStatus);
		log.info("get user  data by userid below");
		User user = userRepo.findById(userStatus.getUserId());
		if (user != null) {
			if (userStatus.getPassword().trim().equals(user.getPassword())) {
				Integer userStatus2 = UserStatus.getUserStatusByDesc(userStatus.getStatus()).getCode();
				user.setPreviousStatus(user.getCurrentStatus());
				user.setCurrentStatus(userStatus2);
				user.setModifiedBy(userStatus.getUsername());
				User output = userRepo.save(user);
				log.info("user data after update the status: " + output);
				if (output != null) {
					String tag = "";
					String subFeature = new String();
					String subject = "";
					if (output.getCurrentStatus() == UserStatus.APPROVED.getCode()) {
						tag = "PRO_ENABLE_ACC_BY_USER";
						log.info("if user status is approved");
						subFeature = "Enabled";
						subject = "Account Enabled Notification for " + output.getUsername();

					} else if (output.getCurrentStatus() == UserStatus.DISABLE.getCode()) {
						tag = "PRO_DISABLE_ACC_BY_USER";
						log.info("if user status is disable");
						subFeature = "Disabled";
						subject = "Account Disabled Notification for " + output.getUsername();
					} else if (output.getCurrentStatus() == UserStatus.DEACTIVATE.getCode()) {
						tag = "PRO_DEACTIVATE_ACC_BY_USER";
						log.info("if user status is deactivate");
						subFeature = "Deactivated";
						subject = "Deactivation Notification for " + output.getUsername();
					} else {
						tag = "";
						log.info("current user status id " + output.getCurrentStatus());
						subFeature = "";
					}
					saveUserTrail(user, "User Management", subFeature, 41, userStatus.getPublicIp(),
							userStatus.getBrowser());
					List<Long> usertypes = new ArrayList<Long>();
					usertypes.add(4l);
					usertypes.add(5l);
					usertypes.add(6l);
					int authorityStatus = 0;
					if (!usertypes.contains(output.getUsertype().getId())) {
						authorityStatus = 1;
					}
					boolean notificationStatus = emailUtils.saveNotification(tag, output.getUserProfile(), 41,
							"User Management", subFeature, output.getUsername(), "", ChannelType.EMAIL, "users",
							authorityStatus);
					log.info("notification save: " + notificationStatus);
					HttpResponse response = new HttpResponse(UpdateUserStatusTags.USER_STATUS_CHANGED.getMessage(), 200,
							UpdateUserStatusTags.USER_STATUS_CHANGED.getTag());
					log.info("response send to user:  " + response);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					HttpResponse response = new HttpResponse(UpdateUserStatusTags.USER_STATUS_CHANGE_FAIL.getMessage(),
							500, UpdateUserStatusTags.USER_STATUS_CHANGE_FAIL.getTag());
					log.info("response send to user:  " + response);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			} else {
				log.info("wrong password");
				HttpResponse response = new HttpResponse(ProfileTags.PRO_CORRECT_PASS.getMessage(), 401,
						ProfileTags.PRO_CORRECT_PASS.getTag());
				log.info("response send to user:  " + response);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} else {
			HttpResponse response = new HttpResponse(UpdateUserStatusTags.USER_STATUS_CHANGE_FAIL.getMessage(), 409,
					UpdateUserStatusTags.USER_STATUS_CHANGE_FAIL.getTag());
			log.info("response send to user:  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> changeUserStatus(ChangeUserStatus userStatus) {
		log.info("inside  change User status  controller");
		log.info(" userStatus data:  " + userStatus);
		log.info("get user  data by userid below");
		User user = userRepo.findById(userStatus.getId());
		if (user != null) {
			if (userStatus.getAction() == 0) {
				User userData = userRepo.findById(userStatus.getUserId());
				if (userData != null) {
					saveUserTrail(userData, "Registration Request", "UserStatus", 41, userStatus.getPublicIp(),
							userStatus.getBrowser());
				}
				user.setPreviousStatus(user.getCurrentStatus());
				user.setCurrentStatus(userStatus.getStatus());
				user.setRemark(userStatus.getRemark());
				user.setReferenceId(userStatus.getReferenceId());
				user.setModifiedBy(userData.getUsername());
				user.setApprovedBy(userData.getUsername());
				User output = userRepo.save(user);

				log.info("user data after update the status: " + output);
				if (output != null) {
					HttpResponse response = new HttpResponse(UpdateUserStatusTags.USER_STATUS_CHANGED.getMessage(), 200,
							UpdateUserStatusTags.USER_STATUS_CHANGED.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					HttpResponse response = new HttpResponse(UpdateUserStatusTags.USER_STATUS_CHANGE_FAIL.getMessage(),
							500, UpdateUserStatusTags.USER_STATUS_CHANGE_FAIL.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			} else if (userStatus.getAction() == 1) {

				log.info("if action is add");
				User userData = userRepo.findById(userStatus.getUserId());
				List<Long> usertypeList = new ArrayList<Long>();
				usertypeList.add(4l);
				usertypeList.add(5l);
				usertypeList.add(6l);
				Userrole roles = null;
				if (usertypeList.contains(userStatus.getUsertype())) {

					if (userData != null) {
						saveUserTrail(userData, "Registration Request", "AddedRole", 8, userStatus.getPublicIp(),
								userStatus.getBrowser());
					}

					boolean existsData = false;
					existsData = userRoleRepoService.existsByUerIdAndUsertypeId(userStatus.getId(),
							userStatus.getRole());
					log.info("exist by role and userid: " + existsData);
					if (existsData == false) {
						User users = new User(userStatus.getId());
						Usertype usertype = new Usertype(userStatus.getRole());
						roles = new Userrole(users, usertype);
					}

				}

				if (roles == null) {
					HttpResponse response = new HttpResponse(UpdateUserStatusTags.Roles_Exist.getMessage(), 200,
							UpdateUserStatusTags.Roles_Exist.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					user.setRemark(userStatus.getRemark());
					user.setReferenceId(userStatus.getReferenceId());
					user.setModifiedBy(userData.getUsername());
					user.setApprovedBy(userData.getUsername());
					Userrole rolesOutput = userRoleRepoService.saveRole(roles);
					if (rolesOutput != null) {
						HttpResponse response = new HttpResponse(UpdateUserStatusTags.Roles_Added.getMessage(), 200,
								UpdateUserStatusTags.Roles_Added.getTag());
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						HttpResponse response = new HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 200,
								RegistrationTags.COMMAN_FAIL_MSG.getTag());
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
				}
			} else if (userStatus.getAction() == 2) {
				User userData = userRepo.findById(userStatus.getUserId());
				List<Long> usertypeList = new ArrayList<Long>();
				usertypeList.add(4l);
				usertypeList.add(5l);
				usertypeList.add(6l);
				if (userData != null) {
					saveUserTrail(userData, "Registration Request", "DeletedRole", 8, userStatus.getPublicIp(),
							userStatus.getBrowser());
				}
				user.setRemark(userStatus.getRemark());
				user.setReferenceId(userStatus.getReferenceId());
				user.setModifiedBy(userData.getUsername());
				user.setApprovedBy(userData.getUsername());
				boolean rolesOutput = userRoleRepoService.deletebyUserIdandUsertype(userStatus.getId(),
						userStatus.getRole());
				if (rolesOutput) {
					HttpResponse response = new HttpResponse(UpdateUserStatusTags.Roles_Delete.getMessage(), 200,
							UpdateUserStatusTags.Roles_Delete.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					HttpResponse response = new HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 200,
							RegistrationTags.COMMAN_FAIL_MSG.getTag());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			} else {
				HttpResponse response = new HttpResponse(RegistrationTags.Invalid_Action.getMessage(), 409,
						RegistrationTags.Invalid_Action.getTag());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		} else {
			HttpResponse response = new HttpResponse(UpdateUserStatusTags.USER_STATUS_WRONG_USERID.getMessage(), 409,
					UpdateUserStatusTags.USER_STATUS_WRONG_USERID.getTag());
			log.info("response send to user:  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public long[] rolesArray(AllRequest request) {
		List<Long> rolesList = new ArrayList<Long>();
		try {
			log.info("going fetch roles by userid :" + request.getDataId());
			List<Userrole> userRoles = userRoleRepo.findByUserData_Id(request.getDataId());
			for (Userrole role : userRoles) {
				rolesList.add(role.getUsertypeData().getId());
			}
			long[] rolesArray = new long[rolesList.size()];
			rolesArray = Longs.toArray(rolesList);
			Arrays.sort(rolesArray);
			return rolesArray;
		} catch (Exception e) {
			log.info("exception when getting role array");
			log.info(e.toString());
			return null;
		}

	}

	public ResponseEntity<?> addRoles(AllRequest request) {
		long ID[] = { 4, 5 };
		long IR[] = { 4, 6 };
		long I[] = { 4 };
		long D[] = { 5 };
		log.info("if action is add");
		User user = userRepo.findById(request.getDataId());
		long mainrole = user.getUsertype().getId();
		log.info("main role is : " + mainrole);
		List<Usertype> rolesOutput = new ArrayList<Usertype>();
		if (mainrole == 4) {
			long rolesArray[] = rolesArray(request);

			if (Objects.isNull(rolesArray)) {
				log.info("role array value is null");
				GenricResponse response = new GenricResponse(409, RegistrationTags.COMMAN_FAIL_MSG.getTag(),
						RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			log.info("rolesArray : " + Arrays.toString(rolesArray));
			log.info("rolesArray length: " + rolesArray.length);
			if (rolesArray.length == 3) {
				log.info("role addition limit completed");
				GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Add_Not_Found.getTag(),
						UserRoleTags.Role_Add_Not_Found.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				if (Arrays.equals(rolesArray, ID)) {
					log.info("if roles are ID");
					rolesOutput.add(new Usertype(6, "Retailer"));
					GenricResponse response = new GenricResponse(200, "roles are available to add", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else if (Arrays.equals(rolesArray, IR)) {
					log.info("if roles are IR");
					rolesOutput.add(new Usertype(5, "Distributor"));
					GenricResponse response = new GenricResponse(200, "roles are available to add", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else if (Arrays.equals(rolesArray, I)) {
					log.info("if roles are IR");
					rolesOutput.add(new Usertype(5, "Distributor"));
					rolesOutput.add(new Usertype(6, "Retailer"));
					GenricResponse response = new GenricResponse(200, "roles are available to add", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					log.info("no roles found in the pair");
					GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Add_Not_Found.getTag(),
							UserRoleTags.Role_Add_Not_Found.getMessage(), "");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
		} else if (mainrole == 5) {
			long rolesArray[] = rolesArray(request);
			if (rolesArray.length == 2) {
				log.info("role addition limit completed");
				GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Add_Not_Found.getTag(),
						UserRoleTags.Role_Add_Not_Found.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				if (Arrays.equals(rolesArray, D)) {
					log.info("if role is D");
					rolesOutput.add(new Usertype(6, "Retailer"));
					GenricResponse response = new GenricResponse(200, "data is found", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Add_Not_Found.getTag(),
							UserRoleTags.Role_Add_Not_Found.getMessage(), "");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
		} else if (mainrole == 6) {
			log.info("we can't add role to it");
			GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Add_Not_Found.getTag(),
					UserRoleTags.Role_Add_Not_Found.getMessage(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			log.info("we can't add role to it");
			GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Add_Not_Found.getTag(),
					UserRoleTags.Role_Add_Not_Found.getMessage(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> deleteroles(AllRequest request) {
		long IDR[] = { 4, 5, 6 };
		long ID[] = { 4, 5 };
		long IR[] = { 4, 6 };
		long DR[] = { 5, 6 };
		long I[] = { 4 };
		long D[] = { 5 };
		User user = userRepo.findById(request.getDataId());
		long mainrole = user.getUsertype().getId();

		List<Usertype> rolesOutput = new ArrayList<Usertype>();

		if (mainrole == 4) {
			long rolesArray[] = rolesArray(request);
			if (Objects.isNull(rolesArray)) {
				GenricResponse response = new GenricResponse(409, RegistrationTags.COMMAN_FAIL_MSG.getTag(),
						RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}

			if (rolesArray.length == 1) {
				log.info("not able to delete");
				GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Del_Not_Found.getTag(),
						UserRoleTags.Role_Del_Not_Found.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				if (Arrays.equals(rolesArray, IDR)) {
					log.info("if roles are IDR");
					rolesOutput.add(new Usertype(5, "Distributor"));
					rolesOutput.add(new Usertype(6, "Retailer"));
					GenricResponse response = new GenricResponse(200, "data is found", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else if (Arrays.equals(rolesArray, ID)) {
					log.info("if roles are ID");
					rolesOutput.add(new Usertype(5, "Distributor"));
					GenricResponse response = new GenricResponse(200, "data is found", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else if (Arrays.equals(rolesArray, IR)) {
					log.info("if roles are IR");
					rolesOutput.add(new Usertype(6, "Retailer"));
					GenricResponse response = new GenricResponse(200, "data is found", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Del_Not_Found.getTag(),
							UserRoleTags.Role_Del_Not_Found.getMessage(), "");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
		} else if (mainrole == 5) {
			long rolesArray[] = rolesArray(request);
			if (rolesArray.length == 1) {
				log.info("not able to delete");
				GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Del_Not_Found.getTag(),
						UserRoleTags.Role_Del_Not_Found.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				if (Arrays.equals(rolesArray, DR)) {
					log.info("if role is D");
					rolesOutput.add(new Usertype(6, "Retailer"));
					GenricResponse response = new GenricResponse(200, "data is found", "", rolesOutput);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Del_Not_Found.getTag(),
							UserRoleTags.Role_Del_Not_Found.getMessage(), "");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}

		} else if (mainrole == 6) {
			log.info("we can't delete role to it");
			GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Del_Not_Found.getTag(),
					UserRoleTags.Role_Del_Not_Found.getMessage(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			log.info("we can't delete role to it");
			GenricResponse response = new GenricResponse(409, UserRoleTags.Role_Del_Not_Found.getTag(),
					UserRoleTags.Role_Del_Not_Found.getMessage(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> addDeleteroles(AllRequest request) {
		// GenricResponse response = new GenricResponse();
		try {
			log.info("inside getting add or delete roles");
			log.info("data given: " + request);
			if (request.getAction() == 1) {
				return addRoles(request);
			}
			if (request.getAction() == 2) {
				return deleteroles(request);
			} else {
				log.info("invalid action");
				GenricResponse response = new GenricResponse(409, UserRoleTags.Invalid_Action.getTag(),
						UserRoleTags.Invalid_Action.getMessage(), "");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("error occurs");
			log.info(e.toString());
			GenricResponse response = new GenricResponse(409, RegistrationTags.COMMAN_FAIL_MSG.getTag(),
					RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<?> editProfile(long id, UserHeader header) {
		try {
			log.info("inside into edit profile");
			log.info("user id:  " + id);
			log.info("get user  data by userid below");
			UserProfile user = userProfileRepo.findByUser_Id(id);
			if (Objects.nonNull(user)) {
				saveUserTrail(user.getUser(), "User Management", "View Profile", 41, header.getPublicIp(),
						header.getBrowser());
				log.info("user profile data: " + user);
				List<QuestionPair> questionList = new ArrayList<QuestionPair>();
				log.info("now going to fetch security question data of user");
				for (UserSecurityquestion question : user.getUser().getUserSecurityquestion()) {
					QuestionPair questionPair = new QuestionPair();
					questionPair.setId(question.getId());
					questionPair.setQuestion(question.getSecurityQuestion().getQuestion());
					questionPair.setQuestionId(question.getSecurityQuestion().getId());
					questionPair.setAnswer(question.getAnswer());
					questionList.add(questionPair);
				}
				log.info("questionList:  " + questionList);
				user.setQuestionList(questionList);
				user.setUserTypeId(user.getUser().getUsertype().getId());
				List<Long> rolesId = new ArrayList<Long>();
				log.info("now going to fetch roles of user");
				for (Userrole userRoles : user.getUser().getUserRole()) {
					rolesId.add(userRoles.getUsertypeData().getId());
				}
				log.info("roles Ids :  " + rolesId);
				long[] arr = new long[rolesId.size()];
				arr = Longs.toArray(rolesId);
				user.setRoles(arr);
				List<SystemConfigListDb> asTypeList = systemConfigRepo.getByTag("AS_TYPE");
				for (SystemConfigListDb asType : asTypeList) {
					Integer value = asType.getValue();
					if (user.getType() == value) {
						user.setAsTypeName(asType.getInterp());
					}
				}
				List<SystemConfigListDb> arrivaPortList = systemConfigRepo.getByTag("CUSTOMS_PORT");
				for (SystemConfigListDb port : arrivaPortList) {
					Integer value = port.getValue();
					if (user.getArrivalPort() == value) {
						user.setArrivalPortName(port.getInterp());
					}
				}

				List<SystemConfigListDb> nature_Of_Employment = systemConfigRepo.getByTag("Nature_Of_Employment");
				for (SystemConfigListDb emplomentType : nature_Of_Employment) {
					Integer value = emplomentType.getValue();
					if (user.getNatureOfEmployment() == value) {
						user.setNatureOfEmploymentInterp(emplomentType.getInterp());
					}
				}

				if (user.getPortAddress() != null) {
					PortAddress portAddress = portAddressRepoService.getById(user.getPortAddress());
					user.setPortAddressName(portAddress.getAddress());
				}

				SystemConfigurationDb filePath = systemConfigurationRepo.getDataByTag("upload_file_link");
				if (filePath != null) {
					if (user.getNidFilename() != null || !"null".equalsIgnoreCase(user.getNidFilename())) {
						user.setNidFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/NID/");
					}

					if (user.getPhotoFilename() != null || !"null".equalsIgnoreCase(user.getPhotoFilename())) {
						user.setPhotoFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/photo/");
					}
					if (user.getIdCardFilename() != null || !"null".equalsIgnoreCase(user.getIdCardFilename())) {
						user.setIdCardFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/IDCard/");
					}
					if (user.getVatFilename() != null || !"null".equalsIgnoreCase(user.getVatFilename())) {
						user.setVatFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/Vat/");
					}
				}
				log.info("All data now fetched");
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				HttpResponse response = new HttpResponse();
				response.setStatusCode(204);
				response.setResponse("User profile data fail to find by userid");
				log.info("response send to user:  " + response);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("exception occur");
			e.printStackTrace();
			HttpResponse response = new HttpResponse();
			response.setStatusCode(409);
			response.setResponse("Oops something wrong happened");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	/*
	 * public void setEmailAndPhoneDetails(UserProfile userProfile, User userData) {
	 * String phoneOtp = otpService.phoneOtp(userProfile.getPhoneNo()); String
	 * emailOtpData = randomDigits.getNumericString(6);
	 * userProfile.setUser(userData); boolean notificationStatus =
	 * emailUtils.saveNotification("PRO_VERIFY_OTP_EMAIL_MSG", userProfile, 41,
	 * "User Management", "Update Profile Email OTP", userData.getUsername(),
	 * emailOtpData, ChannelType.EMAIL, "user_temp", 0);
	 * log.info("notification save:  " + notificationStatus); boolean
	 * notificationStatusForSms = emailUtils.saveNotification("PRO_VERIFY_OTP__MSG",
	 * userProfile, 41, "User Management", "Update Profile SMS OTP",
	 * userData.getUsername(), phoneOtp, ChannelType.SMS, "user_temp", 0);
	 * log.info("notificationStatusForSms save:  " + notificationStatusForSms);
	 * UserTemporarydetails details = new UserTemporarydetails(userData,
	 * userProfile.getEmail(), userProfile.getPhoneNo(), emailOtpData, phoneOtp);
	 * UserTemporarydetails dataByProfileId =
	 * userTemporarydetailsRepo.findByUserDetails_id(userData.getId()); log.
	 * info("temporary email and phone no profile details by user profile table id "
	 * + userProfile.getId()); if (dataByProfileId != null) {
	 * details.setId(dataByProfileId.getId());
	 * details.setCreatedOn(dataByProfileId.getCreatedOn());
	 * details.setModifiedOn(dataByProfileId.getModifiedOn()); }
	 * log.info("going to add data to user profile temporary data table");
	 * userTemporarydetailsRepo.save(details);
	 * log.info("after adding data to temporaray table");
	 * userData.setPreviousStatus(UserStatus.APPROVED.getCode());
	 * userData.setCurrentStatus(UserStatus.OTP_VERIFICATION_PENDING.getCode());
	 * userRepo.save(userData); log.info("user data changed"); }
	 */

	/*
	 * public ResponseEntity<?> updateProfile(UserProfile userProfile) { try {
	 * 
	 * log.info("inside into update user profile");
	 * log.info("user profile data:  :  " + userProfile); UserProfile
	 * userProfileData = userProfileRepo.findById(userProfile.getId());
	 * log.info("user  "); if
	 * (userProfile.getPassword().equals(userProfileData.getUser().getPassword())) {
	 * log.info("user profile data by id:  " + userProfileData);
	 * 
	 * long mainRole=roleCheck(userProfile.getRoles()); if(mainRole>0) {
	 * 
	 * User userData = userRepo.findByUserProfile_Id(userProfile.getId());
	 * log.info("user as userData:  " + userData);
	 * 
	 * saveUserTrail(userData, "User Management", "Updated", 41,
	 * userProfile.getPublicIp(), userProfile.getBrowser()); boolean emailExist =
	 * userProfileRepo.existsByEmailAndUser_CurrentStatusNot(userProfile.getEmail(),
	 * 21); boolean phoneExist =
	 * userProfileRepo.existsByPhoneNoAndUser_CurrentStatusNot(userProfile.
	 * getPhoneNo(), 21); if
	 * (!userProfile.getPhoneNo().equals(userProfileData.getPhoneNo()) &&
	 * !userProfile.getEmail().equals(userProfileData.getEmail())) {
	 * 
	 * if (emailExist) {
	 * 
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.Email_Exist.getMessage(), 409,
	 * RegistrationTags.Email_Exist.getTag()); return new ResponseEntity<>(response,
	 * HttpStatus.OK); }
	 * 
	 * if (phoneExist) {
	 * 
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.Phone_Exist.getMessage(), 409,
	 * RegistrationTags.Phone_Exist.getTag()); return new ResponseEntity<>(response,
	 * HttpStatus.OK); } setEmailAndPhoneDetails(userProfile, userData); } else if
	 * (!userProfile.getPhoneNo().equals(userProfileData.getPhoneNo()) &&
	 * userProfile.getEmail().equals(userProfileData.getEmail())) { if (phoneExist)
	 * {
	 * 
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.Phone_Exist.getMessage(), 409,
	 * RegistrationTags.Phone_Exist.getTag()); return new ResponseEntity<>(response,
	 * HttpStatus.OK); } setEmailAndPhoneDetails(userProfile, userData);
	 * 
	 * } else if (userProfile.getPhoneNo().equals(userProfileData.getPhoneNo()) &&
	 * !userProfile.getEmail().equals(userProfileData.getEmail())) { if (emailExist)
	 * {
	 * 
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.Email_Exist.getMessage(), 409,
	 * RegistrationTags.Email_Exist.getTag()); return new ResponseEntity<>(response,
	 * HttpStatus.OK); } setEmailAndPhoneDetails(userProfile, userData);
	 * 
	 * }
	 * 
	 * updateUserFields(userProfile, userProfileData);
	 * userProfileData.setUser(userData);
	 * log.info("userProfile data going to update: " + userProfileData);
	 * log.info("now going to save user profile data"); UserProfile output =
	 * userProfileRepo.save(userProfileData); log.info("user profile data is save");
	 * if (output != null) { // if(userProfile.getRoles()!=null) { //
	 * if(userProfile.getRoles().length!=0) { // int
	 * rolesOutput=updateRoles(userProfile.getRoles(), output.getUser().getId()); //
	 * log.info("user role update output:  "+rolesOutput); // } // }
	 * 
	 * log.info("if user profile table is update ");
	 * log.info("going to update user  questions and answer"); int i =
	 * updateUserSecurityquestion(userProfileData); log.info("question output:  " +
	 * i); String tag = ""; String msg = ""; if (userData.getCurrentStatus() ==
	 * UserStatus.APPROVED.getCode()) { tag = ProfileTags.PRO_SUCESS_MSG.getTag();
	 * msg = ProfileTags.PRO_SUCESS_MSG.getMessage(); } else { tag =
	 * ProfileTags.PRO_SUCESS_OTPMSG.getTag(); msg =
	 * ProfileTags.PRO_SUCESS_OTPMSG.getMessage();
	 * 
	 * } UpdateProfileResponse response = new UpdateProfileResponse(msg, 200,
	 * UserStatus.getUserStatusByCode(userData.getCurrentStatus()).getDescription(),
	 * output.getUser().getId(), tag); log.info("response send to user:  " +
	 * response); return new ResponseEntity<>(response, HttpStatus.OK); } else {
	 * log.info("user profile to update"); HttpResponse response = new
	 * HttpResponse(ProfileTags.PRO_FAIL_MSG.getMessage(), 204,
	 * ProfileTags.PRO_FAIL_MSG.getTag()); log.info("response send to user:  " +
	 * response); return new ResponseEntity<>(response, HttpStatus.OK); } } else {
	 * log.info("user profile to update"); HttpResponse response=new
	 * HttpResponse(RegistrationTags.REG_FAIL_ROLES_RESP.getMessage(),204,
	 * RegistrationTags.REG_FAIL_ROLES_RESP.getTag());
	 * log.info("response send to user:  "+response); return new
	 * ResponseEntity<>(response,HttpStatus.OK); } } else {
	 * log.info("user profile failed to update"); HttpResponse response = new
	 * HttpResponse(ProfileTags.PRO_CORRECT_PASS.getMessage(), 401,
	 * ProfileTags.PRO_CORRECT_PASS.getTag()); log.info("response send to user:  " +
	 * response); return new ResponseEntity<>(response, HttpStatus.OK); } } catch
	 * (Exception e) { log.info("if exception occur"); log.info(e.toString());
	 * HttpResponse response = new
	 * HttpResponse(RegistrationTags.COMMAN_FAIL_MSG.getMessage(), 409,
	 * RegistrationTags.COMMAN_FAIL_MSG.getTag()); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 * 
	 * }
	 */

	public void updateUserFields(UserProfile userProfile, UserProfile userProfileData) {
		userProfileData.setFirstName(userProfile.getFirstName());
		userProfileData.setMiddleName(userProfile.getMiddleName());
		userProfileData.setLastName(userProfile.getLastName());
		userProfileData.setPropertyLocation(userProfile.getPropertyLocation());
		userProfileData.setStreet(userProfile.getStreet());
		userProfileData.setVillage(userProfile.getVillage());
		userProfileData.setLocality(userProfile.getLocality());
		userProfileData.setDistrict(userProfile.getDistrict());
		userProfileData.setCommune(userProfile.getCommune());
		userProfileData.setPostalCode(userProfile.getPostalCode());
		userProfileData.setCountry(userProfile.getCountry());
		userProfileData.setProvince(userProfile.getProvince());
		userProfileData.setCountry(userProfile.getCountry());
		userProfileData.setQuestionList(userProfile.getQuestionList());
		userProfileData.setAuthorityName(userProfile.getAuthorityName());
		userProfileData.setAuthorityEmail(userProfile.getAuthorityEmail());
		userProfileData.setDesignation(userProfile.getDesignation());
		userProfileData.setAuthorityPhoneNo(userProfile.getAuthorityPhoneNo());
	}

	public HttpResponse updateStatus(User user) {
		HttpResponse response = new HttpResponse();
		try {

			User userDetails = userRepo.findById(user.getId());
			if (userDetails == null) {
				response.setStatusCode(204);
				response.setResponse("User  data not found");
				return response;
			} else {
				user.setCreatedOn(userDetails.getCreatedOn());
				user.setPassword(userDetails.getPassword());
				user.setUsername(userDetails.getUsername());
				userRepo.save(user);
				response.setStatusCode(200);
				response.setResponse("User status is update");
				return response;
			}
		} catch (Exception e) {
			response.setStatusCode(204);
			response.setResponse("User profile data fail to update");
			log.info("response send to user:  " + response);
			return response;
		}
	}

	public ResponseEntity<?> profileDataById(long id, long userId, String publicIp, String browser) {
		try {
			log.info("inside into edit profile");
			log.info("user id:  " + id);
			log.info("get user profile data by id below");
			UserProfile user = userProfileRepo.findById(id);
			if (user != null) {
				User userData = userRepo.findById(userId);
				if (userData != null) {
					saveUserTrail(userData, "Registration Request", "view", 8, publicIp, browser);
				}

				List<Long> rolesId = new ArrayList<Long>();
				List<RolesData> rolesList = new ArrayList<RolesData>();
				for (Userrole userRoles : user.getUser().getUserRole()) {
					rolesId.add(userRoles.getUsertypeData().getId());
					rolesList.add(new RolesData(userRoles.getUserData().getId(),
							userRoles.getUsertypeData().getUsertypeName()));
				}
				log.info("roles Ids :  " + rolesId);
				log.info("user roles :  " + rolesList);

				long[] arr = new long[rolesId.size()];
				arr = Longs.toArray(rolesId);

				user.setRolesList(rolesList);
				user.setRoles(arr);
				List<SystemConfigListDb> asTypeList = systemConfigRepo.getByTag("AS_TYPE");
				for (SystemConfigListDb asType : asTypeList) {
					Integer value = asType.getValue();
					if (user.getType() == value) {
						user.setAsTypeName(asType.getInterp());
					}
				}
				List<SystemConfigListDb> arrivaPortList = systemConfigRepo.getByTag("CUSTOMS_PORT");
				for (SystemConfigListDb port : arrivaPortList) {
					Integer value = port.getValue();
					if (user.getArrivalPort() == value) {
						user.setArrivalPortName(port.getInterp());
					}
				}
				List<SystemConfigListDb> nature_Of_Employment = systemConfigRepo.getByTag("Nature_Of_Employment");
				for (SystemConfigListDb emplomentType : nature_Of_Employment) {
					Integer value = emplomentType.getValue();
					if (user.getNatureOfEmployment() == value) {
						user.setNatureOfEmploymentInterp(emplomentType.getInterp());
					}
				}
				if (user.getPortAddress() != null) {
					PortAddress portAddress = portAddressRepoService.getById(user.getPortAddress());
					user.setPortAddressName(portAddress.getAddress());
				}
				if (Objects.nonNull(user.getUser().getApprovedBy())) {
					user.setApprovedBy(user.getUser().getApprovedBy());
				}
				SystemConfigurationDb filePath = systemConfigurationRepo.getDataByTag("upload_file_link");
				if (filePath != null) {
					if (user.getNidFilename() != null || !"null".equalsIgnoreCase(user.getNidFilename())) {
						user.setNidFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/NID/");
					}
					if (user.getPhotoFilename() != null || !"null".equalsIgnoreCase(user.getPhotoFilename())) {
						user.setPhotoFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/photo/");
					}
					if (user.getIdCardFilename() != null || !"null".equalsIgnoreCase(user.getIdCardFilename())) {
						user.setIdCardFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/IDCard/");
					}
					if (user.getVatFilename() != null || !"null".equalsIgnoreCase(user.getVatFilename())) {
						user.setVatFilePath(filePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + "/"
								+ user.getUser().getUsername() + "/Vat/");
					}
				}
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				HttpResponse response = new HttpResponse();
				response.setStatusCode(204);
				response.setResponse("User profile data not found");
				log.info("response send to user:  " + response);

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.info("exception occur here");
			log.info(e.toString());
			HttpResponse response = new HttpResponse();
			response.setStatusCode(409);
			response.setResponse("Oops something wrong happened");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	public HttpResponse AdminApproval(UserStatusRequest userStatus) {
		try {
			log.info("userStatus data: " + userStatus);
			log.info("inside admin approval controller");
			User user = userRepo.findById(userStatus.getId());
			if (user != null) {
				User userData = userRepo.findById(userStatus.getUserId());
				user.setApprovedBy(userData.getUsername());
				user.setRemark(userStatus.getRemark());
				user.setPreviousStatus(user.getCurrentStatus());
				user.setCurrentStatus(userStatus.getStatusValue());
				log.info("user data to update:  " + user);
				User output = userRepo.save(user);
				log.info("output got:  " + output);
				log.info("user data:  " + output);
				if (output != null) {
					String tag = "";
					String subject = "";
					String status = "";
					String feature = "";
					if (output.getCurrentStatus() == UserStatus.APPROVED.getCode()) {
						feature = "Approved";
						tag = "REG_ACCEPT_BY_CEIR_ADMIN";
						subject = "Registration Approval Notification for " + output.getUsername();
						status = "Approve";
						saveUserTrail(userData, "Registration Request", feature, 8, userStatus.getPublicIp(),
								userStatus.getBrowser());
					} else if (output.getCurrentStatus() == UserStatus.REJECTED.getCode()) {
						feature = "Rejected";
						tag = "REG_REJECT_BY_CEIR_ADMIN";
						subject = "Registration Rejection Notification for " + output.getUsername();
						status = "Reject";
						saveUserTrail(userData, "Registration Request", feature, 8, userStatus.getPublicIp(),
								userStatus.getBrowser());
					} else {
					}
					/*
					 * if(userData!=null) {
					 * saveUserTrail(userData,"Registration Request",feature,8,userData.getPublicIp(
					 * ),userData.getBrowser());
					 */

					List<Long> usertypes = new ArrayList<Long>();
					usertypes.add(4l);
					usertypes.add(5l);
					usertypes.add(6l);
					int authorityStatus = 0;
					if (!usertypes.contains(user.getUsertype().getId())) {
						authorityStatus = 1;
					}
					log.info("usertype: " + user.getUsertype().getId());
					log.info("authorityStatus: " + authorityStatus);
					boolean emailStatus = emailUtils.saveNotification(tag, output.getUserProfile(), 8,
							"Registration Request", status, output.getUsername(), "", ChannelType.EMAIL, "users",
							authorityStatus);
					log.info("emailStatus : " + emailStatus);
					HttpResponse response = new HttpResponse();
					response.setStatusCode(200);
					response.setResponse("user status has been update");
					return response;
				} else {
					HttpResponse response = new HttpResponse();
					response.setStatusCode(204);
					response.setResponse("user status failed to update");
					return response;
				}
			} else {
				HttpResponse response = new HttpResponse();
				response.setStatusCode(204);
				response.setResponse("please enter correct user id");
				return response;
			}

		} catch (Exception e) {
			HttpResponse response = new HttpResponse();
			response.setStatusCode(409);
			response.setResponse("Oops something wrong happened");
			return response;
		}
	}

	public String emailContent(MessageConfigurationDb msgConfigDb, UserProfile profile, String otp) {
		log.info("tag:  " + msgConfigDb.getTag());
		log.info("value: " + msgConfigDb.getValue());
		log.info("user data:  " + profile.getUser());
		String emailBody = null;

		emailBody = msgConfigDb.getValue();
		HashMap<String, String> map = contentMap(emailBody, profile, otp);
		String[] words = { "<First name>", "<userID>", "<Reason>", "<otp>", "<number>", "<user_type>" };
		for (int i = 0; i < words.length; i++) {
			log.info("tag: " + words[i]);
			if (emailBody.contains(words[i])) {
				log.info("if this tag is present in emailContent");
				emailBody = map.get(words[i]);
			}
		}
		return emailBody;

	}

	public String getsubject(MessageConfigurationDb msgConfigDb, UserProfile profile, String otp) {
		log.info("tag:  " + msgConfigDb.getTag());
		String subject = null;

		subject = msgConfigDb.getSubject();
		HashMap<String, String> map = contentMap(subject, profile, otp);
		String[] words = { "<First name>", "<userID>", "<Reason>", "<otp>", "<number>", "<user_type>" };
		for (int i = 0; i < words.length; i++) {
			log.info("tag: " + words[i]);
			if (subject.contains(words[i])) {
				log.info("if this tag is present in emailContent");
				subject = map.get(words[i]);
			}
		}
		return subject;

	}

	public HashMap<String, String> contentMap(String emailBody, UserProfile profile, String otp) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("<First name>", emailBody = emailBody.replaceAll("<First name>", profile.getFirstName()));
		map.put("<userID>", emailBody = emailBody.replaceAll("<userID>", profile.getUser().getUsername()));
		map.put("<Reason>", emailBody = emailBody.replaceAll("<Reason>", profile.getUser().getRemark()));
		map.put("<otp>", emailBody = emailBody.replaceAll("<otp>", otp));
		map.put("<number>", emailBody = emailBody.replaceAll("<number>", otp));
		map.put("<user_type>",
				emailBody = emailBody.replaceAll("<user_type>", profile.getUser().getUsertype().getUsertypeName()));
		return map;
	}

	public int saveUserTrail(User user, String feature, String subFeature, long featureId) {
		try {

//			AuditTrail auditTrail=new AuditTrail(user.getId(), user.getUsername(),
//					user.getUsertype().getId(),user.getUsertype().getUsertypeName(), featureId,
//					feature, subFeature,"0","NA",user.getUsertype().getUsertypeName());

			AuditTrail auditTrail = new AuditTrail(0, "Success", "", feature, subFeature, "",
					propertiesReader.moduleName, propertiesReader.serverName);

			log.info("going to save audit trail=" + auditTrail);
			AuditTrail output = audiTrailRepoService.saveAuditTrail(auditTrail);
			if (output != null) {
				log.info("audit trail sucessfully save");
			} else {
				log.info("user trail fails to save");
			}
			return 1;
		} catch (Exception e) {
			log.info(e.toString());
			return 0;
		}
	}

	public int saveUserTrail(long userId, String username, String userType, long userTypeId, String feature,
			String subFeature, long featureId, String publicIp, String browser) {
		try {
			AuditTrail auditTrail = new AuditTrail(0, "Success", "", feature, subFeature, "",
					propertiesReader.moduleName, propertiesReader.serverName);

			//AuditDB aauditDB = new AuditDB(userId, username, userTypeId, userType, featureId, Features.Alert_Management,
					//SubFeatures.VIEW_ALL, "", "NA", "", publicIp, browser);

//                    new AuditTrail(userId, username,
//                    userTypeId, userType, featureId,
//                    feature, subFeature, "0", "NA", userType, publicIp, browser);

			auditDb.save(new AuditDB(userId, username, userTypeId, userType, featureId, feature, subFeature, "", "NA",
					userType, publicIp, browser));
			log.info("going to save audit trail with request:::::%%%%");
			AuditTrail output = audiTrailRepoService.saveAuditTrail(auditTrail);
			// AuditDB au=alertDb.save(aauditDB);
			if (output != null) {
				log.info("audit trail sucessfully save");
			} else {
				log.info("user trail fails to save");
			}
			return 1;
		} catch (Exception e) {
			log.info(e.toString());
			return 0;
		}
	}

	public boolean delete(long userId) {
		// TODO Auto-generated method stub
		try {
			userRepo.deleteById(userId);
			return true;
		} catch (Exception e) {
			log.info(e.toString());
			return false;
		}

	}

	public ResponseEntity<?> soft_delete(int currentStatus, String username) {
		// TODO Auto-generated method stub
		User user = userRepo.findByUsername(username);
		try {
			log.info("we're updating user" + username + "with current_status value is" + currentStatus
					+ " and previous_status was" + user.getCurrentStatus());
			userRepo.setStatusForUser(currentStatus, user.getCurrentStatus(), username);
			return new ResponseEntity<>(true, HttpStatus.OK);
		} catch (Exception e) {
			log.info(e.toString());
			return new ResponseEntity<>(false, HttpStatus.EXPECTATION_FAILED);
		}

	}

	public int saveUserTrail1(User user, String feature, String subFeature, long featureId, String publicIP,
			String browser) {
		try {

			// AuditTrail auditTrail = new AuditTrail(0, "Success", "", feature, subFeature,
			// "" ,propertiesReader.moduleName,propertiesReader.serverName);

//                    new AuditTrail(user.getId(), user.getUsername(),
//                    user.getUsertype().getId(), user.getUsertype().getUsertypeName(), featureId,
//                    feature, subFeature, "0", "NA", user.getUsertype().getUsertypeName(), publicIP, browser);

			// log.info("going to save audit trail" + auditTrail);
			auditDb.save(new AuditDB(user.getUserId(), user.getUsername(), Long.valueOf(user.getUserTypeId()),
					user.getUserType(), Long.valueOf(user.getFeatureId()), feature, subFeature, "", "NA", user.getUsertype().getUsertypeName(),
					user.getPublicIp(), user.getBrowser()));
			// AuditTrail output = audiTrailRepoService.saveAuditTrail(auditTrail);

			return 1;
		} catch (Exception e) {
			log.info(e.toString());
			return 0;
		}
	}

	public void saveUserTrail(User userData, String registration_Request, String feature, int i, String publicIp,
			String browser) {
		try {
			// AuditTrail auditTrail = new AuditTrail(0, "Success", "",
			// registration_Request, feature,
			// "",propertiesReader.moduleName,propertiesReader.serverName);

			log.info("going to save in audit tail tables with userType ." +userData.getUsertype().getUsertypeName());

			auditDb.save(new AuditDB(userData.getUserId(), userData.getUsername(),
					Long.valueOf(userData.getUserTypeId()), userData.getUsertype().getUsertypeName(), Long.valueOf(userData.getFeatureId()),
					feature, "Login", "", "NA", userData.getUsertype().getUsertypeName(), publicIp, browser));
		} catch (Exception e) {
			log.info(e.toString());
		}
	}
	
	
	
}
