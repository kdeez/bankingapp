package rest.server.security;

import java.util.Comparator;


public class Lockout implements Comparator {

	private final double LOGGIN_TIME = 120000;
	private final double LOCK_TIME =  60000;
	private final int    MAX_ATTEMPTS = 3;
	
	private int    numAttempts;
	private double firstAttempt;
	private double startLockoutTime;
	private boolean locked;
	private String userName;
	
	public Lockout(double firstTimeStamp, String userName) {
		this.firstAttempt = firstTimeStamp;
		this.numAttempts = 1;
		this.userName = userName;
		this.locked = false;
	}
	
	// CALLED WHEN USER ENTERS WRONG PASSWORD
	public void incrementAttempt(double timeStamp) {
		
		if (lockoutIsOver(timeStamp)) {
			numAttempts = 1;
			locked = false;
			firstAttempt = timeStamp;
		}
		else {
			++numAttempts;
			
			if (numAttempts == 3) {
				locked = true;
				startLockoutTime = timeStamp;
			}
		}
	}
	
	// CALLED WHEN USER ENTERS CORRECT PASSWORD
	public boolean isLocked(double timeStamp) {
		
		if (lockoutIsOver(timeStamp)) {
			numAttempts = 0;
			locked = false;
		}
		
		return locked;
	}
	
	private boolean lockoutIsOver(double timeStamp) {
		
		return (timeStamp - startLockoutTime) > LOCK_TIME;
	}
	
	@Override
	public boolean equals(Object o) {
		Lockout lock = (Lockout)o;
		
		return this.userName.equals(lock.getUserName());
	}

	@Override
	public int compare(Object o1, Object o2) {
		Lockout lock1 = (Lockout)o1;
		Lockout lock2 = (Lockout)o2;
		
		return lock1.getUserName().compareTo(lock2.getUserName());
	}
	
	public boolean equals(String name) {
		return userName.equals(name);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public boolean getLocked() {
		return this.locked;
	}
}
