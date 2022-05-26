package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.PaymentRepository;


@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment savePayment(String companyId,String subscriptionId, Payment payload) throws Exception {
		
		Payment newPayment= new Payment();
		
		Company company = new Company();
		company.setCompanyId(companyId);
		payload.setCompanyId(company);
		
		Subscription subscription = new Subscription();
		subscription.setId(subscriptionId);
		payload.setSubscriptionId(subscription);
		
		try {
			newPayment = paymentRepository.save(payload);
		}catch(Exception e) {
			if(e instanceof DataIntegrityViolationException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return newPayment;
	}
	
	public List<Payment> getPaymentDetails() throws Exception {
		try {
			List<Payment> paymentDetails = (List<Payment>) paymentRepository.findAll();
			return paymentDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public List<Payment> getPaymentDetails(String companyId) throws Exception {
		Company company = new Company();
		company.setCompanyId(companyId);
		try {
			List<Payment> paymentDetails =  ((PaymentRepository) paymentRepository).getByCompanyId(company);
			return paymentDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public Optional<Payment> getPaymentID(String id) throws Exception {
		try {
			Optional<Payment> paymentDetails = paymentRepository.findById(id);
			return paymentDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
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
		if(payload.getAmount() != null) {
			PaymentDetails.setAmount(payload.getAmount());
		}
		if(payload.getPaymentMode() != null) {
			PaymentDetails.setPaymentMode(payload.getPaymentMode());
		}
		if(payload.getPaidDate() != null) {
			PaymentDetails.setPaidDate(payload.getPaidDate());
		}
		return PaymentDetails;
	}

	public String deletePaymentByID(String id) throws Exception {
		if(paymentRepository.existsById(id)) {
			try {
				paymentRepository.deleteById(id);
				return "Payment deleted successfully.";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("Payment does not exist on file."); 
			return null;
	}
}
