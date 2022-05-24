package com.OutReachCompany.OutReachCompanyDetails.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.OutReachCompany.OutReachCompanyDetails.model.Company;
import com.OutReachCompany.OutReachCompanyDetails.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, String>{

	boolean existsBytransactionId(String transactionId);

	Payment getById(String id);
	List<Payment> getByCompanyId(Company companyId);
}
