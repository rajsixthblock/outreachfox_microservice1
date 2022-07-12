package com.companyservice.companyservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.SQLException;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import com.Authorization.authorizationservice.model.TokenUtil;
import com.companyservice.companyservice.SecurityConfiguration.SecurityConfig;
import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Password;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.AdminUserRepository;
import com.companyservice.companyservice.repository.UserRepository;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;

@Service
public class AdminUserService {
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	@Autowired
	private SecurityConfig securityConfig;
	private TokenUtil tokenUtil = new TokenUtil();
	
	public AdminUser adminCreation(AdminUser payload) throws Exception {
		payload.setPassword(securityConfig.passwordEncryption(payload.getPassword()));
		AdminUser regUser = new AdminUser();
		if(adminUserRepository.existsByEmail(payload.getEmail())) {
			throw new BadRequestException("User already exist with this email");
		}else {
			try {
				regUser = adminUserRepository.save(payload);
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		return regUser;
	}
	
	public JSONObject adminAuthenticate(JwtRequest payload) throws Exception {
		AdminUser userDetails = adminUserRepository.findByEmail(payload.getEmail());
		JSONObject jsonObject = new JSONObject();
		if(userDetails == null) {
			throw new DetailsNotFound("Invalid Credentials");
		}else {
			if(!payload.getPassword().equals(securityConfig.passwordDecryption(userDetails.getPassword()))) {
				throw new Exception("Invalid password");
			}
			else {
				if(userDetails.isStatus()) {
					String token = tokenUtil.generateToken(userDetails.getEmail());
					jsonObject.put("userDetails",userDetails);
					jsonObject.put("token",token);
					return jsonObject;
				}
			}
		}
		return jsonObject;
	}
	public Page<AdminUser> getAllUsers(int pageNo,int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Page<AdminUser> companiesDetails =  adminUserRepository.findAll(paging);
			return companiesDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public AdminUser getUserDetailsByAdmin(String id) throws Exception {
		try {
			AdminUser userDetails = adminUserRepository.findById(id).orElse(null);
			return userDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	private AdminUser setUserData(AdminUser payload, AdminUser userDetails) {
		if(payload.getName() != null) {
			userDetails.setName(payload.getName());
		}
		if(payload.getPhone() != 0) {
			userDetails.setPhone(payload.getPhone());
		}
		if(payload.getEmail() != null) {
			userDetails.setEmail(payload.getEmail());
		}
		if(payload.isStatus()) {
			userDetails.setStatus(payload.isStatus());
		} else if(!payload.isStatus()) {
			userDetails.setStatus(payload.isStatus());
		}
		return userDetails;
	}
	public AdminUser updateUserByAdmin(String id, AdminUser payload) throws Exception {
		System.out.println(id);
		if(adminUserRepository.existsByAdminId(id)) {
			AdminUser userDetails = adminUserRepository.getByAdminId(id);
			if(userDetails.getEmail().equals(payload.getEmail()) ) {
				if(userDetails.getPhone() == payload.getPhone()) {
					userDetails = setUserData(payload, userDetails);
				}else {
					if(adminUserRepository.existsByPhone(payload.getPhone())) {
						throw new BadRequestException("User already exist with this phone");
					}else {
						userDetails = setUserData(payload, userDetails);
					}
				}
				
			}else {
				if(adminUserRepository.existsByEmail(payload.getEmail()) && adminUserRepository.existsByPhone(payload.getPhone()) ) {
					throw new BadRequestException("User already exist with this email");
				}
				else {
					userDetails = setUserData(payload, userDetails);
				}
			}
			try {
				userDetails = adminUserRepository.save(userDetails);
				return userDetails;
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
		return null;
	}
	public String deleteUserByAdmin(String id) throws Exception {
		if(adminUserRepository.existsById(id)) {
			try {
				adminUserRepository.deleteById(id);
				return "User deleted successfully";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("User does not exist.!"); 
			return null;
	}
}
