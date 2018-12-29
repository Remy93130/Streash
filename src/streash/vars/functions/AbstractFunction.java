package streash.vars.functions;

import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import streash.vars.Function;
import streash.vars.StreamVar;
import streash.vars.Value;

public abstract class AbstractFunction implements Function{
	private final int n;
	private final ArrayList<Value> args;
	
	public AbstractFunction(int argNumber) {
		this.n = argNumber;
		this.args = new ArrayList<Value>();
	}
	
	@Override
	public int argNumber() { return n; }
	
	/**
	 * Add an argument in the function
	 * 
	 * @param v
	 *            the new argument
	 * @param npi
	 *            if the expression is in Reverse Polish Notation
	 */
	@Override
	public void takeArgument(Value v, boolean npi) { 
		Objects.requireNonNull(v, "Cannot add a null argument");
		if (npi)
			args.add(0, v);
		else
			args.add(v);
	}
	
	/**
	 * Check if there is enough argument for the function
	 */
	@Override
	public Value evaluate() {
		if (args.size() < n)
			throw new IllegalStateException("Too few argument for the function "+this);
		return null;
	}
	
	/**
	 * 
	 * @return the values of the function in a table
	 */
	public Value[] getArgs() {
		Value[] toReturn = new Value[n];
		for (int i = 0; i < toReturn.length; i++)
			toReturn[i] = args.get(i);
		return toReturn;
	}
	
	@SuppressWarnings("unused")
	public void illegalTypesException() {
		StringBuilder to = new StringBuilder();
		to.append("Unhandeled call ").append(getName()).append("(");
		boolean first = true;
		for(Value arg : args) {
			to.append(", ");
			to.append(arg.getType());
		}
		to.append(")");
		throw new IllegalArgumentException(to.toString());
	}
	
	abstract public String getName();
	
	@Override
	public String toString() {
		return getName()+" "+args;
	}
	@Override
	public void empty() {
		this.args.clear();	
	}
	
	@Override
	public StreamVar getStreamFromJSON(JSONObject json, boolean npi) {
		JSONArray array = json.getJSONArray("Args");
		for (int i = 0; i < n; i++)
			takeArgument(Value.getValueFromJSON(array.getJSONObject(i)), npi);
		Value ev = evaluate();
		if (!(ev instanceof StreamVar))
			throw new IllegalStateException();
		return (StreamVar) ev;
	}
}
