import java.util.ArrayList;

public class User {
	// fields 
	final long PRIME0 = 547, PRIME1 = 433; // TODO use BigInteger to store even larger numbers
	int id;
	private String name;
	private long privateKey;
	public long publicKey;
	
	
	public User(int id) {
		this.id = id;
		assignKeys();
	}
	 
	public void populateData(String name) {
		this.name = name;
	}
	
	public void assignKeys() {
		// TODO choose 2 random primes from list of primes
		long p = PRIME0, q = PRIME1; // choose p and q
	
		long n = p*q;
		long phiOfN = (p-1)*(q-1);
		
		// choose e from {1..phiOfN} such that gcd(e,phiOfN)=1 (ie e and phiOfN are co-prime)
		// randomly try and find an e in (1,phiOfN)
		
		long e = getRandomE(phiOfN);
		long d = getInverse(e, phiOfN);
		if (d == e) assignKeys(); // rerun if d == e
		privateKey = d;
		publicKey = e;
		
		System.out.println(e + " * " + d + " = 1 (mod "+phiOfN+"), n="+n);

	}

	
	
	private long getRandomE(long phiOfN) {
		// keep trying random vals until we find e such that gcd = 1
		while (true) {
			
			long random = (long) Math.floor(Math.random() * phiOfN);
			long gcd = getGCD(random, phiOfN);
			if (gcd == 1) return random;	
			System.out.println("phi=" + phiOfN + ", ran=" + random + ", gcd=" + gcd);
		}

	}
	
	
	// https://stackoverflow.com/questions/4009198/java-get-greatest-common-divisor
	private long getGCD(long a, long b) {
		if (b==0) return a;
		return getGCD(b,a%b);
	}
	
	
	
	// solution from https://discuss.codechef.com/questions/1440/algorithm-to-find-inverse-modulo-m
	private long getInverse(long a, long m) { // find inverse to a (modulo m)
		Coordinate c = new Coordinate();
		applyExtendedEuclidean(a,m,c);
		if (c.getX() < 0) c.setX(c.getX() + m);
		return c.getX();
		
	}
	
	
	private void applyExtendedEuclidean(long a, long b, Coordinate c) {
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
