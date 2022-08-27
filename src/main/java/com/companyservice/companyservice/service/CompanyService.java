package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.AudienceRepository;
import com.companyservice.companyservice.repository.CampaignRepository;
import com.companyservice.companyservice.repository.CompanyRepository;
import com.companyservice.companyservice.repository.UserRepository;

import net.minidev.json.JSONObject;



@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private SecurityConfig securityConfig;
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private CampaignRepository campaignRepository;
	@Autowired
	private AudienceRepository audienceRepository;
	public User companyRegistration(Company payload) throws Exception {
		
		Company regCompany = new Company();
		User user = new User();
		
		if(companyRepository.existsByemail(payload.getEmail())) {
			throw new BadRequestException("Company already exist with this email");
		}
		else {
			payload.setPassword(securityConfig.passwordEncryption(payload.getPassword()));
			try {
				payload.setStatus(false);
				regCompany = companyRepository.save(payload);
				user.setCompanyId(regCompany);
				user.setEmail(payload.getEmail());
				user.setPassword(payload.getPassword());
				user.setName(payload.getName());
				user.setPhone(payload.getPhone());
				user.setStatus(false);
				user = userRepository.save(user);
				return user;
			}catch(Exception e) {
				//if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				//}
			}
		}
		//return user;
	}
	
	public List<Company> getCompaniesDetails() throws Exception {
		try {
			List<Company> companiesDetails = (List<Company>) companyRepository.findAll();
			return companiesDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}
	public Page<Company> getCompaniesDetailsPagination(int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Page<Company> companiesDetails =  companyRepository.findAll(paging);
			return  companiesDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public Company getCompanyByID(String id) throws Exception {
		try {
			Company companyDetails = companyRepository.getById(id);
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

	Company setcompanyData(Company payload, Company companyDetails) {
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
	
	public Company activateCompany(String companyId,String userId) {
		Company companyDetails = companyRepository.getById(companyId);
		if(companyDetails.getCompanyId() == null) {
			return null;
		}else {
			companyDetails.setStatus(true);
			companyDetails = companyRepository.save(companyDetails);
			User userDetails = userRepository.getById(userId);
			userDetails.setStatus(true);
			userDetails = userRepository.save(userDetails);
			return companyDetails;
		}
	}
	
	public Page<Company> companyFilter(JSONObject payload, int pageNo, int pageSize){
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		boolean companyName = false;
		boolean email = false;
		boolean phone = false;
		String name = null, email_id = null ;
		long phone_no = 0;
		Page<Company> allCompanies;
		if(payload.containsKey("companyName") && payload.getAsString("companyName") != null && payload.getAsString("companyName") != "" ){
			companyName = true;
			name = payload.getAsString("companyName");
		}
		if(payload.containsKey("email") && payload.getAsString("email") != null && payload.getAsString("email") != ""){
			email = true;
			email_id = payload.getAsString("email");
		}
		if(payload.containsKey("phone") && payload.getAsString("phone") != null && payload.getAsString("phone") != ""){
			phone = true;
			phone_no = Long.parseLong(payload.getAsString("phone"));
		}
		if(companyName == true && email == true && phone == true) {
			allCompanies =  companyRepository.getByCompanyNameAndEmailAndPhone(name, email_id, phone_no, paging);
			return allCompanies;
		}
		if(companyName == false && email == true && phone == true) {
			allCompanies =  companyRepository.getByEmailAndPhone(email_id, phone_no, paging);
			return allCompanies;
		}
		if(companyName == true && email == false && phone == true) {
			allCompanies =  companyRepository.getByCompanyNameAndPhone(name,  phone_no, paging);
			return allCompanies;
		}
		if(companyName == true && email == true && phone == false) {
			allCompanies =  companyRepository.getByCompanyNameAndEmail(name, email_id, paging);
			return allCompanies;
		}
		if(companyName == true && email == false && phone == false) {
			allCompanies =  companyRepository.getByCompanyName(name,  paging);
			return allCompanies;
		}
		if(companyName == false && email == true && phone == false) {
			allCompanies =  companyRepository.getByEmail( email_id,  paging);
			return allCompanies;
		}
		if(companyName == false && email == false && phone == true) {
			allCompanies =  companyRepository.getByPhone( phone_no, paging);
			return allCompanies;
		}
		
		return null;
		
	}
	
	public HashMap counting(List<Company> companies) {
		HashMap<String,JSONObject> count = new HashMap<String,JSONObject>();
		for(int i = 0; i < companies.size(); i++) {
			long campaignesCount = campaignRepository.countByCompanyId(companies.get(i));
			long contactsCount = audienceRepository.countByCompanyId(companies.get(i));
			JSONObject object = new JSONObject();
			object.appendField("campaignesCount", campaignesCount);
			object.appendField("contactsCount", contactsCount);
			count.put(companies.get(i).getCompanyId(), object);
		}
		return count;
		
	}

}
