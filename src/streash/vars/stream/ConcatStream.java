package streash.vars.stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class ConcatStream implements StreamVar{
	private StreamVar[] ss;
	private int index = 0;
	
	private ConcatStream(StreamVar s1, StreamVar s2) {
		this.ss = new StreamVar[2];
		ss[0] = s1.duplicate();
		ss[1] = s1.duplicate();
	}
	
	public static StreamVar getVar(StreamVar s1, StreamVar s2) {
		if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
			return new ConcatNumberStream(s1, s2);
		if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
			return new ConcatStringStream(s1, s2);
		return null;
	}
	@Override
	public StreamVar duplicate() {
		return new ConcatStream(ss[0].duplicate(), ss[1].duplicate());
	}
	
	@Override
	public boolean hasNext() {
		if(ss[index].hasNext())
			return true;
		if (index == 1)
			return false;
		index++;
		return ss[index].hasNext();
	}
	
	@Override
	public Value next() {
		if (!hasNext())
			throw new IllegalStateException("No more element in the Stream");
		return ss[index].next();
	}
	
	@Override
	public String getConsoleString() {
		return ss[0].getConsoleString()+" concatened with "+ss[1].getConsoleString();
	}
	
	@Override
	public long len() {
		return ss[0].duplicate().len() + ss[1].duplicate().len();
	}
	
	@Override
	public long print() {
		return ss[0].duplicate().print() + ss[1].duplicate().print();
	}
	
	public static class ConcatNumberStream extends ConcatStream implements NumberStreamVar {
		public ConcatNumberStream(StreamVar s1, StreamVar s2) {
			super(s1, s2);
		}
	}
	public static class ConcatStringStream extends ConcatStream implements NumberStreamVar {
		public ConcatStringStream(StreamVar s1, StreamVar s2) {
			super(s1, s2);
		}
	}
}
