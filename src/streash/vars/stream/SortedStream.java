package streash.vars.stream;

import java.util.ArrayList;
import java.util.Iterator;

import streash.vars.StreamVar;
import streash.vars.Value;

public class SortedStream implements StreamVar{
	private StreamVar s;
	private Iterator<Value> it;
	
	private SortedStream(StreamVar s) {
		this.s = s.duplicate();
		ArrayList<Value> list = new ArrayList<Value>();
		while(this.s.hasNext())
			list.add(this.s.next());
		list.sort(Value::compareTo);
		it = list.iterator();
	}
	
	public static StreamVar getVar(StreamVar s) {
		if (s instanceof NumberStreamVar)
			return new SortedNumberStream(s);
		if (s instanceof StringStreamVar)
			return new SortedStringStream(s);
		return null;
	}
	
	@Override
	public StreamVar duplicate() {
		return new SortedStream(s.duplicate());
	}
	
	@Override
	public String getConsoleString() {
		return "Sorted stream of "+s.getConsoleString();
	}
	
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}
	
	@Override
	public Value next() {
		return it.next();
	}
	
	@Override
	public long len() {
		return s.len();
	}
	
	static class SortedNumberStream extends SortedStream implements NumberStreamVar{
		public SortedNumberStream(StreamVar s) {
			super(s);
		}
	}
	static class SortedStringStream extends SortedStream implements NumberStreamVar{
		public SortedStringStream(StreamVar s) {
			super(s);
		}
	}
}
