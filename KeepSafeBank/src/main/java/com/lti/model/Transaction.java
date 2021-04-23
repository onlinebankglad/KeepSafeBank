package com.lti.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_transactionnn")
public class Transaction {
	@Id
	@SequenceGenerator(name="tr_seq",initialValue=450,allocationSize=2)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="tr_seq")
	int transId;
	double amount;
	String tType="debit";
	LocalDate tDate;
	String tmodes;
	
	@ManyToOne
	@JoinColumn(name="fromacc_no")
	Account account;
	
	
	int toAccNO;


	public int getTransId() {
		return transId;
	}


	public void setTransId(int transId) {
		this.transId = transId;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String gettType() {
		return tType;
	}


	public void settType(String tType) {
		this.tType = tType;
	}


	public LocalDate gettDate() {
		return tDate;
	}


	public void settDate(LocalDate tDate) {
		this.tDate = tDate;
	}


	

	public String getTmodes() {
		return tmodes;
	}


	public void setTmodes(String tmodes) {
		this.tmodes = tmodes;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public int getToAccNO() {
		return toAccNO;
	}


	public void setToAccNO(int toAccNO) {
		this.toAccNO = toAccNO;
	}
	
	
	
	
}
