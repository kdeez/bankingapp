package rest.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rest.server.controller.UserResource;

/**
 * 
 * @author roger.hagen
 * 
 * This is a Plain Old Java Object (POJO) that holds information to be stored or sent.
 * 
 * @Entity and @Table map this Java class to a database table.  Notice how each Java
 * variable includes an @Column annotation that maps itself to a table column.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq")
public class User implements Serializable {
	private static final long serialVersionUID = -417647809423596323L;
	private long id;
	private Role role;
	private String username;
	private String password;
	private boolean active;
	private boolean deletable;
	private String firstName;
	private String lastname;
	private String street;
	private String city;
	private String state;
	private Integer zipCode;
	private String email;
	private String phone;
	private Date created;
	private int failedAttempts;
	private long lockedTime;
	
	private Logger logger = LoggerFactory.getLogger(UserResource.class);

	public User() 
	{
		super();
	}
	
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * Unique primary key for this class.
	 * @return
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "roleId")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastName")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Column(name = "street")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zipCode")
	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "active")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column(name = "deletable")
	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	@Column(name = "created")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	
	@Column(name = "failedAttempts")
	public int getFailedAttempts() {
		return failedAttempts;
	}
	
	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	
	@Column(name = "lockedTime")
	public long getLockedTime() {
		return lockedTime;
	}
	
	public void setLockedTime(long lockedTime) {
		this.lockedTime = lockedTime;
	}
	
	@Transient
	public boolean isLocked(long timeStamp)
	{
		
		if (lockedTime == 0)
			return false;
		
		logger.info("Time now    : "+timeStamp);
		logger.info("Locked Time : "+lockedTime);
		logger.info("Difference  : "+(timeStamp - lockedTime));
		
		if ((timeStamp - lockedTime) < 60000)
			return true;
		
		this.unlockAccount();
		
		return false;
	}
	
	@Transient
	public void setFailedAttempt()
	{	
		if (failedAttempts < 2) {
			failedAttempts++;
		}
		else if (failedAttempts == 2) {
			lockedTime = System.currentTimeMillis();
		}
	}

	@Transient
	public void unlockAccount()
	{
		failedAttempts = 0;
		lockedTime     = 0;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", username=" + username
				+ ", password=" + password + ", active=" + active
				+ ", deletable=" + deletable + ", firstName=" + firstName
				+ ", lastname=" + lastname + ", street=" + street + ", city="
				+ city + ", state=" + state + ", zipCode=" + zipCode
				+ ", email=" + email + ", phone=" + phone + ", created="
				+ created + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (deletable ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (active != other.active)
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (deletable != other.deletable)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}	
	
	
	
}
