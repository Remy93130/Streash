package streash.vars.stream;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;

public class FiboStream implements NumberStreamVar{
	private final long first;
	private final long second;
	private Number current1;
	private Number current2;
	
	
	public FiboStream(long first, long second) {
		this.first = first;
		this.second = second;
		this.current1 = new Number(first);
		this.current1 = new Number(second);
	}
	
	@Override
	public String getConsoleString() {
		return "Fibonacci sequence with "+first+" and "+second+" as firsts terms";
	}
	
	@Override
	public StreamVar duplicate() {
		return new FiboStream(first, second);
	}
	
	@Override
	public boolean hasNext() {
		return true;
	}
	
	@Override
	public Value next() {
		Number to = current1.add(current2);
		current1 = current2;
		current2 = to;
		return to;
	}
}
