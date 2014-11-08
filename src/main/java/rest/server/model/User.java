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
@Entity
@Table(name = "users")
@SequenceGenerator(name = "user_seq", sequenceName = "user_seq")
public class User implements Serializable {
	private static final long serialVersionUID = -417647809423596323L;
	private long id;
	private String username;
	private String password;
	private String firstName;
	private String lastname;
	private String email;
	private boolean active;
	private boolean deletable;
	private Role role;
	private Date created;

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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", firstName=" + firstName + ", lastname="
				+ lastname + ", email=" + email + ", active=" + active
				+ ", deletable=" + deletable + ", created=" + created + "]";
	}

}
