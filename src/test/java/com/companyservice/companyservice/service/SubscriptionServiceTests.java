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

import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.repository.SubscriptionRepository;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {SubscriptionServiceTests.class})
public class SubscriptionServiceTests {
	@Mock
	SubscriptionRepository subscriptionRepository;
	@InjectMocks
	SubscriptionService subscriptionService;
	
	@Test
	public void test_create() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		when(subscriptionRepository.existsByPlanNameAndPlanType(subscription.getPlanName(), subscription.getPlanType())).thenReturn(false);
		when(subscriptionRepository.save(subscription)).thenReturn(subscription);
		Subscription res = subscriptionService.create(subscription);
		assertEquals(subscription.getId(),subscription.getId());
	}
	@Test
	public void test_getById() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		when(subscriptionRepository.getById(subscription.getId())).thenReturn(subscription);
		Subscription res = subscriptionService.getById(subscription.getId());
		assertEquals(res.getId(),subscription.getId());
	}
	@Test
	public void test_deleteSubscriptionByID() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		subscriptionRepository.deleteById(subscription.getId());
		when(subscriptionRepository.existsById(subscription.getId())).thenReturn(true);
		String res = subscriptionService.deleteSubscriptionByID(subscription.getId());
		assertEquals(res,"Plan deleted successfully");
	}
	@Test
	public void test_updateSubscription() throws Exception {
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		subscription = subscriptionService.setsubscriptionData(subscription, subscription);
		when(subscriptionRepository.existsById(subscription.getId())).thenReturn(true);
		when(subscriptionRepository.getById(subscription.getId())).thenReturn(subscription);
//		when(subscriptionRepository.existsByPlanNameAndPlanType(subscription.getPlanName(), subscription.getPlanType())).thenReturn(false);
		when(subscriptionRepository.save(subscription)).thenReturn(subscription);
		Subscription res = subscriptionService.updateSubscription(subscription.getId(),subscription);
		assertEquals(res.getId(),subscription.getId());
	}
}
