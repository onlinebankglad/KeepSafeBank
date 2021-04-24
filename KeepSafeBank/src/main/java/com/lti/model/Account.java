package com.lti.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="tbl_accountsss")
public class Account {
	@Id
	@SequenceGenerator(name = "a_seq", initialValue = 200, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_seq")
	int accNo;
	
	double accBal=10000;
	String accType = "saving";
	int accPwd; // transaction password + otp

	@ManyToOne
	@JoinColumn(name = "cust_id")
	Customer customer;
	
	@OneToMany
	List<Transaction> transaction;

	@OneToMany
	List<Beneficary> benificary;
	
	@JsonIgnore
	public List<Beneficary> getBenificary() {
		return benificary;
	}

	public void setBenificary(List<Beneficary> benificary) {
		this.benificary = benificary;
	}
	
	
	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public double getAccBal() {
		return accBal;
	}

	public void setAccBal(double accBal) {
		this.accBal = accBal;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@JsonIgnore
	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public int getAccPwd() {
		return accPwd;
	}

	public void setAccPwd(int accPwd) {
		this.accPwd = accPwd;
	}
	
	
	
	
}
