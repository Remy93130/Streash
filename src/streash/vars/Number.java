package streash.vars;

import org.json.JSONObject;

public class Number implements Primitive {
	private long num;
	private long den;
	
	public Number(long num, long den) {
		this.num = num;
		this.den = den;
		if (num == 0)
			den = 1;
		else
			this.proper();
	}
	
	public Number(long value) {
		this.num = value;
		this.den = 1;
	}
	
	public String getConsoleString() {
		return this.toString();
	}
	
	@Override
	public String getType() {
		return "Number";
	}
	
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof CharChain)
			return -1;
		Number n = (Number) arg0;
		return Float.valueOf((float) num / den).compareTo(Float.valueOf((float) n.num/n.den));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Number))
			return false;
		Number n = (Number) obj;
		return n.num == num && n.den == den;
	}
	
	@Override
	public int hashCode() {
		return Float.valueOf(this.getFloatingValue()).hashCode();
	}
	
	@Override
	public String toString() {
		if (den == 1)
			return String.valueOf(num);
		if (den == -1)
			return String.valueOf(-num);
		return String.valueOf(num)+"/"+String.valueOf(den)+" ~= "+String.valueOf((float) num/den);
	}

	/**
	 * Return the value of the number if the number is an integer
	 * 
	 * @return this object's num
	 */
	public long getValue() {
		if (den != 1)
			throw new IllegalStateException("Cannot give value of floating Number");
		return num;
	}
	
	public float getFloatingValue() {
		return (float) num / den;
	}
	
	public static Number requireNonFloat(Number n, String message) {
		if(!n.isInteger())
			throw new IllegalArgumentException(message);
		return n;
	}
	
	/**
	 * Create a new number with the values given in the expr. If the expr contains
	 * more than 2 values, an exception is throw
	 * 
	 * @param expr
	 *            the expr that contains the values
	 * @return a new number with the values given in the expr
	 */
	public static Number parse(String expr) {
		String[] split = expr.split("/");
		if (split.length > 2)
			throw new IllegalArgumentException("Corrupted form of Number type");
		if (split.length == 1)
			return new Number(Integer.parseInt(expr));
		return new Number(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
	}
	
	/**
	 * 
	 * @return the fraction in a simplified form
	 */
	private Number proper() {
		if (den == num) {
			num = 1;
			den = 1;
			return this;
		}
		if (num == 1)
			return this;
		if (num == 0) {
			den = 1;
			return this;
		}
		long pgcd = pgcd(den, num);
		this.num = num/pgcd;
		this.den = den/pgcd;
		return this;
	}
	
	private long pgcd(long a, long b) {
		long c;
		if (a < b) { c = a; a = b; b = c; }

		while (a % b != 0) {
			c = a % b;
			a = b;
			b = c;
		}
		return b;
	}
	
	/**
	 * Calculate the sum of this object's values and the values of the number passed
	 * in argument
	 * 
	 * @param n
	 *            a number (in a fraction form)
	 * @return the result of the sum of the two Number
	 */
	public Number add(Number n) {
		if (n.equals(new Number(0)))
			return this;
		if (this.equals(new Number(0)))
			return n;
		
		return new Number(num*n.den + n.num*den, den*n.den).proper();
	}
	
	/**
	 * Calculate the difference of this object's values and the values of the number passed
	 * in argument
	 * 
	 * @param n
	 *            a number (in a fraction form)
	 * @return the result of the difference between the two Number
	 */
	public Number sub(Number n) {
		if (n.equals(new Number(0)))
			return this;
		if (this.equals(new Number(0)))
			return n.mul(new Number(-1));
		return new Number(num*n.den - n.num*den, den*n.den).proper();
	}
	
	/**
	 * Calculate the product of this object's values and the values of the number passed
	 * in argument
	 * 
	 * @param n
	 *            a number (in a fraction form)
	 * @return the result of the product of the two Number
	 */
	public Number mul(Number n) {
		return new Number(num*n.num, den*n.den).proper();
	}
	
	/**
	 * Calculate the quotient of this object's values and the values of the number passed
	 * in argument
	 * 
	 * @param n
	 *            a number (in a fraction form)
	 * @return the result of the quotient of the two Number
	 */
	public Number div(Number n) {
		if (n.equals(new Number(0)))
			throw new IllegalArgumentException("Cannot divide a Number by 0");
		return new Number(num*n.den, den*n.num).proper();
	}
	
	public boolean isInteger() {
		return den == 1;
	}
	
	public long getNum() {
		return num;
	}
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("type", "Number");
		JSONObject number = new JSONObject();
		number.put("Num", num);
		number.put("Den", den);
		o.put("Value", number);
		return o;
	}
	
	public static boolean hasJSONTag(String tag) {
		return tag.equals("Number");
	}
	
	public static Value getValueFromJSON(JSONObject o) {
		return new Number(o.getInt("Num"), o.getInt("Den"));
	}
}
