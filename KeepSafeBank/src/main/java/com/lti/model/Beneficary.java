package com.lti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="tbl_Beneficaryyy")
public class Beneficary {
	@Id
	@SequenceGenerator(name="Benif_seq",initialValue=400,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="benif_seq")
	int benifId;
	String bName;

	String bnickName;
	
	@ManyToOne
	Customer customer;
	
	@ManyToOne
	Account account;
	
	public String getBnickName() {
		return bnickName;
	}

	public void setBnickName(String bnickName) {
		this.bnickName = bnickName;
	}


	@JsonIgnore
	public Account getAccount() {
		return account;
	}

	
	public void setAccount(Account account) {
		this.account = account;
	}

	public int getBenifId() {
		return benifId;
	}

	public void setBenifId(int benifId) {
		this.benifId = benifId;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	@JsonIgnore
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
