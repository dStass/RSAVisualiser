import java.util.HashMap;

public class MathFunctions {
	public final static int TOTAL_ASCII_DIGITS = 128;
	
	
	
	
	
	public static long raiseNumToExponentModulo(long x, long n, long m) {
		return raiseNumToExponentModulo(x, n, m, false);
		
	}
	
	
	// return x^n (mod m)
	// TODO optimisations, fast ways of raising powers:
	// keep raising until we reach x^k = 1 mod m, then factor exponent out 
	// maybe recursive?
	public static long raiseNumToExponentModulo(long x, long n, long m, boolean optimise) {
		long[] calculations;
		if (optimise) calculations = new long[TOTAL_ASCII_DIGITS];
		else calculations = new long[(int) n]; // optimise: keep arraylist, store what we have to store
		
		int cycles = 0;
		long ans = x;
		long xMod = x % m;
		ans %= m;
		calculations[0] = ans;
    	for (int i = 1; i < n; i++) {
    		cycles++;
    		ans *= xMod; 
    		ans %= m;
    		ans += (ans < 0 ? m : 0);
    		if (optimise) {
    			 if (ans < TOTAL_ASCII_DIGITS) calculations[(int) ans] = cycles;
    		} else calculations[cycles] = ans;
    		if (ans == 1) {
    			if (optimise) {
    				for (int j = 1; j < TOTAL_ASCII_DIGITS; j++) {
    					int storedLocation = (int) (n % (cycles + 1));
        				if ((storedLocation - 1) == calculations[j]) return j; 
    				}
    			} else {
    				int storedLocation = (int) (n % (cycles+1));
        			return calculations[storedLocation-1];
    			}
    		}
    	}
		return ans;
	}
	
	
	
	// solution from https://discuss.codechef.com/questions/1440/algorithm-to-find-inverse-modulo-m
	public static long getInverseModulo(long a, long m) { // find inverse to a (modulo m)
		Coordinate c = new Coordinate();
		applyExtendedEuclidean(a,m,c);
		if (c.getX() < 0) c.setX(c.getX() + m);
		return c.getX();
		
	}
	
	
	public static void applyExtendedEuclidean(long a, long b, Coordinate c) {
		if (a % b == 0) {
			c.setX(0);
			c.setY(1);
			return;
		}
		applyExtendedEuclidean(b, a%b, c);
		long temp = c.getX();
		c.setX(c.getY());
		c.setY(temp - c.getY() * (a/b));
	}
}
