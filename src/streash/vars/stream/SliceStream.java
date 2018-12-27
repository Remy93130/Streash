package streash.vars.stream;

import java.util.ArrayList;

import streash.vars.StreamVar;
import streash.vars.Value;

public abstract class SliceStream implements StreamVar{
	private final long a;
	private final long b;
	private final boolean reversed;
	private final StreamVar s;
	private ArrayList<Value> slice;
	private long index;
	private long len;
	
	private SliceStream(StreamVar s, long a, long b) {
		if (a > b) { this.b = a; this.a = b; reversed = true; }
		else       { this.b = b; this.a = a; reversed = false; }

		this.s = s.duplicate();
		
		this.index = 0;
		this.len = 0;
		this.slice = new ArrayList<Value>();
		
		if (a < 0 || b < 0)
			throw new IllegalArgumentException("Illegal bounds for slice()");
		
		for (int i = 0; i < this.a; i++) {
			if (s.hasNext())
				s.next();
			else
				break;
		}
		
		for (int i = 0; i < this.b - this.a + 1; i++) {
			if (!(s.hasNext()))
				break;
			if (reversed)
				slice.add(0, s.next());
			else
				slice.add(s.next());
			len++;
		}
	}
	
	public static StreamVar getVar(StreamVar s, long a, long b) {
		if (s instanceof NumberStreamVar)
			return new SliceNumberStream(s, a, b);
		if (s instanceof StringStreamVar)
			return new SliceStringStream(s, a, b);
		return null;
	}
	
	@Override
	public StreamVar duplicate() {
		if (reversed)
			return getVar(s.duplicate(), b, a);
		return getVar(s.duplicate(), a, b);
	}

	@Override
	public boolean hasNext() {
		return (index < len);
	}
	
	@Override
	public Value next() {
		Value to = slice.get((int) index);
		index++;
		return to;
	}
	
	@Override
	public String getConsoleString() {
		return "Slice of "+s.getConsoleString()+" from the "+a+"th to the "+b+"th element";
	}

	public static class SliceNumberStream extends SliceStream implements NumberStreamVar {
		public SliceNumberStream(StreamVar s, long a, long b) {
			super(s, a, b);
		}
	}
	public static class SliceStringStream extends SliceStream implements StringStreamVar {
		public SliceStringStream(StreamVar s, long a, long b) {
			super(s, a, b);
		}
	}
}
