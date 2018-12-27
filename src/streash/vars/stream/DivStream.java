package streash.vars.stream;

import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.Number;

public class DivStream implements NumberStreamVar{
	private StreamVar s1;
	private StreamVar s2;
	
	public DivStream(StreamVar s1, StreamVar s2) {
		this.s1 = s1.duplicate();
		this.s2 = s2.duplicate();
	}
	
	@Override
	public StreamVar duplicate() {
		return new DivStream(s1, s2);
	}
	
	@Override
	public String getConsoleString() {
		return "Division of "+s1.getConsoleString()+" and "+s2.getConsoleString();
	}
	
	@Override
	public boolean hasNext() {
		return (s1.hasNext() || s2.hasNext());
	}
	
	@Override
	public Value next() {
		if (s1.hasNext() && s2.hasNext())
			return ((Number) s1.next()).div((Number) s2.next());
		if (s1.hasNext())
			return s1.next();
		if (s2.hasNext())
			return (new Number(1)).div((Number) s2.next());
		throw new IllegalStateException("No such element");
	}
	
	@Override
	public long len() {
		long l1, l2;
		l1 = s1.len();
		l2 = s2.len();
		if (l1 > l2)
			return l1;
		return l2;
	}
}
