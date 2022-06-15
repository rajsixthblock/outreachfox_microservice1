package com.companyservice.companyservice.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.MailStatus;

@Repository
public interface MailStatusRepository extends CrudRepository<MailStatus, String>{
	
	MailStatus getById(String id);

	Optional<MailStatus> findByCampaginId(Company company);

}
