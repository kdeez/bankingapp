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
import javax.persistence.Transient;

import rest.server.resources.exceptions.TransactionException;

@Entity
@Table(name = "account")
@SequenceGenerator(name = "account_seq", sequenceName = "account_seq")
public class Account implements Serializable {
	private static final long serialVersionUID = 2395215592820350575L;
	private long accountNumber;
	private String description;
	private long userId;
	private double balance;
	private int accountType;
	private Date created;

	public Account() {
		super();
	}
	
	public enum Type{
		CHECKING,
		SAVINGS,
		CAPITOL;
	}

	/**
	 * Unique primary key for this class.
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "account_seq")
	@Column(name = "accountNumber")
	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "balance")
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	@Column(name = "accountType")
	public int getAccountType()
	{
		return accountType;
	}
	
	public void setAccountType(int type)
	{
		this.accountType = type;
	}
	
	@Transient
	public Type getType()
	{
		return Type.values()[accountType];
	}
	
	public void setType(Type type)
	{
		this.accountType = type.ordinal();
	}
	
	@Transient
	public void debit(double amount) throws TransactionException
	{
		double tmp = this.balance - amount;
		if(tmp < 0)
		{
			throw new TransactionException("Insufficient funds");
		}
		
		if(tmp > balance)
		{
			throw new TransactionException("Invalid operation");
		}
		
		this.balance -= amount;
	}
	
	@Transient
	public void credit(double amount) throws TransactionException
	{
		double tmp = this.balance + amount;
		if(tmp < balance)
		{
			throw new TransactionException("Invalid operation");
		}
		
		this.balance += amount;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", description="
				+ description + ", userId=" + userId + ", balance=" + balance
				+ ", accountType=" + accountType + ", created=" + created + "]";
	}
	
	
}
