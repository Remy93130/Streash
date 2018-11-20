package streash.vars.stream;

import java.util.stream.Stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class ConcatStream implements StreamVar{
	private StreamVar s1;
	private StreamVar s2;
	
	public ConcatStream(StreamVar s1, StreamVar s2) {
		if (!(s1.getType().equals(s2.getType())))
			throw new IllegalStateException("Cannot concat two Streams of different generics");
		
		this.s1 = s1.duplicate();
		this.s2 = s2.duplicate();
	}
	
	@Override
	public String getType(){
		return s2.getType();
	}
	@Override
	public StreamVar duplicate() {
		return new ConcatStream(s1.duplicate(), s2.duplicate());
	}
	@Override
	public String getConsoleString() {
		return s1.getConsoleString()+" concatened with "+s2.getConsoleString();
	}
	@Override
	public Stream<Value> getStream() {
		return Stream.concat(s1.getStream(),  s2.getStream());
	}
	@Override
	public long print() {
		long size = 0;
		size += s1.print();
		size += s2.print();
		return size;
	}
	@Override
	public long len() {
		return s1.duplicate().len() + s2.duplicate().len();
	}
}
