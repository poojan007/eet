package bt.gov.moh.eet.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncryptionUtil {
	
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 512;
  	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	
	public static Optional<String> generateSalt(final int length){
		SecureRandom RAND = new SecureRandom();
		 if (length < 1) {
		      System.err.println("error in generateSalt: length must be > 0");
		      return Optional.empty();
		 }

		 byte[] salt = new byte[length];
		 RAND.nextBytes(salt);

		 return Optional.of(Base64.getEncoder().encodeToString(salt));
	}
	
	public static Optional<String> hashPassword (String password, String salt) {
		char[] chars = password.toCharArray(); 
		byte[] bytes = salt.getBytes();
  
		PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);
		Arrays.fill(chars, Character.MIN_VALUE);
  
		try { 
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM); 
			byte[] securePassword = fac.generateSecret(spec).getEncoded(); 
			return Optional.of(Base64.getEncoder().encodeToString(securePassword));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			System.err.println("Exception encountered in hashPassword()"); 
			return Optional.empty(); 
		} finally { 
			spec.clearPassword(); 
		} 
	}
	
	  
	  public static void main(String[] args) { String password = "test"; String
	  salt = PasswordEncryptionUtil.generateSalt(512).get();
	  System.out.println("Salt:"+salt); String hashPassword =
	  PasswordEncryptionUtil.hashPassword(password, salt).get();
	  System.out.println("Hash:"+hashPassword);
	  
	  String enteredPassword = "test"; String enteredHashPassword =
	  PasswordEncryptionUtil.hashPassword(enteredPassword, salt).get();
	  if(hashPassword.equals(enteredHashPassword)) System.out.println("TRUE"); else
	  System.out.println("FALSE"); }
	 
}
