package com.ceir.CeirCode.util;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.print.attribute.standard.DateTimeAtCompleted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.lang.NonNull;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ceir.CeirCode.model.app.MessageConfigurationDb;
import com.ceir.CeirCode.model.app.Notification;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.constants.ChannelType;
import com.ceir.CeirCode.repo.app.MessageConfigurationDbRepository;
import com.ceir.CeirCode.repo.app.NotificationRepository;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.ConfigurationManagementServiceImpl;
import com.ceir.CeirCode.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Component
public class NotificationUtil {

	private Logger logger = (Logger) LogManager.getLogger(NotificationUtil.class);


	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;

	@Autowired
	MessageConfigurationDbRepository messageConfigurationDbRepository;

	@Autowired
	UserService userService;

	@Autowired
	NotificationRepository notificationRepo;

    public boolean saveNoti(Notification noti)
    {
    	try {
    		notificationRepo.save(noti);
    		return true;
    	}
    	catch(Exception e) {
    		logger.info("error occurs while saving notification");
    		logger.info(e.toString());
    		return false;
    	}
    }
    
    


	public boolean saveNotification(@NonNull String tag, UserProfile userProfileData, long featureId, String featureName, String subFeature, String featureTxnId,String otp,String channelType,String referTable,int authorityStatus) {
		try {
			String emailBody="";
			MessageConfigurationDb messageDB = new MessageConfigurationDb();
		
			messageDB = messageConfigurationDbRepository.getByTagAndActive(tag, 0);
				logger.info("messageDB data by tag: "+messageDB);
				emailBody=userService.emailContent(messageDB, userProfileData, otp);		
		String subject=userService.getsubject(messageDB, userProfileData, otp);
			logger.info("email body=  "+emailBody);
			Notification notification=new Notification();
			notification.setChannelType(channelType);
			notification.setCreatedOn(LocalDateTime.now());
			notification.setFeatureId(featureId);
			notification.setFeatureName(featureName);
			notification.setMessage(emailBody);
			notification.setModifiedOn(LocalDateTime.now());
			notification.setSubFeature(subFeature);
			notification.setUserId(userProfileData.getUser().getId());
			notification.setFeatureTxnId(featureTxnId);
			notification.setStatus(1); 
			notification.setSubject(subject);
			notification.setRetryCount(0);
			notification.setReferTable(referTable);
			notification.setAuthorityStatus(authorityStatus);
			String receiverType="";
			if(userProfileData.getUser().getUsertype().getId()==7 ) {
				receiverType="Custom";
			}
			else if(userProfileData.getUser().getUsertype().getId()==10 ) {
				receiverType="TRC";
			}
			else {
				
			}
			notification.setReceiverUserType(receiverType);
			Notification output=notificationRepo.save(notification);
			if(output!=null) {
				logger.info("notification sucessfully saved");
			}
			else {
				logger.info("notification failed to save");
			}

			return Boolean.TRUE;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Boolean.FALSE;
		}
	}



	

	
	public boolean saveNotification(@NonNull String tag, UserProfile userProfile, long featureId,
			String featureName, String subFeature, String featureTxnId, String txnId,
			Map<String, String> placeholders, String roleType, String receiverUserType, String referTable) {
			try {
			MessageConfigurationDb messageDB = messageConfigurationDbRepository.getByTagAndActive(tag, 0);
			logger.info("Message for tag [" + tag + "] " + messageDB);

			if(Objects.isNull(messageDB)) {
			return Boolean.TRUE;
			}

			String message = messageDB.getValue();
			String subject=messageDB.getSubject();
			subject=subject.replace("<XXX>", txnId);
			logger.info("after replace subject value ::"+subject);
			// Replace Placeholders from message.
			if(Objects.nonNull(placeholders)) {
			for (Map.Entry<String, String> entry : placeholders.entrySet()) {
			logger.info("Placeholder key : " + entry.getKey() + " value : " + entry.getValue());
			message = message.replaceAll(entry.getKey(), entry.getValue());
			subject = subject.replaceAll(entry.getKey(), entry.getValue());
			}
			}
			
			logger.info("final subject value ::"+subject);
			
			// Save email in notification table.
			GenricResponse genricResponse = configurationManagementServiceImpl.saveNotification(ChannelType.EMAIL, message,
			userProfile.getUser().getId(), featureId, featureName, subFeature, featureTxnId,
			subject, 0, referTable, roleType, receiverUserType);

			logger.info("A new notification with id [" + genricResponse.getTxnId() + "] is saved.");

			return Boolean.TRUE;
			}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Boolean.FALSE;
			}
	}
	

}

