package streash.vars;

import org.json.JSONObject;

public interface Value extends Expression, Comparable<Object>{
	String getConsoleString();
	int compareTo(Object arg0);
	String getType();
	public static Value getValueFromJSON(JSONObject json) {
		Expression e = Expression.getValueFromJSON(json);
		if (e instanceof Function)
			throw new IllegalStateException();
		return (Value) e;
	}
}
