package com.companyservice.companyservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Subscription;



@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, String> ,PagingAndSortingRepository<Subscription, String>{
	@Query(value = "SELECT * FROM SUBSCRIPTION WHERE PLAN_NAME = ?1", nativeQuery = true)
	Subscription findByPlanName(String planName);
	
	Subscription getById(String id);
	
	boolean existsByPlanName(String planName);
}
