//package com.ceir.CeirCode.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.ceir.CeirCode.repo.app.UserRepo;
//
//@Service
//public class UserDetailsServiceImpl {
//	@Autowired
//	UserRepo userRepository;
//	
//	@Bean
//	public UserDetailsService userDetailsService() {
//	    return username -> userRepository.getByUsername(username)
//	    		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
//	}
//}
