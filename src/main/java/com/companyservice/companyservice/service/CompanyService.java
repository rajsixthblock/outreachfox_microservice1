package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.companyservice.companyservice.SecurityConfiguration.SecurityConfig;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.CompanyRepository;
import com.companyservice.companyservice.repository.UserRepository;



@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private SecurityConfig securityConfig;
	@Autowired
	private UserRepository userRepository;
	
	public Company companyRegistration(Company payload) throws Exception {
		Company regCompany = new Company();
		if(companyRepository.existsByemail(payload.getEmail())) {
			throw new BadRequestException("Company already exist with this email");
		}
		else {
			payload.setPassword(securityConfig.passwordEncryption(payload.getPassword()));
			try {
				regCompany = companyRepository.save(payload);
				User user = new User();
				user.setCompanyId(regCompany);
				user.setEmail(payload.getEmail());
				user.setPassword(payload.getPassword());
				user.setName("Admin");
				user.setPhone(payload.getPhone());
				user.setStatus(payload.isStatus());
				user = userRepository.save(user);
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		return regCompany;
	}
	
	public List<Company> getCompaniesDetails() throws Exception {
		try {
			List<Company> companiesDetails = (List<Company>) companyRepository.findAll();
			return companiesDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public List<Company> getCompaniesDetailsPagination(int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Page<Company> companiesDetails =  companyRepository.findAll(paging);
			System.err.println(companiesDetails);
			return  companiesDetails.toList();
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public Optional<Company> getCompanyByID(String id) throws Exception {
		try {
			Optional<Company> companyDetails = companyRepository.findById(id);
			return companyDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}

	public Company updateCompany(String id, Company payload) throws Exception {
		if(companyRepository.existsById(id)) {
			Company companyDetails = companyRepository.getById(id);
			if(companyDetails.getEmail().equals(payload.getEmail())) {
				companyDetails = setcompanyData(payload, companyDetails);
			}
			else {
				if(companyRepository.existsByemail(payload.getEmail())) {
					throw new BadRequestException("Company already exist with this email");
				}
				else {
					companyDetails = setcompanyData(payload, companyDetails);
				}
			}
			
			try {
				companyDetails = companyRepository.save(companyDetails);
				return companyDetails;
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else {
			throw new DetailsNotFound("Company details not found.!"); 
		}
		return null;
	}

	private Company setcompanyData(Company payload, Company companyDetails) {
		if(payload.getEmail() != null) {
			companyDetails.setEmail(payload.getEmail());
		}
		if(payload.getName() != null) {
			companyDetails.setName(payload.getName());
		}
		if(payload.getPhone() != 0) {
			companyDetails.setPhone(payload.getPhone());
		}
		if(payload.isStatus()) {
			companyDetails.setStatus(payload.isStatus());
		}else if(!payload.isStatus()) {
			companyDetails.setStatus(payload.isStatus());
		}
		if(payload.getAddress() != null) {
			companyDetails.setAddress(payload.getAddress());
		}
		return companyDetails;
	}

	public String deleteCompanyByID(String id) throws Exception {
		if(companyRepository.existsById(id)) {
			try {
				companyRepository.deleteById(id);
				return "Company deleted successfully";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("Company does not exist.!"); 
			return null;
	}

}
