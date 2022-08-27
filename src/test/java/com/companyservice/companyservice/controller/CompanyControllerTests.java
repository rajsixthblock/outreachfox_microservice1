package com.companyservice.companyservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;

import org.hamcrest.Matchers;
//import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.entity.Template;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.CompanyService;
import com.companyservice.companyservice.service.MailService;
import com.companyservice.companyservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
//
//@WebMvcTest
@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes= {CompanyControllerTests.class})
public class CompanyControllerTests {

	@Mock
	CompanyService companyService;
	@Mock
	UserService userService;
	@InjectMocks
	CompanyController companyController;
	@Mock
	MailService mailing;
	@Test
	public void test_getCompanies() throws Exception {
	
		List<Company> companiesDetails = new ArrayList<Company>();
		companiesDetails.add(new Company("123", "Rajesh", "Sixthblock", 0, "sixthblock@gmail.com", "password1", true, "Hyd", null, null, null));
		companiesDetails.add(new Company("123", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null));
		
		String[] ids = {"123", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.getCompaniesDetails()).thenReturn(companiesDetails);
				ResponseEntity<?> response = companyController.getCompanies();
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(companyService.getCompaniesDetails()).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							companyController.getCompanies();
				      });
				   assertEquals("Companies data not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_register() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User("111", companyDetails, "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		
		String companyId = "111";
		String activationlink = "http://localhost:8080/lick";
		String body = "Please click on the below link to activate your account in outreach fox application.<br>"+activationlink;
		String[] ids = {"123", null};
		int i = 0;
		Throwable exception = null;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.companyRegistration(companyDetails)).thenReturn(userDetails);
				
				try {
					mailing.sendEmail(userDetails.getEmail(),"Account activation link.!",body);
					//Assert.fail("Should throw SendFailedException");
				}catch(Throwable running) {
					running.fillInStackTrace();
					when(companyService.deleteCompanyByID(userDetails.getCompanyId().getCompanyId())).thenReturn("Successfull");
					when(userService.deleteUserByID(userDetails.getUserId())).thenReturn("Successfull");
					ResponseEntity response =   companyController.register(companyDetails);
					//System.out.println(response.toString());
					assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
				}
				JSONObject jsonResponse = new JSONObject();
				jsonResponse.appendField("message", "Account activation link sent to your registered email, please check.!");
				jsonResponse.appendField("data", userDetails);
				ResponseEntity response =   companyController.register(companyDetails);
				//System.out.println(response.toString());
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(companyService.companyRegistration(companyDetails)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							companyController.register(companyDetails);
				      });
				assertEquals("Company registration failed", ex.getMessage());
			}
		}
		//assertEquals(jsonResponse, response.getBody());
	}
	
	@Test
	public void test_getByCompanyID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.getCompanyByID(companyDetails.getCompanyId())).thenReturn(companyDetails);
				ResponseEntity response =   companyController.getByCompanyID(companyDetails.getCompanyId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(companyService.getCompanyByID(companyDetails.getCompanyId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							companyController.getByCompanyID(companyDetails.getCompanyId());
				      });
				assertEquals("Company details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_updateCompany() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.updateCompany(companyDetails.getCompanyId(), companyDetails)).thenReturn(companyDetails);
				ResponseEntity response =   companyController.updateCompany(companyDetails.getCompanyId(), companyDetails);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(companyService.updateCompany(companyDetails.getCompanyId(), companyDetails)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							companyController.updateCompany(companyDetails.getCompanyId(), companyDetails);
				      });
				assertEquals("Company details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_deleteCompany() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.deleteCompanyByID(companyDetails.getCompanyId())).thenReturn("Successfull");
				ResponseEntity response =   companyController.deleteCompany(companyDetails.getCompanyId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(companyService.deleteCompanyByID(companyDetails.getCompanyId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							companyController.deleteCompany(companyDetails.getCompanyId());
				      });
				assertEquals("Company details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_activationLink() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User("111", companyDetails, "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.activateCompany(companyDetails.getCompanyId(),userDetails.getUserId())).thenReturn(companyDetails);
				//String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activated successfully.! </span><br><span>Please <button><a href="+userLoginPath+">Log in</a></button></span></body></html>";
				ResponseEntity response =   companyController.activationLink(companyDetails.getCompanyId(),userDetails.getUserId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(companyService.activateCompany(companyDetails.getCompanyId(),userDetails.getUserId())).thenReturn(null);
//				Throwable ex = assertThrows(
//						DetailsNotFound.class, () -> {
//							companyController.activationLink(companyDetails.getCompanyId(),userDetails.getUserId());
//				      });
				ResponseEntity response =   companyController.activationLink(companyDetails.getCompanyId(),userDetails.getUserId());
				assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			}
		}
	}
	@Test
	public void test_sendActivationLink() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User("111", companyDetails, "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(userService.getUserByID(userDetails.getUserId())).thenReturn(userDetails);
				String body = "Please click on the below link to activate your account in outreach fox application.<br> http://localhost:8080/click.com";
				try{
					mailing.sendEmail(userDetails.getEmail(),"Account activation link.!",body);
				}catch(SendFailedException sendFailedException) {
					ResponseEntity response =   companyController.sendActivationLink(userDetails.getUserId());
					assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
				}
				//String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activated successfully.! </span><br><span>Please <button><a href="+userLoginPath+">Log in</a></button></span></body></html>";
				ResponseEntity response =   companyController.sendActivationLink(userDetails.getUserId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(userService.getUserByID(userDetails.getUserId())).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							companyController.sendActivationLink(userDetails.getUserId());
				      });
				assertEquals("Details not found.!", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getCompaniesPagination() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		
		String[] ids = {"11a", null};
		List<Company> result = new ArrayList();
		result.add(companyDetails);
		result.add(companyDetails);
		Page<Company> emptyPage = null;
		Page<Company> pageDetails = new PageImpl<>(result);
		List res = new ArrayList();
		res.add(pageDetails);
		res.add(emptyPage);
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.getCompaniesDetailsPagination(1,2)).thenReturn(pageDetails);
				ResponseEntity response =   companyController.getCompaniesPagination(1,2);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(companyService.getCompaniesDetailsPagination(1,2)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							companyController.getCompaniesPagination(0,0);
				      });
				assertEquals("Companies data not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_companyFilter() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		
		String[] ids = {"11a", null};
		List<Company> result = new ArrayList();
		result.add(companyDetails);
		result.add(companyDetails);
		Page<Company> emptyPage = null;
		Page<Company> pageDetails = new PageImpl<>(result);
		List res = new ArrayList();
		res.add(pageDetails);
		res.add(emptyPage);
		JSONObject payload = new JSONObject();
		payload.appendField("companyName", "Company_A");
		payload.appendField("email", "company@gmail.com");
		payload.appendField("phone", "0000000000");
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(companyService.companyFilter(payload,1,2)).thenReturn(pageDetails);
				ResponseEntity response =   companyController.companyFilter(payload,1,2);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(companyService.companyFilter(payload,1,2)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							companyController.companyFilter(payload,0,0);
				      });
				assertEquals("Companies data not found", ex.getMessage());
			}
		}
	}
}
