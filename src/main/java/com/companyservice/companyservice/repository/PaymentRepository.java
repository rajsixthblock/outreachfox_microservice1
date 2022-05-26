package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;



public interface PaymentRepository extends CrudRepository<Payment, String>{

	boolean existsBytransactionId(String transactionId);

	Payment getById(String id);
	List<Payment> getByCompanyId(Company companyId);
}
