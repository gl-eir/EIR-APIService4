package com.ceir.CeirCode.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceir.CeirCode.othermodel.PaymentParam;
import com.ceir.CeirCode.othermodel.PaymentResponse;
import com.ceir.CeirCode.response.CallBackResponse;
import com.ceir.CeirCode.response.GenricResponse;
import com.ceir.CeirCode.service.PaymentService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/payment")
public class PaymentGateway {

	@Autowired
	PaymentService paymentService;

	@ApiOperation(value = "call payment url", response = GenricResponse.class)
	@PostMapping("/url")
	public ResponseEntity<?> paymentUrl(@RequestBody PaymentParam paymentParam,HttpServletResponse http){
		 return paymentService.payment(paymentParam,http);
	}   
	
	@ApiOperation(value = "change payment status", response = PaymentResponse.class)
	@GetMapping("/callbackurl/")
	public ResponseEntity<?> updateUserTypeFeaturePeriod(CallBackResponse paymentResp){
		return   paymentService.paymentStatus(paymentResp);
	}   
}
