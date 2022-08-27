package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.CompanyRepository;
import com.companyservice.companyservice.repository.PaymentRepository;

import net.minidev.json.JSONObject;


@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private SubscriptionService subscriptionService;
	public Payment savePayment(String companyId,String subscriptionId, Payment payload) throws Exception {
		Company company = new Company();
		Payment newPayment= new Payment();
		Subscription subscription = new Subscription();
		company = companyRepository.getById(companyId);
		if(company.getCompanyId() != null) {
			payload.setCompanyId(company);
			subscription = subscriptionService.getById(subscriptionId);
			payload.setSubscriptionId(subscription);
			company.setSubscriptionId(subscription);
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //Date onedayorder = sdf.parse(payload.getPaidDate().toGMTString());
			try {
				newPayment = paymentRepository.save(payload);
				company = companyRepository.save(company);
			}catch(Exception e) {
				//if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				//}
			}
		}
		
		return newPayment;
	}
	
	public Page<Payment> getPaymentDetails(int pageNo,int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Page<Payment> paymentDetails =  paymentRepository.findAll(paging);
			return paymentDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}
	
	public Page<Payment> getPaymentDetails(int pageNo,int pageSize,String companyId) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		Company company = new Company();
		company.setCompanyId(companyId);
		try {
			Page<Payment> paymentDetails =   paymentRepository.getByCompanyId(company,paging);
			return paymentDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}
	
	public Payment getPaymentID(String id) throws Exception {
		try {
			Payment paymentDetails = paymentRepository.getById(id);
			return paymentDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}

	public Payment updatePayment(String id, Payment payload) throws Exception {
		if(paymentRepository.existsById(id)) {
			Payment PaymentDetails = paymentRepository.getById(id);
			if(PaymentDetails.getTransactionId().equals(payload.getTransactionId())) {
				PaymentDetails = setPaymentData(payload, PaymentDetails);
			}
			else {
				if(paymentRepository.existsBytransactionId(payload.getTransactionId())) {
					throw new BadRequestException("Payment details already exist with this transaction Id.");
				}
				else {
					PaymentDetails = setPaymentData(payload, PaymentDetails);
				}
			}
			
			try {
				PaymentDetails = paymentRepository.save(PaymentDetails);
				return PaymentDetails;
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else {
			throw new DetailsNotFound("Payment details not found.!"); 
		}
		return null;
	}

	private Payment setPaymentData(Payment payload, Payment PaymentDetails) {
		if(payload.getTransactionId() != null) {
			PaymentDetails.setTransactionId(payload.getTransactionId());
		}
		if(payload.getCompanyId() != null) {
			PaymentDetails.setCompanyId(payload.getCompanyId());
		}
		if(payload.getSubscriptionId() != null) {
			PaymentDetails.setSubscriptionId(payload.getSubscriptionId());
		}
		if(payload.isStatus()) {
			PaymentDetails.setStatus(payload.isStatus());
		}else if(!payload.isStatus()) {
			PaymentDetails.setStatus(payload.isStatus());
		}
		if(payload.getAmount() != 0 && payload.getAmount() != 0.0) {
			PaymentDetails.setAmount(payload.getAmount());
		}
		if(payload.getPaymentMode() != null) {
			PaymentDetails.setPaymentMode(payload.getPaymentMode());
		}
		//if(payload.getPaidDate() != null) {
		//	PaymentDetails.setPaidDate(payload.getPaidDate());
		//}
		return PaymentDetails;
	}

	public String deletePaymentByID(String id) throws Exception {
		if(paymentRepository.existsById(id)) {
			try {
				paymentRepository.deleteById(id);
				return "Payment deleted successfully.";
			}
			catch(Exception e){
				//if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				//}
			}
		}
		else 
			throw new DetailsNotFound("Payment does not exist on file."); 
			//return null;
	}
	public Page<Payment> filteredPayments(JSONObject payload,int pageNo, int pageSize) throws ParseException {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		Page<Payment> allPayments;
		Company company = new Company();
		Subscription subscription = new Subscription();
		Date startDate = new Date();
		Date endDate = new Date();
		String  paidDate2 = null;
		boolean companyId = false;
		boolean planId = false;
		boolean date = false;
		
		//List<Orders> findByCreateddatetimeBetweenOrderByCreateddatetimeDesc(Date createddateBegin, Date createddateEnd);
		
		if(payload.containsKey("companyId") && payload.getAsString("companyId") != null && payload.getAsString("companyId") != "" ){
			companyId = true;
			company.setCompanyId(payload.getAsString("companyId"));
		}
		if(payload.containsKey("startDate") && payload.getAsString("startDate") != null && payload.getAsString("startDate") != "" ){
			date = true;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String a = payload.getAsString("paidDate");
	        Date onedayorder = sdf.parse(a);
			startDate = onedayorder;
//			endDate.setHours(0);
//			endDate.setMinutes(0);
//			endDate.setSeconds(0);
//			endDate.setDate(startDate.getDate()+1);
//			endDate.setMonth(startDate.getMonth());
//			endDate.setYear(startDate.getYear());
		}
		if(payload.containsKey("endDate") && payload.getAsString("endDate") != null && payload.getAsString("endDate") != "" ){
			date = true;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String a = payload.getAsString("endDate");
	        Date onedayorder = sdf.parse(a);
	        endDate = onedayorder;
		}
		if(payload.containsKey("planId") && payload.getAsString("planId") != null && payload.getAsString("planId") != ""){
			planId = true;
			subscription.setId(payload.getAsString("planId"));
		}
		if(companyId == true && planId == true && date == true) {
			allPayments = paymentRepository.getByCompanyIdAndSubscriptionIdAndPaidDateBetween(company, subscription,startDate,endDate,paging);
			return allPayments;
		}
		if(companyId == false && planId == true && date == true) {
			allPayments = paymentRepository.getBySubscriptionIdAndPaidDateBetween( subscription,startDate,endDate,paging);
			return allPayments;
		}
		if(companyId == true && planId == false && date == true) {
			allPayments = paymentRepository.getByCompanyIdAndPaidDateBetween(company, startDate,endDate,paging);
			return allPayments;
		}
		if(companyId == true && planId == true && date == false) {
			allPayments = paymentRepository.getByCompanyIdAndSubscriptionId(company,  subscription,paging);
			return allPayments;
		}
		if(companyId == true && planId == false && date == false) {
			allPayments = paymentRepository.getByCompanyId(company,paging);
			return allPayments;
		}
		if(companyId == false && planId == true && date == false) {
			allPayments = paymentRepository.getBySubscriptionId( subscription,paging);
			return allPayments;
		}
		if(companyId == false && planId == false && date == true) {
			allPayments = paymentRepository.getByPaidDateBetween( startDate,endDate ,paging);
			return allPayments;
		}
		
		return null;
	}
}
