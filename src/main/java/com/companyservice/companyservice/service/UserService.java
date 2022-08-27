package com.companyservice.companyservice.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Authorization.authorizationservice.model.TokenUtil;
//import com.Authorization.authorizationservice.model.TokenUtil;
import com.companyservice.companyservice.SecurityConfiguration.SecurityConfig;
import com.companyservice.companyservice.controller.UserController;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Password;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.UserRepository;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;

@Service
public class UserService {
	@Value("${forgotpassword-link}")
	private String forgotPasswordPath;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecurityConfig securityConfig;
	@Autowired
	private UserController	controllerClass;
	@Autowired
	private MailService mailing;
	private TokenUtil tokenUtil = new TokenUtil();
	
	public User creation(String companyId, User payload) throws Exception {
		Company company = new Company();
		company.setCompanyId(companyId);
		payload.setCompanyId(company);
		payload.setPassword(securityConfig.passwordEncryption(payload.getPassword()));
		User regUser = new User();
		if(userRepository.existsByEmail(payload.getEmail())) {
			throw new BadRequestException("User already exist with this email");
		}else {
			try {
				regUser = userRepository.save(payload);
			}catch(Exception e) {
			//	if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				//}
			}
		}
		return regUser;
	}
	
	public JSONObject authenticate(JwtRequest payload) throws Exception {
		User userDetails = userRepository.findByEmail(payload.getEmail());
		if(userDetails == null) {
			throw new DetailsNotFound("Invalid Credentials");
		}else {
			if(!payload.getPassword().equals(securityConfig.passwordDecryption(userDetails.getPassword()))) {
				throw new Exception("Invalid password");
			}
			else {
				if(userDetails.isStatus()) {
					String token = tokenUtil.generateToken(userDetails.getEmail());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("userDetails",userDetails);
					jsonObject.put("token",token);
					if(userDetails.getCompanyId().getSubscriptionId() != null) {
						jsonObject.put("subscription",true);
					}else {
						jsonObject.put("subscription",false);
					}
					return jsonObject;
				}else {
					WebMvcLinkBuilder activationlink =  linkTo(methodOn(controllerClass.getClass()).activationUser(userDetails.getCompanyId().getCompanyId(),userDetails.getUserId()));
					try {
						String body = "Please click on the below link to activate your account in outreach fox application.<br>"+activationlink.toString();
						mailing.sendEmail(userDetails.getEmail(),"Account activation link.!",body);
			        } catch (SendFailedException sendFailedException) {
			        	throw new BadRequestException("Activation link email unsuccessfull.!");
			        }
					throw new BadRequestException("Your account is in deactivation mode . Activation link has been sent to your registered mail id, please check your mail");
				}
			}
		}
	}
	
	public List<User> getUsersDetails(String companyId) throws Exception {
		try {
			List<User> companiesDetails = (List<User>) userRepository.findByCompanyId(companyId);
			return companiesDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}
	
	public Page<User> getUsersDetails(String companyId,int pageNo,int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Company company = new Company();
			company.setCompanyId(companyId);
			Page<User> companiesDetails =  userRepository.findByCompanyId(company,paging);
			//int count = userRepository.countByCompanyId(company);
			return companiesDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public User getUserByID(String id) throws Exception {
		try {
			User userDetails = userRepository.getById(id);
			return userDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}

	public User updateUser(String id, User payload) throws Exception {		
		if(userRepository.existsById(id)) {
			User userDetails = userRepository.getById(id);
			if(userDetails.getEmail().equals(payload.getEmail())) {
				userDetails = setUserData(payload, userDetails);
			}else {
				if(userRepository.existsByEmail(payload.getEmail())) {
					throw new BadRequestException("User already exist with this email");
				}
				else {
					userDetails = setUserData(payload, userDetails);
				}
			}
			try {
				userDetails = userRepository.save(userDetails);
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

	User setUserData(User payload, User userDetails) {
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

	public String deleteUserByID(String id) throws Exception {
		if(userRepository.existsById(id)) {
			try {
				userRepository.deleteById(id);
				return "User deleted successfully";
			}
			catch(Exception e){
				//if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				//}
			}
		}
		else 
			throw new DetailsNotFound("User does not exist.!"); 
			//return null;
	}
	
	public User updatePassword(String id, Password payload) throws Exception {
		if(userRepository.existsById(id)) {
			User userDetails = userRepository.getById(id);
			if(payload.getOldPassword().equals(securityConfig.passwordDecryption(userDetails.getPassword()))) {
				userDetails.setPassword(securityConfig.passwordEncryption(payload.getNewPassword()));
				try {
					userDetails = userRepository.save(userDetails);
					return userDetails;
				}catch(Exception e){
					if(e instanceof SQLException) {
						throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
					}
				}
			}else {
				throw new BadRequestException("Please enter valid password.!");
			}
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
		return null;
	}
	public User forgotPassword(String id,JwtRequest payload) throws Exception {
		if(userRepository.existsById(id)) {
			User userDetails = userRepository.getById(id);
			if(payload.getPassword() != null ) {
				userDetails.setPassword(securityConfig.passwordEncryption(payload.getPassword()));
				try {
					userDetails = userRepository.save(userDetails);
					return userDetails;
				}catch(Exception e){
					//if(e instanceof SQLException) {
						throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
					//}
				}
			}else {
				throw new BadRequestException("Please enter valid password.!");
			}
		}else {
			throw new BadRequestException("Please provide valid details.!");
		}
		//return null;
	}
	public User sendLinkToUpdatePassword(JwtRequest jwtRequest) throws MessagingException, IOException {
		if(userRepository.existsByEmail(jwtRequest.getEmail())) {
			User userDetails = userRepository.getByEmail(jwtRequest.getEmail());
			try {
				String link =  forgotPasswordPath+"/"+userDetails.getUserId();
				String body = "Please click on the below link to change your account password in outreach fox application.<br>"+link;
				mailing.sendEmail(userDetails.getEmail(),"Forgot password link.!",body);
	        } catch (SendFailedException sendFailedException) {
	        	throw new BadRequestException("Password change link email unsuccessfull.!");
	        }
			return userDetails;
		}else {
			throw new DetailsNotFound("User details not found with this mail id");
		}
	}
	
	public Page<User> userFilter(JSONObject payload, int pageNo, int pageSize){
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		boolean fullName = false;
		boolean email = false;
		boolean phone = false;
		String name = null, email_id = null ;
		long phone_no = 0;
		Page<User> allUsers;
		Company company =  new Company();
		company.setCompanyId(payload.getAsString("companyId"));
		if(payload.containsKey("name") && payload.getAsString("name") != null && payload.getAsString("name") != "" ){
			fullName = true;
			name = payload.getAsString("name");
		}
		if(payload.containsKey("email") && payload.getAsString("email") != null && payload.getAsString("email") != ""){
			email = true;
			email_id = payload.getAsString("email");
		}
		if(payload.containsKey("phone") && payload.getAsString("phone") != null && payload.getAsString("phone") != ""){
			phone = true;
			phone_no = Long.parseLong(payload.getAsString("phone"));
		}
		if(fullName == true && email == true && phone == true) {
			allUsers =  userRepository.getByCompanyIdAndNameAndEmailAndPhone(company,name, email_id, phone_no, paging);
			return allUsers;
		}
		if(fullName == false && email == true && phone == true) {
			allUsers =  userRepository.getByCompanyIdAndEmailAndPhone(company,email_id, phone_no, paging);
			return allUsers;
		}
		if(fullName == true && email == false && phone == true) {
			allUsers =  userRepository.getByCompanyIdAndNameAndPhone(company,name,  phone_no, paging);
			return allUsers;
		}
		if(fullName == true && email == true && phone == false) {
			allUsers =  userRepository.getByCompanyIdAndNameAndEmail(company,name, email_id, paging);
			return allUsers;
		}
		if(fullName == true && email == false && phone == false) {
			allUsers =  userRepository.getByCompanyIdAndName(company,name,  paging);
			return allUsers;
		}
		if(fullName == false && email == true && phone == false) {
			allUsers =  userRepository.getByCompanyIdAndEmail(company, email_id,  paging);
			return allUsers;
		}
		if(fullName == false && email == false && phone == true) {
			allUsers =  userRepository.getByCompanyIdAndPhone( company,phone_no, paging);
			return allUsers;
		}
		
		return null;
		
	}
		
}
