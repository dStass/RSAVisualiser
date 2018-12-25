import java.math.BigInteger;
import java.util.ArrayList;

public class Server {
	private ArrayList<User> users;
	
	public Server() {
		// setup server
		// setup 1 user (TODO many users)
		
		users = new ArrayList<User>();
		
		// 1 user setup;
		// users.add(new User(users.size()));
		User user0 = new User(0); // primes will automatically be generated for each user
		users.add(user0);
		
		
	}
	
	public void sendMessageToAllUsers(String m) {
		int messageLength = m.length();
		BigInteger[] encryptedMessage = new BigInteger[messageLength];
		for (int i = 0; i < messageLength; i++) encryptedMessage[i] = BigInteger.ZERO; // initialise
		for (User u : users) {
			int longestDigits = 0;
			for (int i = 0; i < messageLength; i++) {
				encryptedMessage[i] = encryptCharWithKey(m.charAt(i), u.getPublicKey(), u.getEncryptionModulo());
				int currDigits = encryptedMessage[i].toString().length();
				if (currDigits > longestDigits) longestDigits = currDigits;
			}
			System.out.println("headerLength = " + longestDigits);
			String encryptedString = produceEncryptedString(encryptedMessage, longestDigits);
			System.out.println("EnCrYpTeD sTrInG ===> ");
			for (int i = 0; i < encryptedString.length(); i++) {
				System.out.print(encryptedString.charAt(i));
				if ((i + 1 - User.HEADER_SIZE)%longestDigits == 0) System.out.println();
			}
			System.out.println();
			u.receiveEncryptedString(encryptedString);
		}
	}
	
	
	// return format [lengthEachBlock][blocks0][block1]..., lengthEachBlock = first 2 chars, or 3
	private String produceEncryptedString(BigInteger[] encryptedMessage, int padding) {
		String toReturn = String.format("%0" + User.HEADER_SIZE + "d", padding);
	
		for (int i = 0; i < encryptedMessage.length; i++) {
			toReturn += String.format("%0" + padding + "d", encryptedMessage[i]);
		}
		return toReturn;
	}

	private BigInteger encryptCharWithKey(char c, BigInteger e, BigInteger m) {
		return raiseCharToPowerModulo(c, e, m);
	}
	
	private BigInteger raiseCharToPowerModulo(char c, BigInteger n, BigInteger m) {
		int cInt = c;
		BigInteger cIntBig = new BigInteger(Integer.toString(cInt));
		System.out.print(c+ ": " + cInt);
		BigInteger toReturn = MathFunctions.raiseNumToExponentModulo(cIntBig, n, m);
		System.out.println(" --[^pubKey]--> " + toReturn);
		
		return toReturn;
	}
	
	
}
