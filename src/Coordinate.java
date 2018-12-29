import java.math.BigInteger;

public class Coordinate {
	private BigInteger x, y;
	public Coordinate() {
		x = BigInteger.ZERO;
		y = BigInteger.ZERO;
	}
	
	public void setX(BigInteger n) {
		x = n;
	}
	public void setY(BigInteger n) {
		y = n;
	}
	public BigInteger getX() {
		return x;
	}
	public BigInteger getY() {
		return y;
	}
}
