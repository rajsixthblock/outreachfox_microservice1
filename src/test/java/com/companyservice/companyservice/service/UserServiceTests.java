package com.companyservice.companyservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.Authorization.authorizationservice.model.TokenUtil;
import com.companyservice.companyservice.SecurityConfiguration.SecurityConfig;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Password;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.UserRepository;
import com.companyservice.companyservice.utilities.JwtRequest;

import net.minidev.json.JSONObject;

@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {UserServiceTests.class})
public class UserServiceTests {
	@Mock
	UserRepository userRepository;
	@Mock
	SecurityConfig securityConfig;
	@InjectMocks
	UserService userService;
	@Mock
	MailService mailing;
	TokenUtil tokenUtil = new TokenUtil();
	@Test
	public void test_creation() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User();
		userDetails.setCompanyId(companyDetails);
		userDetails.setUserId("111");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setName("abc");
		userDetails.setPassword("abc");
		userDetails.setPhone(0000000000);
		userDetails.setStatus(true);
		when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(false);
		boolean[] value = {false,true};
		String[] list = {"111",", null"};
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(userRepository.save(userDetails)).thenReturn(userDetails);
				User userResponse = userService.creation(companyDetails.getCompanyId(), userDetails);
				assertEquals(userDetails.getUserId(), userResponse.getUserId());
			}else {
				int id = i;
				userDetails.setCompanyId(null);
				when(userRepository.save(userDetails)).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
				Exception.class, () -> {
					userService.creation(list[id], userDetails);
				           }
				   );
				assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_badRequestException() {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User();
		userDetails.setCompanyId(companyDetails);
		userDetails.setUserId("111");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setName("abc");
		userDetails.setPassword("abc");
		userDetails.setPhone(0000000000);
		userDetails.setStatus(true);
		when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(false);
		boolean value = true;
		if(value) {
			when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(true);
			Throwable ex = assertThrows(
					BadRequestException.class, () -> {
						userService.creation(companyDetails.getCompanyId(), userDetails);
			      });
			   assertEquals("User already exist with this email", ex.getMessage());
			   
		}
	}
//	@Test
//	public void test_authenticate() throws Exception {
//		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
//		User userDetails = new User();
//		userDetails.setCompanyId(companyDetails);
//		userDetails.setUserId("111");
//		userDetails.setEmail("abc@gmail.com");
//		userDetails.setName("abc");
//		userDetails.setPassword("abc");
//		userDetails.setPhone(0000000000);
//		userDetails.setStatus(true);
//		JwtRequest request = new JwtRequest();
//		request.setEmail("abc@gmail.com");
//		request.setPassword("abc");
//		when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(false);
//		when(userRepository.findByEmail(request.getEmail())).thenReturn(userDetails);
//		if(!request.getPassword().equals(userDetails.getPassword())) {
//			
//		}
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("userDetails",userDetails);
//		jsonObject.put("token","112xcbcjb&^hbsjnkn");
//		JSONObject userResponse = userService.authenticate(request);
//		assertEquals(jsonObject.get("token"),jsonObject.get("token"));
//	}
	
	@Test
	public void test_getUsersDetails() throws Exception {
		List<User> users = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		User user2 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		users.add(user2);
		users.add(user1);
		String[] ids = {"111","",null};
		for(int j=0; j< ids.length; j++) {
			if(ids[j] == "111") {
				when(userRepository.findByCompanyId(companyDetails.getCompanyId())).thenReturn(users);
				List<User> responseUsers = userService.getUsersDetails(companyDetails.getCompanyId());
				assertEquals(users.size(), responseUsers.size());
			}else {
				int id = j;
				when(userRepository.findByCompanyId(ids[id])).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
						Exception.class, () -> {
							userService.getUsersDetails(ids[id]);
				});
				assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_getUserByID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		
		String[] value = {"111",null,""};
		for(int i=0; i< value.length; i++) {
			if(value[i]== "111") {
				when(userRepository.getById(user1.getUserId())).thenReturn(user1);
				User responseUsers = userService.getUserByID(user1.getUserId());
				assertEquals(user1.getUserId(), responseUsers.getUserId());
			}else {
				int id = i;
				when(userRepository.getById(value[id])).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
						Exception.class, () -> {
							userService.getUserByID(value[id]);
				});
				assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_dataNotFoundExceptionForDelete() {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User();
		userDetails.setCompanyId(companyDetails);
		userDetails.setUserId("111");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setName("abc");
		userDetails.setPassword("abc");
		userDetails.setPhone(0000000000);
		userDetails.setStatus(true);
//		when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(false);
		boolean value = true;
		if(value == true) {
			when(userRepository.existsById(userDetails.getUserId())).thenReturn(false);
			userRepository.deleteById(userDetails.getUserId());
			Throwable ex = assertThrows(
					DetailsNotFound.class, () -> {
						userService.deleteUserByID(userDetails.getUserId());
			      });
			   assertEquals("User does not exist.!", ex.getMessage());
			   
		}
	}
	@Test
	public void test_deleteUserByID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		String message = "Successfull";
		String[] value = {"111",null,""};
		
		for(int i=0; i< value.length; i++) {
			if(value[i]== "111") {
				when(userRepository.existsById(user1.getUserId())).thenReturn(true);
				userRepository.deleteById(user1.getUserId());
				String responseUsers = userService.deleteUserByID(user1.getUserId());
				assertEquals(responseUsers, "User deleted successfully");
			}else {
				int id = i;
				Exception e = new Exception();
				when(userRepository.existsById(user1.getUserId())).thenReturn(true);
				doThrow(new NestedRuntimeException("Exception occured") {}).when(userRepository).deleteById("111");
				Throwable exception = assertThrows(
						Exception.class, () -> {
							userService.deleteUserByID("111");
				});
				assertEquals("Exception occured", exception.getMessage());
			}
		}	
	}
//	@Test
//	public void test_updateUser() throws Exception {
//		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
//		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
//		String message = "Successfull";
//		boolean[] value = {true,false};
//		for(int i=0; i< value.length; i++) {
//			if(value[i]) {
//				when(userRepository.getById(user1.getUserId())).thenReturn(user1);
//				for(int k=0; k< value.length; k++) {
//					if(value[k]) {
//						userService.setUserData(user1, user1);
//					}else {
//						for(int j=0; j< value.length; j++) {
//							if(value[j]) {
//								when(userRepository.getById(user1.getUserId())).thenReturn(user1);
//								when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);
//								Throwable ex = assertThrows(
//										BadRequestException.class, () -> {
//											userService.updateUser(user1.getUserId(),user1);
//								      });
//								   assertEquals("User already exist with this email", ex.getMessage());
//							}else {
//								userService.setUserData(user1, user1);
//							}
//						}
//					}
//				}
//				when(userRepository.save(user1)).thenReturn(user1);
//				User responseUsers = userService.updateUser(user1.getUserId(),user1);
//				assertEquals(responseUsers.getUserId(), user1.getUserId());
//				
//			}else {
//				when(userRepository.existsById(user1.getUserId())).thenReturn(false);
//				Throwable ex = assertThrows(
//						DetailsNotFound.class, () -> {
//							userService.updateUser(user1.getUserId(),user1);
//				      });
//				   assertEquals("User details not found", ex.getMessage());
//			}	
//		}
//		
//	}
	@Test
	public void test_dataNotFoundExceptionForUpdate(){
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		boolean value = true;
		if(value == true) {
			when(userRepository.existsById(userDetails.getUserId())).thenReturn(false);
			//userRepository.deleteById(userDetails.getUserId());
			Throwable ex = assertThrows(
					DetailsNotFound.class, () -> {
						userService.updateUser(userDetails.getUserId(),userDetails);
			      });
			   assertEquals("User details not found", ex.getMessage());
			   
		}
	}
//	@Test
//	public void test_badRequestExceptionForUpdate() throws Exception{
//		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
//		User userDetails = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
//		boolean[] value = {true,false};
//		String[] emails = {"userEmail@gmail.com",""};
//		boolean b = false;
//		if(b == true) {
//			
//		}else {
//			when(userRepository.existsById(userDetails.getUserId())).thenReturn(true);
//			when(userRepository.getById(userDetails.getUserId())).thenReturn(userDetails);
//			when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(true);
//			for(int i=0; i<value.length;i++) {
//				when(userRepository.existsById(userDetails.getUserId())).thenReturn(true);
//				when(userRepository.getById(userDetails.getUserId())).thenReturn(userDetails);
//				when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(true);
//				if(value[i] == true) {
//					when(userRepository.existsById(userDetails.getUserId())).thenReturn(true);
//					when(userRepository.getById(userDetails.getUserId())).thenReturn(userDetails);
//					when(userRepository.existsByEmail(userDetails.getEmail())).thenReturn(true);
//					Throwable ex = assertThrows(
//							BadRequestException.class, () -> {
//								userService.updateUser(userDetails.getUserId(),userDetails);
//					      });
//					   assertEquals("User already exist with this email", ex.getMessage());
//				}else {
//					userService.setUserData(userDetails,userDetails);
//				}
//			}	
//		}
//		
//	}
	@Test
	public void test_forgotPassword() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		String message = "Successfull";
		JwtRequest request = new JwtRequest();
		request.setEmail("abc@gmail.com");
		request.setPassword("abc");
		when(userRepository.existsById(user1.getUserId())).thenReturn(true);
		when(userRepository.getById(user1.getUserId())).thenReturn(user1);
		List list = new ArrayList();
		list.add(user1);
		list.add(null);
		for(int i=0; i< list.size(); i++) {
			if(list.get(i) != null) {
				when(userRepository.save(user1)).thenReturn(user1);
				User responseUsers = userService.forgotPassword(user1.getUserId(),request);
				assertEquals(responseUsers.getUserId(), user1.getUserId());
			}else {
//				when(userRepository.save(null)).thenReturn(new NestedRuntimeException("Exception occured") {});
				//User responseUsers = userService.forgotPassword(user1.getUserId(),request);
				Throwable exception = assertThrows(
						BadRequestException.class, () -> {
							userService.forgotPassword(null,request);
				});
				assertEquals("Please provide valid details.!", exception.getMessage());
				//assertEquals(responseUsers.getUserId(), user1.getUserId());
			}
		}
		
	}
//	@Test
//	public void test_updatePassword() throws Exception {
//		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
//		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
//		Password request = new Password();
//		request.setNewPassword("abc12");
//		request.setOldPassword("abc");
//		when(userRepository.existsById(user1.getUserId())).thenReturn(true);
//		when(userRepository.getById(user1.getUserId())).thenReturn(user1);
//		user1.setPassword(securityConfig.passwordEncryption(request.getNewPassword()));
//		when(userRepository.save(user1)).thenReturn(user1);
//		request.getOldPassword().equals(user1.getPassword());
//		User responseUsers = userService.updatePassword(user1.getUserId(),request);
//		assertEquals(responseUsers.getUserId(), user1.getUserId());
//	}
	
	
}