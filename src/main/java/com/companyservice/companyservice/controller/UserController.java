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
import org.springframework.web.bind.annotation.RestController;
import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.UserService;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;

@RestController
@Logging
public class UserController {

	@Autowired
	private UserService userService;
	
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
			System.out.println(response.getAsString("token"));
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
			throw new DetailsNotFound("Companies data not found");
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
		List<User> companyDetails =  userService.getUsersDetails(companyId,page,limit);
		if(!companyDetails.isEmpty()) {
			return new ResponseEntity<>(companyDetails, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Companies data not found");
		}
	}
	
	@Authorization
	@GetMapping("/user/getByID/{id}")
	public ResponseEntity<?> getByUserID(@PathVariable String id) throws Exception {
		Optional<User> userDetails =  userService.getUserByID(id);
		if(!userDetails.isEmpty()) {
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
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("User details not found");
		}
		
	}
}
