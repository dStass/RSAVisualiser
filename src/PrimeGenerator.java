import java.math.BigInteger;
import java.util.Random;

public class PrimeGenerator {
	static BigInteger ONE = BigInteger.ONE;
	static BigInteger TWO = new BigInteger("2");
	
	
	/**
	 * 
	 * @param digits > 0
	 * @return a highly probable prime number with given digits 
	 */
	static BigInteger generate(int digits) {
		BigInteger generatedNumber = MathFunctions.randomOddBigInteger(digits);
		while (!isProbablePrime(generatedNumber)) { // while we don't have a prime, add 2 to generated number
			generatedNumber = generatedNumber.add(TWO);
			if (generatedNumber.toString().length() > digits) generatedNumber = MathFunctions.randomOddBigInteger(digits);
		}
		return generatedNumber;
	}

	
	
	// return whether a number is a probable prime based on miller-rabin test
	private static boolean isProbablePrime(BigInteger n) {
		int kTests = 100;
		for (int i = 0; i < kTests; i++) {
			if (applyMillerRabin(n) == false) return false;
		}
		return true;
	}
	
	// inspired: https://www.youtube.com/watch?v=qdylJqXCDGs
	private static boolean applyMillerRabin(BigInteger n) {
		int lastDigit = MathFunctions.getLastDigit(n);
		if (lastDigit % 2 == 0 && lastDigit != 2) { // return false if n even
			return false;
		}
		
		// Step 1: find m such that n-1 = m*2^k
		BigInteger m = new BigInteger(n.toString());
		m = m.subtract(ONE);
		while (true) {
			if (MathFunctions.getLastDigit(m) % 2 == 0) { // divisible by 2
				m = m.divide(TWO);
			} else break;
		}
		
		
		// Step 2: pick a s.t.: 1 < a < n-1
		BigInteger a;
		do {
			a = new BigInteger(n.bitLength(), new Random());
		} while (a.compareTo(n) >= 0);
		
		
		// Step 3: Compute b_0 = a^m (mod n), b_i = (b_(i-1))^2
		int iMAX = 150;
		BigInteger b_i = null;
		BigInteger N_MINUS_ONE = n.subtract(BigInteger.ONE);
		for (int i = 0; i < iMAX; i++) {
			if (i == 0) {
				b_i = MathFunctions.raiseNumToExponentModulo(a, m, n);
				if (b_i.compareTo(ONE) == 0 || b_i.compareTo(N_MINUS_ONE) == 0) return true; // is probably prime
			} else { // for i = 1, 2,..
				if (b_i.compareTo(ONE) == 0) return false; // composite for sure
				if (b_i.compareTo(N_MINUS_ONE) == 0) return true; // probably prime
			}
			b_i = MathFunctions.raiseNumToExponentModulo(b_i, TWO, n); // b_i = (b_(i-1))^2
		}
		return false;
	}
	
}
