package com.companyservice.companyservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Authorization.authorizationservice.model.TokenUtil;
import com.companyservice.companyservice.SecurityConfiguration.SecurityConfig;
import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.repository.AdminUserRepository;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {AdminUserServiceTests.class})
public class AdminUserServiceTests {
	@Mock
	AdminUserRepository adminUserRepository;
	@Mock
	SecurityConfig securityConfig;
	@Mock
	TokenUtil tokenUtil = new TokenUtil();
	@InjectMocks
	AdminUserService adminUserService;
	@Test
	public void test_adminCreation() throws Exception {
		AdminUser regUser = new AdminUser();
		regUser.setAdminId("111a");
		regUser.setEmail("admin@gmail.com");
		regUser.setName("Admin");
		regUser.setPassword("admin");
		regUser.setPhone(0000000000);
		regUser.setStatus(true);
		when(adminUserRepository.existsByEmail(regUser.getEmail())).thenReturn(false);
		when(adminUserRepository.save(regUser)).thenReturn(regUser);
		AdminUser responseUser = adminUserService.adminCreation(regUser);
		assertEquals(regUser.getAdminId(), responseUser.getAdminId());
	}
	@Test
	public void test_getUserDetailsByAdmin() throws Exception {
		AdminUser regUser = new AdminUser();
		regUser.setAdminId("111a");
		regUser.setEmail("admin@gmail.com");
		regUser.setName("Admin");
		regUser.setPassword("admin");
		regUser.setPhone(0000000000);
		regUser.setStatus(true);
		when(adminUserRepository.getByAdminId(regUser.getAdminId())).thenReturn(regUser);
		AdminUser responseUser = adminUserService.getUserDetailsByAdmin(regUser.getAdminId());
		assertEquals(regUser.getAdminId(), responseUser.getAdminId());
	}
	@Test
	public void test_updateUserByAdmin() throws Exception {
		AdminUser regUser = new AdminUser();
		regUser.setAdminId("111a");
		regUser.setEmail("admin@gmail.com");
		regUser.setName("Admin");
		regUser.setPassword("admin");
		regUser.setPhone(0000000000);
		regUser.setStatus(true);
		AdminUser setData = adminUserService.setUserData(regUser,regUser);
		when(adminUserRepository.existsByAdminId(regUser.getAdminId())).thenReturn(true);
		when(adminUserRepository.getByAdminId(regUser.getAdminId())).thenReturn(regUser);
//		when(adminUserRepository.existsByEmail(regUser.getEmail())).thenReturn(false);
//		when(adminUserRepository.existsByPhone(regUser.getPhone())).thenReturn(false);
		when(adminUserRepository.save(regUser)).thenReturn(regUser);
		AdminUser responseUser = adminUserService.updateUserByAdmin(regUser.getAdminId(),regUser);
		assertEquals(regUser.getAdminId(), responseUser.getAdminId());
	}
	@Test
	public void test_deleteUserByAdmin() throws Exception {
		AdminUser regUser = new AdminUser();
		regUser.setAdminId("111a");
		regUser.setEmail("admin@gmail.com");
		regUser.setName("Admin");
		regUser.setPassword("admin");
		regUser.setPhone(0000000000);
		regUser.setStatus(true);
		when(adminUserRepository.existsById(regUser.getAdminId())).thenReturn(true);
		adminUserRepository.deleteById(regUser.getAdminId());
		String responseUser = adminUserService.deleteUserByAdmin(regUser.getAdminId());
		assertEquals("User deleted successfully", responseUser);
	}
	
//	@Test
//	public void test_adminAuthenticate() throws Exception {
//		AdminUser regUser = new AdminUser();
//		regUser.setAdminId("111a");
//		regUser.setEmail("admin@gmail.com");
//		regUser.setName("Admin");
//		regUser.setPassword("admin");
//		regUser.setPhone(0000000000);
//		regUser.setStatus(true);
//		JwtRequest request =  new JwtRequest();
//		request.setEmail("admin@gmail.com");
//		request.setPassword("admin");
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("userDetails",regUser);
//		jsonObject.put("token","4356ghvgc153138765gvv%^444@3c%cb");
//		when(!request.getPassword().equals(regUser.getPassword())).thenReturn(false);
//		when(adminUserRepository.findByEmail(regUser.getEmail())).thenReturn(regUser);
//		when(adminUserRepository.existsById(regUser.getAdminId())).thenReturn(true);
//		JSONObject responseUser = adminUserService.adminAuthenticate(request);
//		assertEquals(responseUser, responseUser);
//	}
}
