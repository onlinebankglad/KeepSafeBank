package com.lti.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;

import com.lti.dao.BankingDao;
import com.lti.dto.AdminDto;
import com.lti.dto.BeneficaryDto;
import com.lti.exception.CustomerServiceException;
import com.lti.model.Account;
import com.lti.model.Admin;
import com.lti.model.Beneficary;
import com.lti.model.Customer;
import com.lti.model.CustomerMailBox;
import com.lti.model.Transaction;

@Controller
public class BankingServiceImpl implements BankingService {

	@Autowired
	BankingDao bankingdao;

	@Autowired
	EmailService emailService;

	@Override
	public Customer addorUpdateCustomer(Customer customer) {

		Customer cust = null;
		if (!bankingdao.isCustomerPresent(customer.getEmailID())) {
			cust = bankingdao.addorUpdateCustomer(customer);
			String subject = "Registeration done :). Please wait for approval of account and credentials.";
			String text = "Hi" + " " + customer.getCustName() + "You have successfully registered!";
			emailService.sendEmailForNewRegistration(customer.getEmailID(), text, subject);
			System.out.println("Mail sent");
			return cust;
		} else
			throw new CustomerServiceException("Customer already registered!");
	}

	public void contactus(CustomerMailBox pcustomer) {
		bankingdao.contactusmailbox(pcustomer);
		String subject = "Thankyou for contacting us";
		String text = "Hi" + pcustomer.getName()
				+ "Our team will soon reach to you and these are the details of schemes you requested";
		emailService.sendEmailForNewRegistration(pcustomer.getEmail(), text, subject);
		System.out.println("Mail sent");
	}

	public boolean loginCustomer(int custId, String loginPassword) {
		// TODO Auto-generated method stub
		return bankingdao.loginCustomer(custId, loginPassword);
	}

	public Account UpdateAccount(Account account, int custid) {
		return bankingdao.UpdateAccount(account, custid);
	}

	public Account addAccount(Account account) {
		// TODO Auto-generated method stub
		return bankingdao.addAccount(account);
	}

	public Customer findCustomerById(int custId) {
		// TODO Auto-generated method stub
		return bankingdao.findCustomerById(custId);
	}

	public void addAnAdmin(Admin admin) {
		// TODO Auto-generated mDtoethod stub
		bankingdao.addAnAdmin(admin);
	}

	public boolean loginAdmin(int aId, String aPwd) {
		// TODO Auto-generated method stub
		return bankingdao.loginAdmin(aId, aPwd);
	}

	public Account findAccountByNo(int accNo) {
		// TODO Auto-generated method stub
		return bankingdao.findAccountByNo(accNo);
	}

	public List<Beneficary> findBeneficaryAccountByNo(int accNo) {
		// TODO Auto-generated method stub
		return bankingdao.findBeneficaryAccountByNo(accNo);
	}

	public List<Account> viewAllAccounts() {
		// TODO Auto-generated method stub
		return bankingdao.viewAllAccounts();
	}

	public Customer aprroveCustomerbyId(int custId) {
		Customer customer = bankingdao.aprroveCustomerbyId(custId);
		Account acc = null;
		for (Account a : customer.getAccount()) {
			acc = a;
		}

		Random rand = new Random(); // instance of random class
		int upperbound = 99999;
		// generate random values from 0-24
		int int_random = rand.nextInt(upperbound);
		if (int_random < 10000) {
			int_random = int_random + 1000;
		}
		acc.setAccPwd(int_random);
		System.out.println(acc.getAccNo());
		System.out.println(acc.getAccPwd());
		bankingdao.UpdateAccount(acc, custId);
		String subject = "Registeration done";
		String text = "Hi" + " " + customer.getCustName()
				+ "Your account is approved and this is your temporary transaction password" + " " + acc.getAccPwd();
		emailService.sendEmailForNewRegistration(customer.getEmailID(), text, subject);
		System.out.println("Mail sent");
		return customer;
		// TODO Auto-generated method stub
//		return bankingdao.aprroveCustomerbyId(custId);
	}

	public List<Customer> unapprovedCustomerList() {
		// TODO Auto-generated method stub
		return bankingdao.unapprovedCustomerList();
	}

	public List<Customer> approvedCustomerList() {
		// TODO Auto-generated method stub
		return bankingdao.approvedCustomerList();
	}

	public Beneficary addBenefiary(BeneficaryDto beneficarydto) {
		// TODO Auto-generated method stub
		return bankingdao.addBenefiary(beneficarydto);
	}

	public List<Account> findAccountsByCustId(int custId) {
		// TODO Auto-generated method stub
		return bankingdao.findAccountsByCustId(custId);
	}

	public Account addAmountToAccNO(int accNo, double amount) {
		// TODO Auto-generated method stub
		return bankingdao.addAmountToAccNO(accNo, amount);
	}

	public Account deductAmountfromAccNO(int accNo, double amount) {
		// TODO Auto-generated method stub
		return bankingdao.deductAmountfromAccNO(accNo, amount);
	}

	public List<Transaction> viewAllTransactionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return bankingdao.viewAllTransactionByDate(date);
	}

	public List<Beneficary> viewAllBeneficaryofUser(int i) {
		// TODO Auto-generated method stub
		return bankingdao.viewAllBeneficaryofUser(i);
	}

	@Override
	public void register(Customer customer) {
		if (!bankingdao.isCustomerPresent(customer.getEmailID())) {
			bankingdao.save(customer);
			String subject = "Registeration done";
			String text = "Hi" + customer.getCustName() + "You have successfully registered ";
			emailService.sendEmailForNewRegistration(customer.getEmailID(), subject, text);
			System.out.println("Mail sent");
		} else
			throw new CustomerServiceException("Customer already registered!");
	}

	@Override
	public Customer login(String email, String password) {
		try {
			if (!bankingdao.isCustomerPresent(email))
				throw new CustomerServiceException("Customer not registered!");
			int id = bankingdao.findByEmailAndPassword(email, password);
			Customer customer = bankingdao.findById(id);
			return customer;
		} catch (EmptyResultDataAccessException e) {
			throw new CustomerServiceException("Incorrect email/password");
		}
	}

	@Override
	public Customer get(int id) {
		return bankingdao.findById(id);
	}

	@Override
	public void update(Customer customer) {
		bankingdao.save(customer);
	}

	@Override
	public List<Customer> viewAllCustomers() {
		// TODO Auto-generated method stub
		return bankingdao.viewAllCustomers();
	}

	@Override
	public Customer unaprroveCustomerbyId(int custId) {
		// TODO Auto-generated method stub
		return bankingdao.unaprroveCustomerbyId(custId);
	}

	// ---------------------------------------------------------------
	public int generateotp(int cusid) {
		Customer c = bankingdao.findCustomerById(cusid);
		int otp = bankingdao.Generateotp();
		String subject = "OTP";
		String text = "Hi" + " " + c.getCustId() + "this is your generated otp " + otp;
		emailService.sendEmailForNewRegistration(c.getEmailID(), text, subject);
		System.out.println("Mail sent");
		return otp;
	}

	@Override
	public int generateotpforforgetId(int accNO) {
		//
		Account acc = bankingdao.findAccountByNo(accNO);
		Customer c = bankingdao.findCustomerById(acc.getCustomer().getCustId());
		int otp = bankingdao.Generateotp();
		String subject = "OTP";
		String text = "Hi" + " " + c.getCustName() + "this is your customer id" + c.getCustId();
		emailService.sendEmailForNewRegistration(c.getEmailID(), text, subject);
		System.out.println("Mail sent");
		return otp;

	}

	@Override
	public void resetPassword(int custId, String password) {
		bankingdao.resetPassword(custId, password);
	}

	@Override
	public Customer findCustomerIdbyAccNo(int AccNo) {
		// TODO Auto-generated method stub
		return bankingdao.findCustomerIdbyAccNo(AccNo);
	}

	@Override
	public int findBenAccFromBenId(int benId) {
		// TODO Auto-generated method stub
		return bankingdao.findBenAccFromBenId(benId);
	}

	@Override
	public List<Transaction> viewAllTransactionOfUser(int custID) {
		// TODO Auto-generated method stub
		return bankingdao.viewAllTransactionOfUser(custID);
	}

	@Override
	public Transaction addATransaction(int ToAcc, int fromAcc, double amt, String tmode) {
		// TODO Auto-generated method stub
		return bankingdao.addATransaction(ToAcc, fromAcc, amt, tmode);
	}

	public int returnaccpwdfromcustId(int custId) {
		return bankingdao.returnaccpwdfromcustId(custId);
	}

	public void resetAccPassword(int custId, int Accpassword) {

		bankingdao.resetAccPassword(custId, Accpassword);
		Customer c = bankingdao.findCustomerById(custId);
		String subject = "transaction password reset";
		String text = "Hi" + c.getCustName() + "Your transaction password has been changed ";
		emailService.sendEmailForNewRegistration(c.getEmailID(), text, subject);
		System.out.println("Mail sent");
	}
	
	 public void resetAdminPassword(int AdminId, String password) {
         bankingdao.resetAdminPassword(AdminId, password);
     }
}
