package com.microservices.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
	
	private static Logger logger = LoggerFactory.getLogger(FallbackController.class);
	
	@GetMapping("/user")
	public ResponseEntity<String> userFallback() {
		logger.error("User service is unavailable!");
		return new ResponseEntity<>("User service is unavailable!", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/product")
	public ResponseEntity<String> productFallback() {
		logger.error("Product service is unavailable!");
		return new ResponseEntity<>("Product service is unavailable!", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/cart")
	public ResponseEntity<String> cartFallback() {
		logger.error("Cart service is unavailable!");
		return new ResponseEntity<>("Cart service is unavailable!", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/payment")
	public ResponseEntity<String> paymentFallback() {
		logger.error("Payment service is unavailable!");
		return new ResponseEntity<>("Payment service is unavailable!", HttpStatus.NOT_FOUND);
	}
}
