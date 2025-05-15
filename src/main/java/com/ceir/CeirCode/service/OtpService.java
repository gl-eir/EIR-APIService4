package com.ceir.CeirCode.service;
import java.util.Random;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.EmailDetails;
import com.ceir.CeirCode.util.HttpClient;
@Service
public class OtpService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());	
	String SALTCHARS = "1234567890";
	public String phoneOtp(String mobile) {
		log.info("inside phone otp");
		HttpClient client=new HttpClient();
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		String otpval =salt.toString();
		return otpval;
	}

	public boolean emailOtp(EmailDetails details)     {
		try {
		log.info("inside email otp");    
			boolean b=true;
			log.info("email send:   "+b);
			return b;   
		}  
		catch(Exception e){
			log.info("exception occur when sendinf email");     
			return false; 
			
		}
	}
}
