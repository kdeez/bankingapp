package rest.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "transaction")
@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_seq")
public class Transaction implements Serializable{
	private static final long serialVersionUID = 3453088715384202617L;
	private long id;
	private long accountId;
	private int transactionType;
	private double amount;
	private double balance;
	private String description;
	private Date dateTime;
	
	public Transaction() 
	{
		super();
	}
	
	
	public Transaction(long accountId, int transactionType, double amount, String description) 
	{
		super();
		this.accountId = accountId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
	}


	public enum Type
	{
		DEBIT,
		CREDIT
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "transaction_seq")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "accountId")
	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Column(name = "transactionType")
	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "amount")
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "balance")
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "dateTime")
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountId=" + accountId
				+ ", transactionType=" + transactionType + ", amount=" + amount
				+ ", balance=" + balance + ", description=" + description
				+ ", dateTime=" + dateTime + "]";
	}
	
	
	
}
