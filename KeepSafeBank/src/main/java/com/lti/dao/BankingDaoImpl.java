package com.lti.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.lti.dto.AdminDto;
import com.lti.dto.BeneficaryDto;
import com.lti.model.Account;
import com.lti.model.Admin;
import com.lti.model.Beneficary;
import com.lti.model.Customer;
import com.lti.model.CustomerMailBox;
import com.lti.model.Tmodes;
import com.lti.model.Transaction;

@Repository
public class BankingDaoImpl implements BankingDao {

	@PersistenceContext
	EntityManager em; /*
						 * // The entity manager implements the API and encapsulates all of them within
						 * a single interface. Entity manager is used to read, delete and write an
						 * entity. An object referenced by an entity is managed by entity manager.
						 */

	@Transactional
	public Customer addorUpdateCustomer(Customer customer) {

		Customer cust = em.merge(customer);// update when there is an existing field/data or make new entry in table
		return cust;
	}

	public boolean loginCustomer(int custId, String loginPassword) {

		String jql = "select c from Customer c where c.custId=:cId and c.loginPassword=:cpass";

		TypedQuery<Customer> query = em.createQuery(jql, Customer.class);
		query.setParameter("cId", custId);
		query.setParameter("cpass", loginPassword);

		Customer customer = null;
		try {
			customer = query.getSingleResult();
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (customer == null)
			return false;

		return true;

	}

	@Transactional
	public Account addAccount(Account account) {
		Account acc = em.merge(account);
		return acc;
	}

	@Transactional
	public Account UpdateAccount(Account account, int custid) {
		String jpql = "select a from Account a where a.customer.custId=:custid and a.accNo=:accNo";
		TypedQuery<Account> query = em.createQuery(jpql, Account.class);
		query.setParameter("custid", custid);
		query.setParameter("accNo", account.getAccNo());
		Account acc = query.getSingleResult();

		Account accPersisted = null;
		if (acc != null) {
			acc.setAccBal(account.getAccBal());
			accPersisted = addAccount(acc);
		}
		return accPersisted;
	}

	@Transactional
	public Beneficary addBenefiary(BeneficaryDto beneficarydto) {
		Beneficary b = new Beneficary();
		Account a = findAccountByNo(beneficarydto.getAccNo());
		Customer c = findCustomerById(beneficarydto.getCustId());
		b.setCustomer(c);
		b.setAccount(a);
		b.setBnickName(beneficarydto.getBnickName());
		b.setbName(a.getCustomer().getCustName());

		return em.merge(b);

	}

	public Customer findCustomerById(int custId) {
		return em.find(Customer.class, custId);
	}

	/*
	 * @Transactional public Account addUpdateAccount(Address address, int userId) {
	 * User user=findUserById(userId); Address addressPersisted=null;
	 * if(user!=null){ addressPersisted = em.merge(address);
	 * addressPersisted.setUser(user); }
	 * 
	 * return addressPersisted; }
	 */

	public Customer findCustomerIdbyAccNo(int AccNo) {
		Account account = em.find(Account.class, AccNo);
		return account.getCustomer();

	}

	@Transactional
	public void addAnAdmin(Admin admin) {
		em.persist(admin);
	}

	public boolean loginAdmin(int aId, String aPwd) {

		String jql = "select a from Admin a where a.aId=:adminid and a.aPwd=:apass";

		TypedQuery<Admin> query = em.createQuery(jql, Admin.class);
		query.setParameter("adminid", aId);
		query.setParameter("apass", aPwd);

		Admin admin = null;
		try {
			admin = query.getSingleResult();
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (admin == null)
			return false;

		return true;

	}

	public Account findAccountByNo(int accNo) {
		return em.find(Account.class, accNo); // find() is an inbuild function
	}

	public List<Account> viewAllAccounts() {

		String jpql = "Select a from Account a";// select * from Account

		TypedQuery<Account> query = em.createQuery(jpql, Account.class);
		List<Account> accs = query.getResultList();

		return accs;

	}

	@Transactional
	public Customer aprroveCustomerbyId(int custId) {
		Customer customer = findCustomerById(custId);
		customer.setApproved(true);
		Account a = new Account();
		a.setCustomer(customer);
		addAccount(a);
		return addorUpdateCustomer(customer);

	}

	public List<Customer> unapprovedCustomerList() {
		String jpql = "select c from Customer c where c.isApproved=0";
		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
		return query.getResultList();

	}

	public List<Customer> approvedCustomerList() {
		String jpql = "select c from Customer c where c.isApproved=1";
		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
		return query.getResultList();

	}

	public List<Account> findAccountsByCustId(int custId) {
		String jqpl = "select a from Account a where a.customer.custId=:cId";

		TypedQuery<Account> query = em.createQuery(jqpl, Account.class);
		query.setParameter("cId", custId);

		List<Account> accts = query.getResultList();
		return accts;
	}

	public List<Beneficary> viewAllBeneficaryofUser(int custId) {
		String jqpl = "select b from Beneficary b where b.customer.custId=:cId";
		TypedQuery<Beneficary> query = em.createQuery(jqpl, Beneficary.class);
		query.setParameter("cId", custId);
		List<Beneficary> accts = query.getResultList();

		return accts;

	}

	public List<Beneficary> findBeneficaryAccountByNo(int OwneraccNo) {
		String jpql = "Select b from Beneficary b where b.account.accNo=:accNo";
		TypedQuery<Beneficary> query = em.createQuery(jpql, Beneficary.class);
		query.setParameter("accNo", OwneraccNo);
		List<Beneficary> bfcs = query.getResultList();
		return query.getResultList(); // find() is an inbuild function
	}

	public Account addAmountToAccNO(int accNo, double amount) {
		Account acc = em.find(Account.class, accNo);
		double bal = acc.getAccBal();
		bal = bal + amount;
		acc.setAccBal(bal);
		return addAccount(acc);
	}

	public Account deductAmountfromAccNO(int accNo, double amount) {

		Account acc = em.find(Account.class, accNo);
		double bal = acc.getAccBal();
		bal = bal - amount;
		acc.setAccBal(bal);
		return addAccount(acc);
	}

	@Transactional
	public Transaction addATransaction(int ToAcc, int fromAcc, double amt, String tmodes) {

		Account fromacc = deductAmountfromAccNO(fromAcc, amt);
		Account toAcc = addAmountToAccNO(ToAcc, amt);
		Account toacc = findAccountByNo(ToAcc);
		Transaction transaction1 = new Transaction();
		transaction1.setAccount(fromacc);
		transaction1.setToAccNO(toacc.getAccNo());
		transaction1.setAmount(amt);
		transaction1.setTmodes(tmodes);
		transaction1.settDate(LocalDate.now());
		Transaction transaction2 = em.merge(transaction1);
		return transaction2;

	}

	@Transactional
	public List<Transaction> viewAllTransactionOfUser(int custID) {
		Customer cust = em.find(Customer.class, custID);
		System.out.println("Cust is here" + cust.getCustName());
		Account acc = null;
		for (Account a : cust.getAccount()) {
			acc = a;
		}
		int ac = acc.getAccNo();
		System.out.println(ac);
		String jqpl = "select t from Transaction t where to_accno=:accno or fromacc_no=:accno order by tDate desc";

		TypedQuery<Transaction> query = em.createQuery(jqpl, Transaction.class);
		query.setParameter("accno", acc);
//       
		List<Transaction> bfcs = query.getResultList();
		for (Transaction t : bfcs) {
			if (t.getToAccNO() == ac) {
				t.settType("credit");
				em.merge(t);
			}
		}
		System.out.println("We are here");
		return bfcs;
	}

	public List<Transaction> viewAllTransactionByDate(LocalDate date) {
		String jqpl = "select t from Transaction t where tDate=:date";

		TypedQuery<Transaction> query = em.createQuery(jqpl, Transaction.class);
		query.setParameter("date", date);

		List<Transaction> bfcs = query.getResultList();
		return bfcs;
	}

	public Beneficary addBenefiary(Beneficary beneficary, int custId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		em.merge(customer);
	}

	public Customer findById(int id) {
		// TODO Auto-generated method stub
		return em.find(Customer.class, id);
	}

	public List<Customer> findAll() {
		return em.createNamedQuery("fetch-all").getResultList();
	}

	public int findByEmailAndPassword(String email, String password) {
		return (Integer) em
				.createQuery("select c.custId from Customer c where c.emailID = :em and c.loginPassword = :pw")
				.setParameter("em", email).setParameter("pw", password).getSingleResult();
	}

	public boolean isCustomerPresent(String email) {
		return (Long) em.createQuery("select count(c.custId) from Customer c where c.emailID = :em")
				.setParameter("em", email).getSingleResult() == 1 ? true : false;
	}

	@Transactional
	public void contactusmailbox(CustomerMailBox cust) {

		em.merge(cust);
	}

	@Override
	public List<Customer> viewAllCustomers() {
		// TODO Auto-generated method stub
		String jpql = "Select a from Customer a";// select * from Account

		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
		List<Customer> customers = query.getResultList();

		return customers;
	}

	@Override
	public Customer unaprroveCustomerbyId(int custId) {
		// TODO Auto-generated method stub
		Customer customer = findCustomerById(custId);
		customer.setApproved(false);
		return addorUpdateCustomer(customer);
	}

//---------------------------Risky------------------------------
	public int Generateotp() {
		Random rand = new Random(); // instance of random class
		int upperbound = 200;
		// generate random values from 0-24
		int int_random = rand.nextInt(upperbound);
		return int_random;
	}

	@Transactional
	public void resetPassword(int custId, String password) {

		Customer customer = findCustomerById(custId);
		customer.setLoginPassword(password);
		em.merge(customer);
	}

	@Transactional
	public int findBenAccFromBenId(int benId) {
		Beneficary bento = em.find(Beneficary.class, benId);
		Account acc = bento.getAccount();

		return acc.getAccNo();

	}

	public int returnaccpwdfromcustId(int custId) {
		Customer cust = em.find(Customer.class, custId);
		Account acc = null;
		for (Account a : cust.getAccount()) {
			acc = a;
		}

		int Password = acc.getAccPwd();
		return Password;

	}

	@Transactional
	public void resetAccPassword(int custId, int Accpassword) {

		Customer customer =em.find(Customer.class, custId);
		Account acc = null;
		for (Account a : customer.getAccount()) {
			acc = a;
		}
		acc.setAccPwd(Accpassword);
		em.merge(acc);
	}

	@Transactional
	public void resetAdminPassword(int AdminId, String password){
	  
	   
	    Admin admin=em.find(Admin.class, AdminId);
	    System.out.println("in daoooo");
	    admin.setaPwd(password);
	    System.out.println("in 2 daoooo");

	    em.merge(admin);
	}
}
