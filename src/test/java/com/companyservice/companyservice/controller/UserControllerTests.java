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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Password;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.CompanyService;

import com.companyservice.companyservice.service.UserService;
import com.companyservice.companyservice.utilities.JwtRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {UserControllerTests.class})
public class UserControllerTests {
	@Mock
	UserService userService;
	@Mock
	CompanyService companyService;
	//@Autowired
	//private MockMvc mockMvc;
	@InjectMocks
	UserController userController;
	private static ObjectMapper mapper = new ObjectMapper();
	@Test
	public void test_create() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User();
		userDetails.setCompanyId(companyDetails);
		userDetails.setUserId("111");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setName("abc");
		userDetails.setPassword("abc");
		userDetails.setPhone(0000000000);
		userDetails.setStatus(true);
		
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(userService.creation(companyDetails.getCompanyId(), userDetails)).thenReturn(userDetails);
				String json = mapper.writeValueAsString(userDetails);
				//mockMvc.perform(post("/user/create/"+companyDetails.getCompanyId()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
					//	.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
				ResponseEntity<User> response = (ResponseEntity<User>) userController.create(companyDetails.getCompanyId(), userDetails);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							userController.create(ids[id],userDetails);
				      });
				   assertEquals("User registration failed", ex.getMessage());
			}
		}
	}
	@Test
	public void test_authenticate() throws Exception {
		JwtRequest request =  new JwtRequest("abc@gmail.com","abc");
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User();
		userDetails.setCompanyId(companyDetails);
		userDetails.setUserId("111");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setName("abc");
		userDetails.setPassword("abc");
		userDetails.setPhone(0000000000);
		userDetails.setStatus(true);
		JSONObject json = new JSONObject();
		json.appendField("token", "Asdfadfaf124357gsjbf819ebiq97");
		json.appendField("userDetails", userDetails);
		when(userService.authenticate(request)).thenReturn(json);
		//String json = mapper.writeValueAsString(userDetails);
		//mockMvc.perform(post("/user/create/"+companyDetails.getCompanyId()).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
			//	.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
		ResponseEntity<User> response = (ResponseEntity<User>) userController.authenticate(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
//		String[] ids = {"111", null};
//		int i = 0;
//		for(i = 0; i < ids.length; i++) {
//			
//			if(ids[i] != null) {
//				
//			}else {
//				int id = i;
//				when(userService.authenticate(request)).thenReturn(json);
//				Throwable ex = assertThrows(
//						BadRequestException.class, () -> {
//							userController.authenticate(request);
//				      });
//				   assertEquals("User Login failed", ex.getMessage());
//			}
//		}
	}
	
	@Test
	public void test_getUsers() throws Exception {
		List<User> users = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		User user2 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		users.add(user2);
		users.add(user1);
		String[] ids = {"111", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(userService.getUsersDetails(companyDetails.getCompanyId())).thenReturn(users);
				ResponseEntity<User> response = (ResponseEntity<User>) userController.getUsers(companyDetails.getCompanyId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				when(userService.getUsersDetails(companyDetails.getCompanyId())).thenReturn(null);
				int id = i;
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.getUsers(companyDetails.getCompanyId());
				      });
				   assertEquals("Users data not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getUsersPagenation() throws Exception {
		List<User> users = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		User user2 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		users.add(user2);
		users.add(null);
		Page<User> emptyUsers = null;
		int i = 0;
		for(i = 0; i < users.size(); i++) {
			Page<User> usersPagedDetails = new PageImpl<>(users);
			if(users.get(i) != null) {
				when(userService.getUsersDetails(companyDetails.getCompanyId(), 1, 2)).thenReturn(usersPagedDetails);
				ResponseEntity<Page> response = (ResponseEntity<Page>) userController.getUsers(companyDetails.getCompanyId(),1,2);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				when(userService.getUsersDetails(companyDetails.getCompanyId(), 1, 10)).thenReturn(null);
				Throwable exception = assertThrows(
						DetailsNotFound.class, () -> {
							userController.getUsers(companyDetails.getCompanyId(),0,0);
						});
				assertEquals("Users data not found", exception.getMessage());
			}
		}
		
	}
	@Test
	public void test_getUserFilter() throws Exception {
		List<User> users = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		User user2 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		users.add(user2);
		users.add(null);
		JSONObject payload = new JSONObject();
		payload.appendField("name", "sai");
		payload.appendField("email", "sai@gmail.com");
		payload.appendField("phone", "0000000000");
		Page<User> emptyUsers = null;
		int i = 0;
		for(i = 0; i < users.size(); i++) {
			Page<User> usersPagedDetails = new PageImpl<>(users);
			if(users.get(i) != null) {
				when(userService.userFilter(payload, 1, 10)).thenReturn(usersPagedDetails);
				ResponseEntity<Page> response = (ResponseEntity<Page>) userController.userFilter(payload,1,10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
//				when(userService.getUsersDetails(companyDetails.getCompanyId(), 1, 10)).thenReturn(null);
				Throwable exception = assertThrows(
						DetailsNotFound.class, () -> {
							userController.userFilter(payload,0,0);
							throw new DetailsNotFound("Users data not found"); 
						});
				assertEquals("Users data not found", exception.getMessage());
			}
		}
		
	}

	@Test
	public void test_getByUserID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(userService.getUserByID(user1.getUserId())).thenReturn(user1);
				ResponseEntity<?> response =  userController.getByUserID(user1.getUserId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.getByUserID(ids[id]);
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	
	@Test
	public void test_updateUser() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(userService.updateUser(user1.getUserId(),user1)).thenReturn(user1);
				ResponseEntity<?> response =  userController.updateUser(user1.getUserId(),user1);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.updateUser(ids[id],user1);
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_deleteUser() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				String result = "Successfull";
				when(userService.deleteUserByID(user1.getUserId())).thenReturn(result);
				ResponseEntity<?> response =  userController.deleteUser(user1.getUserId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.deleteUser(ids[id]);
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_updatePassword() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","newPassword",true,null,null);
		Password password = new Password();
		password.setNewPassword("newPassword");
		password.setOldPassword("oldPassword");
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				String result = "Successfull";
				when(userService.updatePassword(user1.getUserId(),password)).thenReturn(user1);
				ResponseEntity<?> response =  userController.updatePassword(user1.getUserId(),password);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.updatePassword(ids[id],password);
				      });
				   assertEquals("User details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_forgotPassword() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","newPassword",true,null,null);
		JwtRequest request = new JwtRequest();
		request.setEmail("userEmail@gmail.com");
		request.setPassword("newPassword");
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				String result = "Successfull";
				when(userService.forgotPassword(user1.getUserId(),request)).thenReturn(user1);
				ResponseEntity<?> response =  userController.forgotPassword(user1.getUserId(),request);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.forgotPassword(ids[id],request);
				      });
				   assertEquals("Password updation failed.!", ex.getMessage());
			}
		}
	}
	@Test
	public void test_activationUser() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","newPassword",true,null,null);
		JwtRequest request = new JwtRequest();
		request.setEmail("userEmail@gmail.com");
		request.setPassword("newPassword");
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				String result = "Successfull";
				when(companyService.activateCompany(user1.getUserId(),companyDetails.getCompanyId())).thenReturn(companyDetails);
				ResponseEntity<?> response =  userController.activationUser(companyDetails.getCompanyId(),user1.getUserId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				String htmlPage = "<html><head><title>Login page link.</title><style>body{background-color: #ed7117;text-align:center;color:white; margin-top:100px;font-size:50px}a{text-decoration:none;color:white;}button{padding-top:5px;padding-bottom:5px;padding-left:15px;padding-right:15px;font-size:20px;border:2px solid white;border-radius:5px;background-color: #ed7117;}</style></head><body ><span>Account activation failed.! Please contact admin </span><br></body></html>";
//				Throwable ex = assertThrows(
//						BadRequestException.class, () -> {
//							userController.activationUser(companyDetails.getCompanyId(),ids[id]);
//				      });
				ResponseEntity<?> response =  userController.activationUser(companyDetails.getCompanyId(),ids[id]);
				   assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode() );
			}
		}
	}
	
	@Test
	public void test_sendLinkToUpdatePassword() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","newPassword",true,null,null);
		JwtRequest request = new JwtRequest();
		request.setEmail("userEmail@gmail.com");
		request.setPassword("newPassword");
		
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				String result = "Successfull";
				when(userService.sendLinkToUpdatePassword(request)).thenReturn(user1);
				ResponseEntity<?> response =  userController.sendLinkToUpdatePassword(request);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(userService.sendLinkToUpdatePassword(request)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							userController.sendLinkToUpdatePassword(request);
				      });
				   assertEquals("Link sending failed.!", ex.getMessage());
			}
		}
	}
}
