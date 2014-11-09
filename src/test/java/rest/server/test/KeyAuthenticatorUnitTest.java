package rest.server.test;

import static org.junit.Assert.*;
import org.junit.Test;
import rest.server.security.KeyAuthenticator;

/**
 * @author eric.olaveson
 *
 */
public class KeyAuthenticatorUnitTest {

	private final String SALTED_HASHED_PASS = "69f16d0e0b12d534b1e480ba3442343577e72275535ff68f229f0d89d7b2294d";
	
	@Test
	public void SaltingHash() {
		
		KeyAuthenticator key = new KeyAuthenticator("password",SALTED_HASHED_PASS);
		
		assertTrue(!key.getHashCode().equals("password"));
		assertTrue(key.getHashCode().equals(SALTED_HASHED_PASS));
		assertTrue(key.verifyKey());
	}
}
