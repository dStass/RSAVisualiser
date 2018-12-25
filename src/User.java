import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class User {
	public final int PRIME_DIGITS = 50; // RSA-1024
	public static final int HEADER_SIZE = 4;
	public int id;
	public String name;
	private BigInteger privateKey;
	public BigInteger publicKey;
	public BigInteger encryptionModulo;
	private ArrayList<String> encryptedMessages; 	// encrypted String, first 8 bits represent length of each "block" = 1 character,
												 	// followed by uniform blocks of encrypted chars (-> longs)
													// ie 0000000819923405011293040019293000203949 = 8 char-long encryptions, 4 encrypted characters
	//private ArrayList<String> decryptedMessages;

	public User(int id) {
		this.id = id;
		assignKeys();
		encryptedMessages = new ArrayList<String>();
	}

	public void populateData(String name) {
		this.name = name;
	}

	public void assignKeys() {
		
		// generate p and q prime numbers
		BigInteger p, q;
		
		p = PrimeGenerator.generate(PRIME_DIGITS);
		q = PrimeGenerator.generate(PRIME_DIGITS);
		while (q.compareTo(p) == 0) q = PrimeGenerator.generate(PRIME_DIGITS); // make sure q != p;
		
		
		
		BigInteger n = p.multiply(q);
		BigInteger phiOfN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));//(p-1)*(q-1);
		System.out.println("p(" + p.toString().length() + ") = " + p);
		System.out.println("q(" + q.toString().length() + ") = " + q);
		p = null; q = null;
		
		// choose e from {1..phiOfN} such that gcd(e,phiOfN)=1 (ie e and phiOfN are co-prime)
		// randomly try and find an e in (1,phiOfN)
		BigInteger e = getRandomE(phiOfN);
		BigInteger d = MathFunctions.getInverseModulo(e, phiOfN);
		if (d == e) {
			assignKeys(); // re-run if d == e
			return;
		}
		privateKey = d;
		publicKey = e;
		encryptionModulo = n;

		System.out.println("E(public) = " + publicKey + "\nD(private) = " + privateKey + "\n(mod " + encryptionModulo+")\n\n");

	}



	private BigInteger getRandomE(BigInteger upperLimit) {
		// keep trying random vals until we find e such that gcd(e,phiOfN) = 1
		while (true) {
			Random rand = new Random();
			int maxBitLength = upperLimit.bitLength();
			BigInteger random = new BigInteger(maxBitLength, rand);
			BigInteger gcd = getGCD(random, upperLimit);
			if (gcd.compareTo(BigInteger.ONE) == 0) return random;
		}

	}


	// https://stackoverflow.com/questions/4009198/java-get-greatest-common-divisor
	private BigInteger getGCD(BigInteger a, BigInteger b) {
		if (b.compareTo(BigInteger.ZERO) == 0) return a;
		return getGCD(b,a.mod(b));
	}





	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getEncryptionModulo() {
		return encryptionModulo;
	}

	public void receiveEncryptedString(String encryptedString) {
		encryptedMessages.add(encryptedString);
		String decryptedString = decryptString(encryptedString);
		System.out.println("decrypted=" + decryptedString);

	}

	private String decryptString(String encryptedString) {
		int blockLength = Integer.parseInt(encryptedString.substring(0, HEADER_SIZE));
		String decryptedString = "";
		for (int i = HEADER_SIZE; i < encryptedString.length(); i+=blockLength) {
			String blockStr = encryptedString.substring(i, i + blockLength);
			System.out.print("block = " + blockStr);
			BigInteger parsedBlock = new BigInteger(blockStr);
			BigInteger decryptedValue = MathFunctions.raiseNumToExponentModulo(parsedBlock, privateKey, encryptionModulo);
			System.out.println(" --[^privKey]--> " + decryptedValue + " (mod EncryptionModulo)");
			char decryptedChar = (char) decryptedValue.longValue();
			decryptedString += decryptedChar;

		}

		return decryptedString;
	}
}
