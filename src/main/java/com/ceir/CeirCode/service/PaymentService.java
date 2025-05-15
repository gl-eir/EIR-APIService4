package com.ceir.CeirCode.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserPayment;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.constants.Paymentstatus;
import com.ceir.CeirCode.othermodel.PaymentParam;
import com.ceir.CeirCode.othermodel.PaymentParameters;
import com.ceir.CeirCode.othermodel.PaymentResponse;
import com.ceir.CeirCode.repo.app.PaymentRepo;
import com.ceir.CeirCode.repo.app.UserProfileRepo;
import com.ceir.CeirCode.repoService.SystemConfigDbRepoService;
import com.ceir.CeirCode.response.CallBackResponse;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.response.tags.PaymentTags;
import com.ceir.CeirCode.util.GenerateRandomDigits;
import com.ceir.CeirCode.util.HttpResponse;
import com.ceir.CeirCode.util.Utility;

@Service
public class PaymentService {

	@Autowired
	GenerateRandomDigits randomDigits;
	
	@Autowired
	Utility utility;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	SystemConfigDbRepoService systemConfigService;
	
	@Autowired
	PaymentRepo paymentRepo;
	
	@Autowired  
	UserProfileRepo  userProfileRepo;
	
//	@Autowired
//	PaymentFeign paymentFeign;
	
	public ResponseEntity<?> payment(PaymentParam payment,HttpServletResponse http){
		log.info("inside payment controller");
		log.info("data given: "+payment);
		String txnId=utility.currentDateTimeInSeconds()+randomDigits.getAlphaNumericString(5);
		log.info("here generated txn id: "+txnId);
		//SystemConfigurationDb urlDb=systemConfigService.getDataByTag("Payment_Url");
		//log.info("url from db:  "+urlDb.getValue());
		//String url=urlDb.getValue().replaceAll("T", txnId);
		UserPayment userPayment=new UserPayment();
	        String merchantId="ec000269";
	        String key="4d6cd0cbb5594bc8fd481d47e39727f70a93e97f";
	        String data=merchantId+txnId+payment.getAmount();
	        String hash=generateHMAC(key,data);
			User user=new User(payment.getUserId());
			UserPayment paymentData=new UserPayment(txnId,payment.getAmount(),user,Paymentstatus.Fail.getCode());
			try {
			 userPayment=paymentRepo.save(paymentData);
			}
			catch(Exception e) {
				 log.info("error occurs while add userPayment entry in db:  ");
			}
		String returnUrl="http://172.24.2.65:9501/CEIRCode/payment/callbackurl/";
		log.info("return url: "+returnUrl);
		String encodedURL = Base64.getUrlEncoder().encodeToString(returnUrl.getBytes());
		log.info("encoded return url: "+encodedURL);
		String paymentUrl="https://sandbox.payway.com.kh/sandbox/api/269/";
		if(userPayment!=null) {
			log.info("user id : "+userPayment.getUserPayment().getId());
			UserProfile profile=userProfileRepo.findByUser_Id(userPayment.getUserPayment().getId());
			log.info("profile data: "+profile);
//            PaymentParam paymentParam=new PaymentParam(txnId,payment.getAmount(),hash,userPayment.getUserPayment().getUserProfile().getFirstName(),
//            		userPayment.getUserPayment().getUserProfile().getLastName(),userPayment.getUserPayment().getUserProfile().getPhoneNo(),
//            		userPayment.getUserPayment().getUserProfile().getEmail(),"","");
			 PaymentParameters paymentParam=new PaymentParameters(txnId, payment.getAmount(), hash,profile.getFirstName(),
					 profile.getLastName(),profile.getPhoneNo(),profile.getEmail(),encodedURL,"",paymentUrl);
			 log.info("now going to call payment parameters: ");
		//	 Integer resp=paymentFeign.paymentUrl(paymentParam);
		//	 log.info("response got from url: "+resp);
//			PaymentParam paymentParam=new PaymentParameters(tran_id, amount, hash, firstname, lastname, phone, email, return_url, return_params)
			 GenricResponse response=new GenricResponse(200,PaymentTags.url_Sucess.getMessage(),PaymentTags.url_Sucess.getTag(),paymentParam);
            log.info("response send: "+response);
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}
		else {
			log.info("if user payment details are not saving to the table");
			GenricResponse response=new GenricResponse(500,PaymentTags.url_Fail.getMessage(),PaymentTags.url_Fail.getTag());
            log.info("response send: "+response);
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}

	}
	
	public ResponseEntity<?> paymentStatus(CallBackResponse paymentResp){
		log.info("inside  paymentStatus   controller");  
		log.info(" txnId:  "+paymentResp.getTran_id() +"status: "+paymentResp.getStatus());      
		log.info("get userTypeFeature  data by  id below"); 
		UserPayment paymentData=new UserPayment();
		try {
			paymentData=paymentRepo.findByTxnId(paymentResp.getTran_id());	
		}
		catch(Exception e) {
			log.info(e.getMessage());
			log.info(e.toString());
		} 
		if(paymentData!=null) {
		   Integer paymentStatus;
			if(paymentResp.getStatus()==Paymentstatus.Success.getCode()) {
				paymentStatus=Paymentstatus.Success.getCode();
				/*
				 * } else { paymentStatus=Paymentstatus.Fail.getCode(); }
				 */
			paymentData.setStatus(paymentStatus);
		    UserPayment output=new UserPayment();
			try {
				output=paymentRepo.save(paymentData);
			}
			catch(Exception e) {
				log.info(e.getMessage());
				log.info(e.toString());
			}
			
			if(output!=null) {
				HttpResponse response=new HttpResponse("Payment status successfully update",
						200);
				log.info("response send:  "+response);
				return new ResponseEntity<>(response,HttpStatus.OK);	
			}
			else {
				HttpResponse response=new HttpResponse("Something wrong happened..  please try after some time",
						500);
				log.info("response send"+response);
				return new ResponseEntity<>(response,HttpStatus.OK);	
			} 
		}
		else {
			//paymentStatus=Paymentstatus.Fail.getCode();
			HttpResponse response=new HttpResponse("Payment failed",
					422);
			log.info("response send:  "+response);
			return new ResponseEntity<>(response,HttpStatus.OK);	

		}
		}    
		else { 
			HttpResponse response=new HttpResponse("transation id id wrong",422);
			log.info("response send"+response);
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}
	}
	private static final String HMAC_SHA512 = "HmacSHA512";
	public String generateHMAC(String key,String data) {
		log.info("inside HMAC SHA512 code generation ");
		 Mac sha512Hmac;
	        String result;
	        try {
	            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
	            sha512Hmac = Mac.getInstance(HMAC_SHA512);
	            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
	            sha512Hmac.init(keySpec);
	            byte[] macData = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
	            // Can either base64 encode or put it right into hex
	            result = Base64.getEncoder().encodeToString(macData);
	            //result = bytesToHex(macData);
	            log.info("hmac generated value: "+result);
	            return result;
	        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
	        	log.info("error in generating hmac");
	        	log.info(e.toString());
	            return null;
	        } finally {
	            // Put any cleanup here
	        	log.info("HMAC block complete");
	        	
	        }
	    }
}