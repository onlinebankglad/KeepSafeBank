package com.lti.dao;

import java.time.LocalDate;
import java.util.List;

import com.lti.dto.AdminDto;
import com.lti.dto.BeneficaryDto;
import com.lti.model.Account;
import com.lti.model.Admin;
import com.lti.model.Beneficary;
import com.lti.model.Customer;
import com.lti.model.CustomerMailBox;
import com.lti.model.Transaction;

public interface BankingDao {

	Customer addorUpdateCustomer(Customer customer); //done

	boolean loginCustomer(int custId, String loginPassword); 

	Account addAccount(Account account); //done

	Account UpdateAccount(Account account, int custid); //done

	Customer findCustomerById(int custId); //done
	
	Customer findCustomerIdbyAccNo(int AccNo);

	void addAnAdmin(Admin admin);

	boolean loginAdmin(int aId, String aPwd);

	Account findAccountByNo(int accNo); //done

	List<Beneficary> findBeneficaryAccountByNo(int accNo);

	List<Account> viewAllAccounts(); //done
	
	List<Customer> viewAllCustomers(); //done


	Customer aprroveCustomerbyId(int custId);
	
	Customer unaprroveCustomerbyId(int custId);


	List<Customer> unapprovedCustomerList();

	List<Customer> approvedCustomerList();

	Beneficary addBenefiary(BeneficaryDto beneficarydto); //done

	List<Account> findAccountsByCustId(int custId); //done


	Account addAmountToAccNO(int accNo, double amount); //done

	Account deductAmountfromAccNO(int accNo, double amount); //done

	
	public Transaction addATransaction(int ToAcc ,int fromAcc ,double amt, String tmode);

	List<Transaction> viewAllTransactionByDate(LocalDate date);

	List<Beneficary> viewAllBeneficaryofUser(int i);
	
	void save(Customer customer);

	Customer findById(int id);

	List<Customer> findAll();

	int findByEmailAndPassword(String email, String password);

	boolean isCustomerPresent(String email);
	
	public void contactusmailbox(CustomerMailBox cust);
	
	public int Generateotp();
	
	public void resetPassword(int custId, String password);
	
	public int findBenAccFromBenId(int benId);
	
	public List<Transaction> viewAllTransactionOfUser(int custID);

	int returnaccpwdfromcustId(int custId);	
	
	public void resetAccPassword(int custId, int Accpassword);

	void resetAdminPassword(int adminId, String password);
	
}
