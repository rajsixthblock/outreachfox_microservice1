package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;


@Repository
public interface PaymentRepository extends CrudRepository<Payment, String>,PagingAndSortingRepository<Payment, String>{

	boolean existsBytransactionId(String transactionId);
	Payment getById(String id);
	List<Payment> getByCompanyId(Company companyId);
	Page<Payment> getByCompanyId(Company companyId,org.springframework.data.domain.Pageable paging);
}
