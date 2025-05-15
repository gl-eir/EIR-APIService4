package com.ceir.CeirCode.controller;

import java.util.List;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserPasswordHistory;
import com.ceir.CeirCode.repo.app.UserPasswordHistoryRepo;
import com.ceir.CeirCode.repo.app.UserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
public class EncryptDBPassword {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	UserRepo userRepo;
	
	@Autowired
    UserPasswordHistoryRepo userPassHistoryRepo;

	@Autowired
	PooledPBEStringEncryptor pooledPBEStringEncryptor;
	
	@ApiOperation(value="Encrypt the password in users table")
	@PostMapping("/password-encryption")
	public ResponseEntity<?> encryptPassword() {
		encryptUserPassword();
		encryptUserPasswordHistory(); 
		return ResponseEntity.ok("Passwords encrypted successfully.");
	}
	
	public ResponseEntity<?> encryptUserPassword() {
		List<User> users = userRepo.findAll();	
		 for (User user : users) {
	            String decryptedPassword = user.getPassword();
	            if (!isEncrypted(decryptedPassword)) {
	            	 String encryptedPassword = pooledPBEStringEncryptor.encrypt(decryptedPassword);
	                 user.setPassword(encryptedPassword);
	                 userRepo.save(user); 
	                 log.info("Encrypted password for user: {}", user.getUsername());
	            };
	        }
		 return ResponseEntity.ok("Passwords encrypted successfully in user table.");
		
	}
	
	public ResponseEntity<?> encryptUserPasswordHistory() {
		List<UserPasswordHistory> users = userPassHistoryRepo.findAll();	
		 for (UserPasswordHistory userPasswordHistory : users) {
	            String decryptedPassword = userPasswordHistory.getPassword();
	            if (!isEncrypted(decryptedPassword)) {
	            	 String encryptedPassword = pooledPBEStringEncryptor.encrypt(decryptedPassword);
	            	 userPasswordHistory.setPassword(encryptedPassword);
	            	 userPassHistoryRepo.save(userPasswordHistory); 
	                 log.info("Encrypted password for userID: {}", userPasswordHistory.getId());
	            };
	        }
		 return ResponseEntity.ok("Passwords encrypted successfully in user_password_history Table.");
		
	}
	
	
	private boolean isEncrypted(String password) {
        try {
            pooledPBEStringEncryptor.decrypt(password); 
            return true;
        } catch (Exception e) {
            return false; 
        }
    }
}
