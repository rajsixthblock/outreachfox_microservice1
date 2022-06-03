package com.companyservice.companyservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.companyservice.companyservice.entity.*;


@Repository
public interface TemplateRepository extends CrudRepository<Template, String>,PagingAndSortingRepository<Template, String>{
	
	Template getById(String id);

	boolean existsByName(String name);


}
