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

@Entity
@Table(name = "account")
@SequenceGenerator(name = "account_seq", sequenceName = "account_seq")
public class Account implements Serializable {
	private static final long serialVersionUID = 2395215592820350575L;
	private long accountNumber;
	private String description;
	private long userId;
	private int balance;
	private int accountType;
	private Date created;

	public Account() {
		super();
	}
	
	public enum AccountType{
		CHECKING("Checking"),
		SAVINGS("Savings");
		
		private String description;
		
		AccountType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
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
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
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
	public AccountType getType()
	{
		return AccountType.values()[accountType];
	}
	
	public void setType(AccountType type)
	{
		this.accountType = type.ordinal();
	}
	
}
