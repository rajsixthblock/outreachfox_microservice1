package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.SubscriptionRepository;



@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	public Subscription create(Subscription payload) throws Exception {
		if(subscriptionRepository.existsByPlanName(payload.getPlanName())) {
			throw new BadRequestException("Plan already exist with this Name");
		}else {
			try {
				Subscription subscription = subscriptionRepository.save(payload);
				return subscription;
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		return null;
	}
	
	public List<Subscription> getAll() throws Exception{
		try {
			List<Subscription> subscriptions = (List<Subscription>) subscriptionRepository.findAll();
			return subscriptions;
		}catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public Optional<Subscription> getById(String id) throws Exception {
		try {
			Optional<Subscription> subscription = subscriptionRepository.findById(id);
			return subscription;
		}catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public Subscription updateSubscription(String id, Subscription payload) throws Exception {
		if(subscriptionRepository.existsById(id)) {
			Subscription subscription = subscriptionRepository.getById(id);
			if(subscription.getPlanName().equals(payload.getPlanName())){
				subscription = setsubscriptionData(payload, subscription);
			} 
			else {
				if(subscriptionRepository.existsByPlanName(payload.getPlanName())) {
					throw new BadRequestException("Plan already exist with this Name");
				}else {
					subscription = setsubscriptionData(payload, subscription);
				}
			}
			try {
				subscription =subscriptionRepository.save(subscription);
				return subscription;
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}else {
			throw new DetailsNotFound("Plan details not found.!"); 
		}
		return null;
	}
	
	private Subscription setsubscriptionData(Subscription payload, Subscription subscription) {
		if(payload.getPlanName() != null) {
			subscription.setPlanName(payload.getPlanName());
		}
		if(payload.getEmailsLimit() != 0) {
			subscription.setEmailsLimit(payload.getEmailsLimit());
		}
		if(payload.getAmount() != 0) {
			subscription.setAmount(payload.getAmount());
		}
		if(payload.getCurrencyType() != null) {
			subscription.setCurrencyType(payload.getCurrencyType());
		}
		if(payload.isStatus()) {
			subscription.setStatus(payload.isStatus());
		}else if(!payload.isStatus()) {
			subscription.setStatus(payload.isStatus());
		}
		return subscription;
	}

	public String deleteSubscriptionByID(String id) throws Exception {
		if(subscriptionRepository.existsById(id)) {
			try {
				subscriptionRepository.deleteById(id);
				return "Plan deleted successfully";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("Plan does not exist.!"); 
			return null;
	}
}
