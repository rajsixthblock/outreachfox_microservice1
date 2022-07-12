package com.companyservice.companyservice.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;


@Repository
public interface PaymentRepository extends CrudRepository<Payment, String>,PagingAndSortingRepository<Payment, String>{

	boolean existsBytransactionId(String transactionId);
	Payment getById(String id);
	List<Payment> getByCompanyId(Company companyId);
	Page<Payment> getByCompanyId(Company companyId,Pageable paging);
	Page<Payment> getByCompanyIdAndSubscriptionIdAndPaidDateBetween(Company companyId,Subscription subscriptionId,Date startDate,Date endDate,Pageable paging);
	Page<Payment> getBySubscriptionIdAndPaidDateBetween(Subscription subscriptionId,Date startDate,Date endDate,Pageable paging);
	Page<Payment> getByCompanyIdAndSubscriptionId(Company companyId,Subscription subscriptionId,Pageable paging);
	Page<Payment> getByCompanyIdAndPaidDateBetween(Company companyId,Date startDate,Date endDate,Pageable paging);
	Page<Payment> getBySubscriptionId(Subscription subscriptionId,Pageable paging);
	Page<Payment> getByPaidDateBetween(Date startDate,Date endDate,Pageable paging);
}
