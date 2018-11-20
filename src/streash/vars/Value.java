package streash.vars;

public interface Value extends Expression, Comparable<Object>{
	String getConsoleString();
	String getType();
	int compareTo(Object arg0);
}
