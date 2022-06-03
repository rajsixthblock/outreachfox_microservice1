package com.companyservice.companyservice.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.PaymentService;


@RestController
@Authorization
@Logging
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payment/create/{companyId}/{subscriptionId}")
	public ResponseEntity<?> save(@PathVariable String companyId,@PathVariable String subscriptionId,  @RequestBody @Valid Payment payload) throws Exception {
		Payment newPayment = paymentService.savePayment(companyId,subscriptionId, payload);
		if(newPayment.getPaymentId() != null) {
			return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Payment creation failed");
		}
	}
	
	@GetMapping("/payment/getAllPayments/{page}/{limit}")
	public ResponseEntity<?> getPayments(@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		List<Payment> newPayment =  paymentService.getPaymentDetails(page, limit);
		if(!newPayment.isEmpty()) {
			return new ResponseEntity<>(newPayment, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Payment details not found");
		}
	}
	
	@GetMapping("/payment/getAll/{companyId}/{page}/{limit}")
	public ResponseEntity<?> getPayments(@PathVariable String companyId,@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			System.out.println("1");
			page = 1;
		}
		if(limit == 0) {
			System.out.println(limit);
			limit = 10;
		}
		List<Payment> newPayment =  paymentService.getPaymentDetails(page,limit,companyId);
		if(newPayment != null) {
			return new ResponseEntity<>(newPayment, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Payment details not found");
		}
	}
	
	@GetMapping("/payment/getByID/{id}")
	public ResponseEntity<?> getByPaymentID(@PathVariable String id) throws Exception {
		Optional<Payment> newPayment =  paymentService.getPaymentID(id);
		if(!newPayment.isEmpty()) {
			return new ResponseEntity<>(newPayment, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Payment details not found");
		}
	}
	
	@PutMapping("/payment/update/{id}")
	public ResponseEntity<?> updatePayment(@PathVariable String id, @RequestBody Payment payload) throws Exception {
		Payment newPayment =  paymentService.updatePayment(id, payload);
		if(newPayment != null) {
			return new ResponseEntity<>(newPayment, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Payment details not found");
		}
	}
	
	@DeleteMapping("/payment/delete/{id}")
	public ResponseEntity<?> deletePayment(@PathVariable String id) throws Exception {
		String response =  paymentService.deletePaymentByID(id);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Payment details not found");
		}
		
	}
}
