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
	
	private final static String SUPER_SECRET_SALT = "saltysecret";
	
	/**
	 * This will hash a plain text string using SHA-256 provided by google
	 * @param plainText
	 * @return
	 */
	private static String hash(String plainText) {	
		
		HashFunction derp = Hashing.sha256();
		HashCode hash = derp.newHasher().putString(plainText, Charsets.UTF_8).hash();
		
		return hash.toString();
	}
	
	/**
	 * Verifies if an input password matches the hash code
	 * of a queried hash code.
	 * @param keyInput
	 * @param queriedHashCode
	 * @return
	 */
	public static boolean verifyKey(String keyInput, String queriedHashCode) {
		String hashCode  = hash(keyInput + SUPER_SECRET_SALT);
		
		return hashCode.equals(queriedHashCode);
	}
	
	/**
	 * Compute hash code of a given string.
	 * @param text
	 * @return
	 */
	public static String getHashCode(String text) {
		return hash(text+SUPER_SECRET_SALT);
	}
}