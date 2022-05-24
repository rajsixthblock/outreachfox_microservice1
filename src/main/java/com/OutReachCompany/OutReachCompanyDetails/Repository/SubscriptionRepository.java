package com.OutReachCompany.OutReachCompanyDetails.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.OutReachCompany.OutReachCompanyDetails.model.Subscription;


@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, String> {
	@Query(value = "SELECT * FROM SUBSCRIPTION WHERE PLAN_NAME = ?1", nativeQuery = true)
	Subscription findByPlanName(String planName);
	
	Subscription getById(String id);
	
	boolean existsByPlanName(String planName);
}
