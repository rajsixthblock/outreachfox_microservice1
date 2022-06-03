package com.companyservice.companyservice.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.CompanyService;



@RestController
@Logging
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@PostMapping("/company/register")
	public ResponseEntity<?> register(@RequestBody @Valid Company payload) throws Exception {
		Company regCompany = companyService.companyRegistration(payload);
		if(regCompany.getCompanyId() != null) {
			return new ResponseEntity<>(regCompany, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Company registration failed");
		}
	}
	
	@Authorization
	@GetMapping("/company/getCompanies")
	public ResponseEntity<?> getCompanies() throws Exception {
		List<Company> companiesDetails =  companyService.getCompaniesDetails();
		if(!companiesDetails.isEmpty()) {
			return new ResponseEntity<>(companiesDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Companies data not found");
		}
	}
	
	@Authorization
	@GetMapping("/company/getCompanies/{page}/{limit}")
	public ResponseEntity<?> getCompaniesPagination(@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			System.out.println("1");
			page = 1;
		}
		if(limit == 0) {
			System.out.println(limit);
			limit = 10;
		}
		List<Company> companiesDetails =  companyService.getCompaniesDetailsPagination(page,limit);
		if(!companiesDetails.isEmpty()) {
			return new ResponseEntity<>(companiesDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Companies data not found");
		}
	}
	
	@Authorization
	@GetMapping("/company/getByID/{id}")
	public ResponseEntity<?> getByCompanyID(@PathVariable String id) throws Exception {
		Optional<Company> companiesDetails =  companyService.getCompanyByID(id);
		if(!companiesDetails.isEmpty()) {
			return new ResponseEntity<>(companiesDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Company details not found");
		}
	}
	
	@Authorization
	@PutMapping("/company/update/{id}")
	public ResponseEntity<?> updateCompany(@PathVariable String id, @RequestBody @Valid Company payload) throws Exception {
		Company companyDetails =  companyService.updateCompany(id, payload);
		if(companyDetails != null) {
			return new ResponseEntity<>(companyDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Company details not found");
		}
		
	}
	
	@Authorization
	@DeleteMapping("/company/delete/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable String id) throws Exception {
		String response =  companyService.deleteCompanyByID(id);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Company details not found");
		}
		
	}
	
}
