package com.companyservice.companyservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
@RestController
@Logging
@Authorization
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@PostMapping("/plan/create")
	public ResponseEntity<?> creation(@RequestBody @Valid Subscription payload) throws Exception{
		Subscription subscription =  subscriptionService.create(payload);
		if(subscription != null) {
			return new ResponseEntity<>(subscription, HttpStatus.CREATED);
		}else {
			throw new BadRequestException("Plan Creation failed");
		}
	}
	
	
	@GetMapping("/getAllPlans/{page}/{limit}")
	public ResponseEntity<?> getPlans(@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Subscription> subscriptions = subscriptionService.getAll(page, limit);
		Page<Subscription> subscriptions = subscriptionService.getAll(page, limit);
		if(subscriptions != null) {
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		}else {
			throw new DetailsNotFound("No plans found");
		}
	}
	@GetMapping("/getAllPlans/{PlanType}/{page}/{limit}")
	public ResponseEntity<?> getPlansByType(@PathVariable int page,@PathVariable int limit,@PathVariable String PlanType) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Subscription> subscriptions = subscriptionService.getAll(page, limit);
		Page<Subscription> subscriptions = subscriptionService.getAllByType(page, limit,PlanType);
		if(subscriptions != null) {
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		}else {
			throw new DetailsNotFound("No plans found");
		}
	}
	
	@GetMapping("/plan/getByID/{id}")
	public ResponseEntity<?> getByPlanID(@PathVariable String id) throws Exception {
		Subscription subscriptionDetails =  subscriptionService.getById(id);
		if(subscriptionDetails != null) {
			return new ResponseEntity<>(subscriptionDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Plan details not found");
		}
	}
	
	@PutMapping("/plan/update/{id}")
	public ResponseEntity<?> updatePlan(@PathVariable String id, @RequestBody Subscription payload)throws Exception {
		Subscription subscription =  subscriptionService.updateSubscription(id, payload);
		if(subscription != null) {
			return new ResponseEntity<>(subscription, HttpStatus.OK);
		}else {
			throw new DetailsNotFound("Plan details not found");
		}
	}
	
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
