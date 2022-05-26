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
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.SubscriptionService;


@RestController
@Logging
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@Authorization
	@PostMapping("/plan/create")
	public ResponseEntity<?> creation(@RequestBody @Valid Subscription payload) throws Exception{
		Subscription subscription =  subscriptionService.create(payload);
		if(subscription.getId() != null) {
			return new ResponseEntity<>(subscription, HttpStatus.CREATED);
		}else {
			throw new BadRequestException("Plan Creation failed");
		}
	}
	
	@Authorization
	@GetMapping("/getAllPlans")
	public ResponseEntity<?> getPlans() throws Exception {
		List<Subscription> subscriptions = subscriptionService.getAll();
		if(!subscriptions.isEmpty()) {
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		}else {
			throw new DetailsNotFound("No plans found");
		}
	}
	
	@Authorization
	@GetMapping("/plan/getByID/{id}")
	public ResponseEntity<?> getByPlanID(@PathVariable String id) throws Exception {
		Optional<Subscription> subscriptionDetails =  subscriptionService.getById(id);
		if(!subscriptionDetails.isEmpty()) {
			return new ResponseEntity<>(subscriptionDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Plan details not found");
		}
	}
	
	@Authorization
	@PutMapping("/plan/update/{id}")
	public ResponseEntity<?> updatePlan(@PathVariable String id, @RequestBody Subscription payload)throws Exception {
		Subscription subscription =  subscriptionService.updateSubscription(id, payload);
		if(subscription != null) {
			return new ResponseEntity<>(subscription, HttpStatus.OK);
		}else {
			throw new DetailsNotFound("Plan details not found");
		}
	}
	
	@Authorization
	@DeleteMapping("/plan/delete/{id}")
	public ResponseEntity<?> deletePlan(@PathVariable String id) throws Exception {
		String response =  subscriptionService.deleteSubscriptionByID(id);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Plan details not found");
		}
		
	}
}
