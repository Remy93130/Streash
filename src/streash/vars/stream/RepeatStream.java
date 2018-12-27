package streash.vars.stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class RepeatStream implements StreamVar{
	private float repeat;
	private StreamVar s;
	private int index = 0;
	private int point = 0;
	private int len;
	private StreamVar current;
	
	private RepeatStream(StreamVar s, float a) {
		if (a < 0)
			throw new IllegalArgumentException("Cannot use repeat() with a negative value");
		this.repeat = a;
		this.s = s.duplicate();
		this.len = (int) s.len();
		this.current = s.duplicate();
	}
	
	public static StreamVar getVar(StreamVar s, float a) {
		if (s instanceof NumberStreamVar)
			return new RepeatNumberStream(s, a);
		if (s instanceof StringStreamVar)
			return new RepeatStringStream(s, a);
		return null;
	}
 	
	@Override
	public StreamVar duplicate() {
		return new RepeatStream(s.duplicate(), repeat);
	}
	
	@Override
	public String getConsoleString() {
		return s.getConsoleString()+" repeated "+repeat+" times";
	}
	
	@Override
	public boolean hasNext() {
		if (index <= (int) repeat) {
			if (current.hasNext())
				return true;
			current = s.duplicate();
			index++;
			point = 0;
			return hasNext();
		}
		if (((float) point / len) >= repeat - (int) repeat)
			return false;
		if (current.hasNext())
			return true;
		return false;
	}
	
	@Override
	public Value next() {
		if (hasNext()) {
			point++;
			return current.next();
		}
		throw new IllegalStateException("No such element");
	}
	
	public static class RepeatNumberStream extends RepeatStream implements NumberStreamVar {
		public RepeatNumberStream(StreamVar s, float a) {
			super(s, a);
		}
	}
	public static class RepeatStringStream extends RepeatStream implements NumberStreamVar {
		public RepeatStringStream(StreamVar s, float a) {
			super(s, a);
		}
	}
	
	public static void main(String[] args) {
		StreamVar inf = new InfiniteIntegersStream(0, false);
		StreamVar slice = SliceStream.getVar(inf, 0, 9);
		StreamVar s = RepeatStream.getVar(slice, (float) 2.5);
		s.print();
	}
}
