package streash.vars.stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import streash.vars.StreamVar;
import streash.vars.Value;

public class IntersectionStream implements StreamVar{
	private StreamVar s1;
	private StreamVar s2;
	private Iterator<Value> it;
	
	private IntersectionStream(StreamVar s1, StreamVar s2) {
		this.s1 = s1.duplicate();
		this.s2 = s2.duplicate();
		this.it = initIterator();
	}
	
	public static StreamVar getVar(StreamVar s1, StreamVar s2) {
		if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
			return new IntersectionNumberStream(s1, s2);
		if (s1 instanceof StringStreamVar && s2 instanceof StringStreamVar)
			return new IntersectionStringStream(s1, s2);
		throw new IllegalStateException("Cannot call inter on Streams of differents generics");
	}
	
	private Iterator<Value> initIterator() {
		List<Value> l1 = s1.collect();
		List<Value> l2 = s2.collect();
		List<Value> to = new ArrayList<Value>();
		for (Value v : l1) {
			if (l2.contains(v))
				to.add(v);
		}
		return to.iterator();
	}
	
	@Override
	public StreamVar duplicate() {
		return new IntersectionStream(s1, s2);
	}
	
	@Override
	public String getConsoleString() {
		return "Intersection of "+s1.getConsoleString()+" and "+s2.getConsoleString();
	}
	
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}
	
	@Override
	public Value next() {
		return it.next();
	}
	
	private static class IntersectionNumberStream extends IntersectionStream implements NumberStreamVar{
		public IntersectionNumberStream(StreamVar s1, StreamVar s2) {
			super(s1, s2);
		}
	}
	private static class IntersectionStringStream extends IntersectionStream implements NumberStreamVar{
		public IntersectionStringStream(StreamVar s1, StreamVar s2) {
			super(s1, s2);
		}
	}
}
