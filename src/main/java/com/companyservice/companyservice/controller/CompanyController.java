package com.companyservice.companyservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.CompanyService;
import com.companyservice.companyservice.service.MailService;
import com.companyservice.companyservice.service.UserService;

import net.minidev.json.JSONObject;


@CrossOrigin
@RestController
@Logging
public class CompanyController {
	@Value("${login-link}")
	private String userLoginPath;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MailService mailing;
	@Autowired
	private UserService userService;
	
	@PostMapping("/company/register")
	public ResponseEntity<?> register(@RequestBody @Valid Company payload) throws Exception {
		User regUser = companyService.companyRegistration(payload);
		if(regUser != null) {
			WebMvcLinkBuilder activationlink =  linkTo(methodOn(this.getClass()).activationLink(regUser.getCompanyId().getCompanyId(),regUser.getUserId()));
			try { 
				String body = "Please click on the below link to activate your account in outreach fox application.<br>"+activationlink.toString();
				mailing.sendEmail(regUser.getEmail(),"Account activation link.!",body);
			} catch (SendFailedException sendFailedException) {
	        	String comResponse = companyService.deleteCompanyByID(regUser.getCompanyId().getCompanyId());
	        	String userResponse  = userService.deleteUserByID(regUser.getUserId());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company registration failed due to mail id.!");
	        }
			JSONObject response = new JSONObject();
			response.appendField("message", "Account activation link sent to your registered email, please check.!");
			response.appendField("data", regUser);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Company registration failed");
		}
	}
	
	@Authorization
	@GetMapping("/company/getCompanies")
	public ResponseEntity<?> getCompanies() throws Exception {
		List<Company> companiesDetails =  companyService.getCompaniesDetails();
		if(companiesDetails != null) {
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
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Company> companiesDetails =  companyService.getCompaniesDetailsPagination(page,limit);
		Page<Company> companiesDetails =  companyService.getCompaniesDetailsPagination(page,limit);
		if(companiesDetails != null) {
			HashMap count = companyService.counting(companiesDetails.getContent());
			JSONObject response = new JSONObject();
			response.appendField("companyDetails", companiesDetails);
			response.appendField("counts", count);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Companies data not found");
		}
	}
	
	@Authorization
	@GetMapping("/company/getByID/{id}")
	public ResponseEntity<?> getByCompanyID(@PathVariable String id) throws Exception {
		Company companiesDetails =  companyService.getCompanyByID(id);
		if(companiesDetails != null) {
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
	
	@GetMapping("/company/activate/{companyId}/{userId}")
	public ResponseEntity<?> activationLink(@PathVariable String companyId,@PathVariable String userId){
		Company companyDetails =  companyService.activateCompany(companyId,userId);
		if(companyDetails != null) {
			String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activated successfully.! </span><br><span>Please <button><a href="+userLoginPath+">Log in</a></button></span></body></html>";
			return new ResponseEntity<>(htmlPage, HttpStatus.OK);
		}
		else {
			String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activation failed.! Please contact admin </span><br></body></html>";
			//throw new DetailsNotFound("Company details not found");
			return new ResponseEntity<>(htmlPage, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/company/send/link/{userId}")
	public ResponseEntity<?> sendActivationLink(@PathVariable String userId) throws Exception{
		User regUser =  userService.getUserByID(userId);
		if(regUser != null) {
			WebMvcLinkBuilder activationlink =  linkTo(methodOn(this.getClass()).activationLink(regUser.getCompanyId().getCompanyId(),regUser.getUserId()));
			try {
				String body = "Please click on the below link to activate your account in outreach fox application.<br>"+activationlink.toString();
				mailing.sendEmail(regUser.getEmail(),"Account activation link.!",body);
	        } catch (SendFailedException sendFailedException) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Activation link email unsuccessfull.!");
	        }
			JSONObject response = new JSONObject();
			response.appendField("message", "Account activation link sent to your registered email, please check.!");
			response.appendField("data", regUser);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			throw new BadRequestException("Details not found.!");
		}
	}
	
	@Authorization
	@PostMapping("/company/filter/{page}/{limit}")
	public ResponseEntity<?> companyFilter(  @RequestBody JSONObject payload,@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		Page<Company> companiesDetails =  companyService.companyFilter(payload,page,limit);
		if(companiesDetails != null) {
			return new ResponseEntity<>(companiesDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Companies data not found");
		}
	}
	
	
}
