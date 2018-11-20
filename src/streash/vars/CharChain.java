package streash.vars;

import java.util.Scanner;

public class CharChain implements Value{
	private String value;
	
	public CharChain(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
	public String getConsoleString(){
		return "\""+value+"\"";
	}
	
	@Override
	public String getType() {
		return "String";
	}
	
	public CharChain concat(CharChain c) {
		return new CharChain(value.concat(c.value));
	}
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof Number)
			return 1;
		CharChain c = (CharChain) arg0;
		return c.value.compareTo(value);
	}
	public CharChain time(long i) {
		if (i < 0)
			throw new IllegalArgumentException("Cannot time a String by a negative number");
		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < i; j++)
			builder.append(this.toString());

		return new CharChain(builder.toString());
	}
	

	public CharChain subSuffix(CharChain c) {
		String string = c.value;
		if (!isSuffix(string, value))
			throw new IllegalArgumentException(string+" is not a suffix of "+value);
		return new CharChain(value.substring(0, value.length()-string.length()));
	}

	private boolean isSuffix(String sub, String string) 
	{ 
	    int n1 = sub.length(), n2 = string.length(); 
	    if (n1 > n2) 
	      return false; 
	    for (int i=0; i < n1; i++) 
	       if (sub.charAt(n1 - i - 1) != string.charAt(n2 - i - 1)) 
	           return false; 
	    return true; 
	} 
	
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
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println(CharChain.parse("\"armand", s));
	}
}