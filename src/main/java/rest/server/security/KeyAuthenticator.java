package rest.server.security;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author eric.olaveson
 *
 */
public class KeyAuthenticator {
	
	private final String SUPER_SECRET_SALT = "saltysecret";
	private String saltedKey;
	private String hashCode;
	private String hashCodeSolution;
	
	/**
	 * Construct a key authenticator by giving input key and actual
	 * key to authenticate with. Here the hash code is calculated
	 * and stored for authentication.
	 * @param keyInput
	 * @param queriedHashCode
	 */
	public KeyAuthenticator(String keyInput, String queriedHashCode) {
			
		this.saltedKey = keyInput + SUPER_SECRET_SALT;
		this.hashCode = hash(saltedKey);
		this.hashCodeSolution = queriedHashCode;
	}
	
	/**
	 * This will hash a plain text string using SHA-256 provided by google
	 * @param plainText
	 * @return
	 */
	private String hash(String plainText) {	
		
		HashFunction derp = Hashing.sha256();
		HashCode hash = derp.newHasher().putString(plainText, Charsets.UTF_8).hash();
		
		return hash.toString();
	}
	
	/**
	 * Compares the hash code output from the input key with
	 * the actual hash code stored for that specific user.
	 * @return
	 */
	public boolean verifyKey() {
		return hashCode.equals(hashCodeSolution);
	}
	
	/**
	 * Returns the hash code created by the input key
	 * @return
	 */
	public String getHashCode() {
		return hashCode;
	}
}
