import java.math.BigInteger;

public class MathFunctions {
	public final static int TOTAL_ASCII_DIGITS = 128;

	
	public static BigInteger raiseNumToExponentModulo(BigInteger x, BigInteger n, BigInteger m) {
		BigInteger zero = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		BigInteger two = new BigInteger("2");
		
		if (n.compareTo(zero) == 0) return one;
		if (n.compareTo(one) == 0) return x; // x already modded by m
		BigInteger xMod = x.mod(m);
		BigInteger xMod2 = xMod.multiply(xMod);
		xMod2 = xMod2.mod(m);
		if (n.mod(two).compareTo(zero) == 0) {
			return raiseNumToExponentModulo(xMod2, n.divide(two), m);
		} else {
			BigInteger pow = (n.subtract(one)).divide(two);
			return raiseNumToExponentModulo(xMod2, pow, m).multiply(xMod).mod(m);
		}
	}
		
	
	// solution from https://discuss.codechef.com/questions/1440/algorithm-to-find-inverse-modulo-m
	public static BigInteger getInverseModulo(BigInteger a, BigInteger m) { // find inverse to a (modulo m)
		Coordinate c = new Coordinate();
		applyExtendedEuclidean(a,m,c);
		if (c.getX().compareTo(BigInteger.ZERO) == -1) c.setX(c.getX().add(m));
		return c.getX();
		
	}
	
	
	public static void applyExtendedEuclidean(BigInteger a, BigInteger b, Coordinate c) {
		if (a.mod(b).compareTo(BigInteger.ZERO) == 0) {
			c.setX(BigInteger.ZERO);
			c.setY(BigInteger.ONE);
			return;
		}
		applyExtendedEuclidean(b, a.mod(b), c);
		BigInteger temp = c.getX();
		c.setX(c.getY());
		c.setY(temp.subtract(c.getY().multiply((a.divide(b)))));
	}
	
	
	// returns last digit of a BigInteger
	public static int getLastDigit(BigInteger n) {
		String numberStr = n.toString();
		char lastDigitChar = numberStr.charAt(numberStr.length()-1);
		int lastDigit = Integer.parseInt(new String() + lastDigitChar);
		return lastDigit;
	}
	
	
	
	public static BigInteger randomOddBigInteger(int digits) {
		BigInteger toReturn;
		String toReturnStr = "";
		for (int i = 0; i < digits; i++) {
			int randomNum = (int) (Math.random()*10);
			if (i == 0) {
				while (randomNum == 0) { // make sure first number is not 0
					randomNum = (int) (Math.random()*10);
					if (randomNum == 10) randomNum--;
				}
			}
			if (i == digits - 1) { // on last number, generate such that it is odd
				if (randomNum % 2 == 0) randomNum++;
			}
			
			toReturnStr += (char) ('0'+randomNum);
		}
		toReturn = new BigInteger(toReturnStr);
		return toReturn;
	}
	
}
