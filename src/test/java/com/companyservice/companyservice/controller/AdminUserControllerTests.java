package com.companyservice.companyservice.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.AdminUserService;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//RunWith(SpringRunner.class)
//@SpringBootTest(classes= {AdminUserControllerTests.class})
public class AdminUserControllerTests {
	@Mock
	AdminUserService adminUserService;
	@InjectMocks
	AdminUserController adminUserController;
	
	@Test
	public void test_create() throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("11a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		String[] ids = {"11a", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(adminUserService.adminCreation(adminUser)).thenReturn(adminUser);
				ResponseEntity response = adminUserController.create(adminUser);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(adminUserService.adminCreation(adminUser)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							adminUserController.create(adminUser);
				      });
				   assertEquals("User registration failed", ex.getMessage());
			}
		}
	}
	@Test
	public void test_adminCreate() throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("11a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		String[] ids = {"11a", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(adminUserService.adminCreation(adminUser)).thenReturn(adminUser);
				ResponseEntity response = adminUserController.adminCreate(adminUser);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(adminUserService.adminCreation(adminUser)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							adminUserController.adminCreate(adminUser);
				      });
				   assertEquals("User registration failed", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getAdminUserDetails() throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("11a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		
		String[] ids = {"11a", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(adminUserService.getUserDetailsByAdmin(adminUser.getAdminId())).thenReturn(adminUser);
				ResponseEntity response = adminUserController.getAdminUserDetails(adminUser.getAdminId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(adminUserService.getUserDetailsByAdmin(adminUser.getAdminId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							adminUserController.getAdminUserDetails(adminUser.getAdminId());
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_updateUser() throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("11a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		String[] ids = {"11a", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(adminUserService.updateUserByAdmin(adminUser.getAdminId(),adminUser)).thenReturn(adminUser);
				ResponseEntity response = adminUserController.updateUser(adminUser.getAdminId(),adminUser);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(adminUserService.updateUserByAdmin(adminUser.getAdminId(),adminUser)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							adminUserController.updateUser(ids[id],adminUser);
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_deleteUserByAdmin() throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("11a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		
		String[] ids = {"11a", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(adminUserService.deleteUserByAdmin(adminUser.getAdminId())).thenReturn("Successfull");
				ResponseEntity response = adminUserController.deleteUserByAdmin(adminUser.getAdminId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(adminUserService.deleteUserByAdmin(adminUser.getAdminId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							adminUserController.deleteUserByAdmin(ids[id]);
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_authenticate() throws Exception {
		JwtRequest request =  new JwtRequest("abc@gmail.com","abc");
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("11a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		JSONObject json = new JSONObject();
		json.appendField("token", "Asdfadfaf124357gsjbf819ebiq97");
		json.appendField("userDetails", adminUser);
		String[] ids = {"11a", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(adminUserService.adminAuthenticate(request)).thenReturn(json);
				ResponseEntity response = adminUserController.authenticate(request);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(adminUserService.adminAuthenticate(request)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							adminUserController.authenticate(request);
				      });
				   assertEquals("User Login failed", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getAdminUsers() throws Exception {
		AdminUser adminUser = new AdminUser();
		adminUser.setAdminId("111a");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword("password");
		adminUser.setName("Admin");
		adminUser.setPhone(0000000000);
		adminUser.setStatus(true);
		String[] ids = {"111a", null};
		List list = new ArrayList();
		list.add(adminUser);
		list.add(null);
		Page<AdminUser> emptyPage = null;
		Page<AdminUser> pageDetails = new PageImpl<>(list);
		List result = new ArrayList();
		result.add(pageDetails);
		result.add(emptyPage);
		int i = 0;
		for(i = 0; i < result.size(); i++) {
			if(result.get(i)!= null) {
				when(adminUserService.getAllUsers(1, 2)).thenReturn(pageDetails);
				ResponseEntity response = adminUserController.getUsers(1, 2);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(adminUserService.getAllUsers(1, 2)).thenReturn(emptyPage);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							adminUserController.getUsers(0, 0);
				      });
				   assertEquals("Users data not found", ex.getMessage());
			}
		}
	}
}
