package com.companyservice.companyservice.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import com.companyservice.companyservice.entity.Password;
//import com.Authorization.authorizationservice.annotation.Authorization;
//import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.UserRepository;
import com.companyservice.companyservice.service.CompanyService;
import com.companyservice.companyservice.service.UserService;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;
@CrossOrigin
@RestController
@Logging
public class UserController {
	@Value("${login-link}")
	private String userLoginPath;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserRepository userRepository;
	
	@Authorization
	@PostMapping("/user/create/{companyId}")
	public ResponseEntity<?> create( @PathVariable String companyId, @RequestBody @Valid User payload) throws Exception {
		User regUser = userService.creation(companyId,payload);
		if(regUser.getUserId() != null) {
			return new ResponseEntity<>(regUser, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("User registration failed");
		}
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<?> authenticate( @RequestBody JwtRequest jwtRequest ) throws Exception {
		JSONObject response = userService.authenticate(jwtRequest);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new BadRequestException("User Login failed");
		}
		
	}
	
	@Authorization
	@GetMapping("/user/getUsers/{companyId}")
	public ResponseEntity<?> getUsers(@PathVariable String companyId) throws Exception {
		List<User> companyDetails =  userService.getUsersDetails(companyId);
		if(!companyDetails.isEmpty()) {
			return new ResponseEntity<>(companyDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Users data not found");
		}
	}
	@Authorization
	@GetMapping("/user/getUsers/{companyId}/{page}/{limit}")
	public ResponseEntity<?> getUsers(@PathVariable String companyId,@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<User> companyDetails =  userService.getUsersDetails(companyId,page,limit);
		Page<User>  companyDetails =  userService.getUsersDetails(companyId,page,limit);
		if(!companyDetails.isEmpty()) {
			return new ResponseEntity<>(companyDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Users data not found");
		}
	}
	
	@Authorization
	@GetMapping("/user/getByID/{id}")
	public ResponseEntity<?> getByUserID(@PathVariable String id) throws Exception {
		User userDetails =  userService.getUserByID(id);
		if(userDetails != null) {
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
	}
	
	@Authorization
	@PutMapping("/user/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User payload) throws Exception {
		User userDetails =  userService.updateUser(id, payload);
		if(userDetails != null) {
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
		
	}
	
	@Authorization
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) throws Exception {	
		String response =  userService.deleteUserByID(id);
		if(response != "") {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
		
	}
	@Authorization
	@PutMapping("/user/password/update/{id}")
	public ResponseEntity<?> updatePassword(@PathVariable String id, @RequestBody Password payload) throws Exception {
		User userDetails =  userService.updatePassword(id, payload);
		if(userDetails != null) {
			return new ResponseEntity<>("Password updated successfully.!", HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
	}
	@PutMapping("/user/password/forgot/{id}")
	public ResponseEntity<?> forgotPassword(@PathVariable String id, @RequestBody JwtRequest payload) throws Exception {
		User userDetails =  userService.forgotPassword(id, payload);
		if(userDetails != null) {
			return new ResponseEntity<>("Password updated successfully.!", HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Password updation failed.!");
		}
	}
	@PostMapping("/user/password/forgot/email")
	public ResponseEntity<?> sendLinkToUpdatePassword(@RequestBody JwtRequest jwtRequest) throws Exception {
			User userDetails =  userService.sendLinkToUpdatePassword(jwtRequest);
			if(userDetails != null) {
				return new ResponseEntity<>("Password change link send to your registered mail id successfully.!", HttpStatus.OK);
			}
			else {
				throw new DetailsNotFound("Link sending failed.!");
			}
	}
	@GetMapping("/user/activate/{companyId}/{userId}")
	public ResponseEntity<?> activationUser(@PathVariable String companyId,@PathVariable String userId){
		Company companyDetails =  companyService.activateCompany(companyId,userId);
		if(companyDetails.getCompanyId() != null) {
			String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activated successfully.! </span><br><span>Please <button><a href="+userLoginPath+">Log in</a></button></span></body></html>";
			return new ResponseEntity<>(htmlPage, HttpStatus.OK);
		}
		else {
			//throw new DetailsNotFound("Company details not found");
			String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activation failed.! Please contact admin </span><br></body></html>";
			//throw new DetailsNotFound("Company details not found");
			return new ResponseEntity<>(htmlPage, HttpStatus.BAD_REQUEST);
		}
	}
	@Authorization
	@PostMapping("/user/filter/{page}/{limit}")
	public ResponseEntity<?> userFilter(  @RequestBody JSONObject payload,@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			System.out.println("1");
			page = 1;
		}
		if(limit == 0) {
			System.out.println(limit);
			limit = 10;
		}
		Page<User> usersList =  userService.userFilter(payload,page,limit);
		if(usersList != null) {
			return new ResponseEntity<>(usersList, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Users data not found");
		}
	}
	
}
