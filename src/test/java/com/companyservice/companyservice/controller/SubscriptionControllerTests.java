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

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.SubscriptionService;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {SubscriptionControllerTests.class})
public class SubscriptionControllerTests {
	@Mock
	SubscriptionService subscriptionService;
	@InjectMocks
	SubscriptionController subscriptionController;
	
	@Test
	public void test_creation() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		String[] ids = {"111", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(subscriptionService.create(subscription)).thenReturn(subscription);
				ResponseEntity response = subscriptionController.creation(subscription);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(subscriptionService.create(subscription)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							subscriptionController.creation(subscription);
				      });
				   assertEquals("Plan Creation failed", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getByPlanID() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		String[] ids = {"111a", null};
		int i = 0;
		for(i = 0; i <ids.length; i++) {
			if(ids[i]!= null) {
				when(subscriptionService.getById(subscription.getId())).thenReturn(subscription);
				ResponseEntity response = subscriptionController.getByPlanID(subscription.getId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(subscriptionService.getById(subscription.getId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							subscriptionController.getByPlanID(ids[id]);
				      });
				   assertEquals("Plan details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getPlans() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		String[] ids = {"111a", null};
		List list = new ArrayList();
		list.add(subscription);
		list.add(null);
		Page<Payment> emptyPage = null;
		int i = 0;
		for(i = 0; i <ids.length; i++) {
			if(ids[i]!= null) {
				Page<Subscription> pageDetails = new PageImpl<>(list);
				when(subscriptionService.getAll(1,2)).thenReturn(pageDetails);
				ResponseEntity response = subscriptionController.getPlans(1,2);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(subscriptionService.getAll(1,2)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							subscriptionController.getPlans(0,0);
				      });
				   assertEquals("No plans found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getPlansByType() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		String[] ids = {"111a", null};
		List list = new ArrayList();
		list.add(subscription);
		list.add(null);
		Page<Subscription> emptyPage = null;
		Page<Subscription> pageDetails = new PageImpl<>(list);
		List result = new ArrayList();
		result.add(pageDetails);
		result.add(emptyPage);
		String type = "monthly";
		int i = 0;
		for(i = 0; i < result.size(); i++) {
			if(result.get(i)!= null) {
				when(subscriptionService.getAllByType(1,10,type)).thenReturn(pageDetails);
				ResponseEntity response = subscriptionController.getPlansByType(1,10,type);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(subscriptionService.getAllByType(1,10,type)).thenReturn(emptyPage);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							subscriptionController.getPlansByType(0,0,type);
				      });
				   assertEquals("No plans found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_updatePlan() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		String[] ids = {"111a", null};
		int i = 0;
		for(i = 0; i <ids.length; i++) {
			if(ids[i]!= null) {
				when(subscriptionService.updateSubscription(subscription.getId(), subscription)).thenReturn(subscription);
				ResponseEntity response = subscriptionController.updatePlan(subscription.getId(),subscription);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(subscriptionService.updateSubscription(subscription.getId(), subscription)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							subscriptionController.updatePlan(ids[id],subscription);
				      });
				   assertEquals("Plan details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_deletePlan() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		String[] ids = {"111a", null};
		int i = 0;
		for(i = 0; i <ids.length; i++) {
			if(ids[i]!= null) {
				when(subscriptionService.deleteSubscriptionByID(subscription.getId())).thenReturn("Successfull");
				ResponseEntity response = subscriptionController.deletePlan(subscription.getId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(subscriptionService.deleteSubscriptionByID(subscription.getId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							subscriptionController.deletePlan(ids[id]);
				      });
				   assertEquals("Plan details not found", ex.getMessage());
			}
		}
	}
}
