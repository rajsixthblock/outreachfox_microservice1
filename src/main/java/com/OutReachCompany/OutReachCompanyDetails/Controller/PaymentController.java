package com.OutReachCompany.OutReachCompanyDetails.Controller;

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
import com.OutReachCompany.OutReachCompanyDetails.Exception.BadRequestException;
import com.OutReachCompany.OutReachCompanyDetails.Exception.DetailsNotFound;
import com.OutReachCompany.OutReachCompanyDetails.Service.PaymentService;
import com.OutReachCompany.OutReachCompanyDetails.model.Payment;

@RestController
@Authorization
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
	
	@GetMapping("/payment/getAllPayments")
	public ResponseEntity<?> getPayments() throws Exception {
		List<Payment> newPayment =  paymentService.getPaymentDetails();
		if(!newPayment.isEmpty()) {
			return new ResponseEntity<>(newPayment, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Payment details not found");
		}
	}
	
	@GetMapping("/payment/getAll/{companyId}")
	public ResponseEntity<?> getPayments(@PathVariable String companyId) throws Exception {
		List<Payment> newPayment =  paymentService.getPaymentDetails(companyId);
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
