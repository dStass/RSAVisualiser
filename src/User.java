import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class User {
	// fields 
	// TODO: generate primes
	final BigInteger PRIME0 = new BigInteger("203956878356401977405765866929034577280193993314348263094772646453283062722701277632936616063144088173312372882677123879538709400158306567338328279154499698366071906766440037074217117805690872792848149112022286332144876183376326512083574821647933992961249917319836219304274280243803104015000563790123"),
					 PRIME1 = new BigInteger("290245329165570025116016487217740287508837913295571609463914348778319654489118435855243301969001872061575755804802874062021927719647357060447135321577028929269578574760547268310055056867386875959045119093967972205124270441648450825188877095173754196346551952542599226295413057787340278528252358809329"); // TODO use BigInteger to store even larger primes 7927 8821 | 27449 8821
	int id;
	private String name;
	private BigInteger privateKey;
	public BigInteger publicKey;
	public BigInteger encryptionModulo;
	private ArrayList<String> encryptedMessages; 	// TODO have a format encrypted String, first (8?) bits represent length of each "block" = 1 character, 
												 	// followed by uniform blocks of encrypted chars (-> longs)
													// ie 0000000819923405011293040019293000203949 = 8 char-long encryptions, 4 encrypted characters
	private ArrayList<String> decryptedMessages;
	
	public User(int id) {
		this.id = id;
		assignKeys();
		encryptedMessages = new ArrayList<String>();
	}
	 
	public void populateData(String name) {
		this.name = name;
	}
	
	public void assignKeys() {
		// TODO choose 2 random primes from list of primes
		BigInteger p = PRIME0, q = PRIME1;
		
		BigInteger n = p.multiply(q);
		BigInteger phiOfN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));//(p-1)*(q-1);
		
		// choose e from {1..phiOfN} such that gcd(e,phiOfN)=1 (ie e and phiOfN are co-prime)
		// randomly try and find an e in (1,phiOfN)
		
		BigInteger e = getRandomE(phiOfN);
		BigInteger d = MathFunctions.getInverseModulo(e, phiOfN);
		if (d == e) {
			assignKeys(); // rerun if d == e
			return;
		}
		privateKey = d;
		publicKey = e;
		encryptionModulo = n;
		
		//System.out.println(e + " * " + d + " = 1 (modphi= "+phiOfN+"), n="+n);
		System.out.println("p(" + p.toString().length() + "), q(" + q.toString().length() + ")");
		System.out.println("E(public) = " + publicKey + "\nD(private) = " + privateKey + "\n(mod " + encryptionModulo+")\n\n");

	}

	
	
	private BigInteger getRandomE(BigInteger upperLimit) {
		// keep trying random vals until we find e such that gcd(e,phiOfN) = 1
		while (true) {
			
		//	BigInteger random = (phiOfN.multiply(BigInteger.valueOf((long) Math.random())))  ; //(long) Math.floor(Math.random() * phiOfN);
			//BigInteger rand = new BigInteger(upperLimit.bi);
//			BigInteger gcd = getGCD(random, phiOfN);
//			if (gcd.compareTo(BigInteger.ONE) == 0) return random;	
//			System.out.println("phi=" + phiOfN + ", ran=" + random + ", gcd=" + gcd);
			
			Random rand = new Random();
			int maxBitLength = upperLimit.bitLength();
			BigInteger random;
			random = new BigInteger(maxBitLength, rand);
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
	
	public void receiveEncryptedMessage(long[] encryptedMessage) {
		//encryptedMessages.add(encryptedMessage);
	}

	public void receiveEncryptedString(String encryptedString) {
		// TODO 
		encryptedMessages.add(encryptedString);
		String decryptedString = decryptString(encryptedString);
		System.out.println("decrypted=" + decryptedString);
		// hello
		
	}

	private String decryptString(String encryptedString) {
		int blockLength = Integer.parseInt(encryptedString.substring(0,8));
		String decryptedString = "";
		for (int i = 8; i < encryptedString.length(); i+=blockLength) {
			String blockStr = encryptedString.substring(i, i + blockLength);
			System.out.print("block = " + blockStr);
			BigInteger parsedBlock = new BigInteger(blockStr);
			BigInteger decryptedValue = MathFunctions.raiseNumToExponentModuloBig(parsedBlock, privateKey, encryptionModulo);
			System.out.println(" --[^privKey]--> " + decryptedValue + " (mod " + encryptionModulo+")");
			char decryptedChar = (char) decryptedValue.longValue();
			decryptedString += decryptedChar;
			
			// raise inside the array to decrypt private key and store in another array 
			
		}
		
		return decryptedString;
	}
}
