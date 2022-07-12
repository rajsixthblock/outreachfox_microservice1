package com.companyservice.companyservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.AdminUserService;
import com.companyservice.companyservice.service.UserService;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;

@CrossOrigin
@RestController
@Logging
public class AdminUserController {
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Authorization
	@PostMapping("/user/creation/by/admin")
	public ResponseEntity<?> create(@RequestBody @Valid AdminUser payload) throws Exception {
		AdminUser regUser = adminUserService.adminCreation(payload);
		if(regUser.getAdminId() != null) {
			return new ResponseEntity<>(regUser, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("User registration failed");
		}
	}
	
	@PostMapping("/admin/user/creation")
	public ResponseEntity<?> adminCreate(@RequestBody @Valid AdminUser payload) throws Exception {
		AdminUser regUser = adminUserService.adminCreation(payload);
		if(regUser.getAdminId() != null) {
			return new ResponseEntity<>(regUser, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("User registration failed");
		}
	}
	@PostMapping("/admin/user/login")
	public ResponseEntity<?> authenticate( @RequestBody JwtRequest jwtRequest ) throws Exception {
		JSONObject response = adminUserService.adminAuthenticate(jwtRequest);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new BadRequestException("User Login failed");
		}
	}
	
	@Authorization
	@GetMapping("/user/getallusers/{page}/{limit}")
	public ResponseEntity<?> getUsers(@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<User> companyDetails =  userService.getUsersDetails(companyId,page,limit);
		Page<AdminUser>  companyDetails =  adminUserService.getAllUsers(page,limit);
		if(!companyDetails.isEmpty()) {
			return new ResponseEntity<>(companyDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Users data not found");
		}
	}
	
	@Authorization
	@GetMapping("/admin/user/getByID/{id}")
	public ResponseEntity<?> getAdminUserDetails(@PathVariable String id) throws Exception {
		AdminUser userDetails =  adminUserService.getUserDetailsByAdmin(id);
		if(userDetails != null) {
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
			
		}
	}
	@Authorization
	@PutMapping("/admin/user/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody AdminUser payload) throws Exception {
		AdminUser userDetails =  adminUserService.updateUserByAdmin(id, payload);
		if(userDetails != null) {
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
	}
	
	@Authorization
	@DeleteMapping("/admin/user/delete/{id}")
	public ResponseEntity<?> deleteUserByAdmin(@PathVariable String id) throws Exception {	
		String response =  adminUserService.deleteUserByAdmin(id);
		if(response != "") {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
		
	}
}
