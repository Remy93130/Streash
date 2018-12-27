package streash.vars;

public interface Value extends Expression, Comparable<Object>{
	String getConsoleString();
	int compareTo(Object arg0);
	String getType();
}
