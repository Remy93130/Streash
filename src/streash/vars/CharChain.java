package streash.vars;

import java.util.Scanner;

import org.json.JSONObject;

public class CharChain implements Primitive{
	private String value;
	
	public CharChain(String value) {
		this.value = value;
	}
	
	@Override
	public String getConsoleString(){
		return "\""+value+"\"";
	}
	
	@Override
	public String getType() {
		return "String";
	}
	
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof Number)
			return 1;
		CharChain c = (CharChain) arg0;
		return c.value.compareTo(value);
	}
	
	public String toString() {
		return value;
	}
	
	public CharChain concat(CharChain c) {
		return new CharChain(value.concat(c.value));
	}
	
	/**
	 * Multiply this object's value by the number indicated in the argument
	 * 
	 * @param i
	 *            the number of time the value is going to be multiplicated
	 * @return a new Charchain that represents this object's value multiplied i
	 *         times
	 */
	public CharChain time(long i) {
		if (i < 0)
			throw new IllegalArgumentException("Cannot time a String by a negative number");
		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < i; j++)
			builder.append(this.toString());

		return new CharChain(builder.toString());
	}
	
	/**
	 * Remove the Charchain passed in argument from the end of this object's value
	 * if the argument is a suffix of the value
	 * 
	 * @param c
	 *            the Charchain that will be removed
	 * @return a new Charchain that represent this object's value without the
	 *         argument at the end
	 */
	public CharChain subSuffix(CharChain c) {
		String string = c.value;
		if (!isSuffix(string, value))
			throw new IllegalArgumentException(string+" is not a suffix of "+value);
		return new CharChain(value.substring(0, value.length()-string.length()));
	}

	/**
	 * 
	 * @param sub
	 *            the suffix
	 * @param string
	 *            the Charchain
	 * @return true if the sub is a suffix of string. False otherwise.
	 */
	private boolean isSuffix(String sub, String string) { 
	    int n1 = sub.length(), n2 = string.length(); 
	    if (n1 > n2) 
	      return false; 
	    for (int i=0; i < n1; i++) 
	       if (sub.charAt(n1 - i - 1) != string.charAt(n2 - i - 1)) 
	           return false; 
	    return true; 
	} 
	
	/**
	 * Create a new CharChain with the content in the string and scanner
	 * 
	 * @param beginning
	 *            a string
	 * @param s
	 *            the scanner that represents the command written by the user (use "
	 *            to quit)
	 * @return a new Charchain that contains the string beginning followed by the
	 *         strings written by the user in the scanner
	 */
	public static CharChain parse(String beginning, Scanner s) {
		StringBuilder to = new StringBuilder();
		boolean end = false;
		String part = beginning.substring(1);
		while(true) {
			if (!(part.contains("\"")))
				to.append(part);
			else {
				for (char c : part.toCharArray()) {
					if (c == '"' && end) { end = false; to.append('"'); continue; }
					if (end && c != '"') { throw new IllegalStateException("Wrong declaration of string var"); }
					if (c == '"') { end = true; continue; }
					to.append(c);
				}
				if (end) return new CharChain(to.toString());
			}
			if (!s.hasNext())
				throw new IllegalStateException("Missing closure of string declaration");
			to.append(' ');
			part = s.next();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CharChain))
			return false;
		CharChain c = (CharChain) obj;
		return c.value.equals(value);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public JSONObject getJSONObject() {
		JSONObject o = new JSONObject();
		o.put("type", "String");
		o.put("Value", new JSONObject().put("Value", value));
		return o;
	}
	
	public static boolean hasJSONTag(String tag) {
		return tag.equals("String");
	}
	
	public static CharChain getValueFromJSON(JSONObject o) {
		return new CharChain(o.getJSONObject("Value").getString("Value"));
	}
}
