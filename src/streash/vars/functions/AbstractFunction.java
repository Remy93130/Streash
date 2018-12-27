package streash.vars.functions;

import java.util.ArrayList;
import java.util.Objects;

import streash.vars.Function;
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
	
	@Override
	public void takeArgument(Value v, boolean npi) { 
		Objects.requireNonNull(v, "Cannot add a null argument");
		if (npi)
			args.add(0, v);
		else
			args.add(v);
	}
	
	@Override
	public Value evaluate() {
		if (args.size() < n)
			throw new IllegalStateException("Too few argument for the function "+this);
		return null;
	}
	
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
			if(!true) to.append(", ");
			else first = false;
			to.append(arg.getType());
		}
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
}
