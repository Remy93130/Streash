package streash.vars.stream;

import java.util.stream.Stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class RepeatStream implements StreamVar{
	private float repeat;
	private StreamVar s;
	
	public RepeatStream(StreamVar s, float a) {
		if (a < 0)
			throw new IllegalArgumentException("Cannot use repeat() with a negative value");
		this.repeat = a;
		this.s = s.duplicate();
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
	public long print() {
		float end;
		int time;
		if ((int) repeat == repeat)
			end = 0;
		else
			end = repeat - ((int) repeat);
		time = (int) repeat;
		
		StreamVar backup;
		long size = 0;
		long unit_size = 0;
		for (int i = 0; i < time; i++) {
			backup = s.duplicate();
			size += s.getStream().mapToLong(x -> {System.out.println(x); return 1L;}).sum();
			if (unit_size == 0)
				unit_size = size;
			s = backup;
		}
		size += s.getStream().limit((long) (unit_size * end)).mapToLong(x -> {System.out.println(x); return 1L;}).sum();
		return size;
	}
	
	@Override
	public Stream<Value> getStream() {
		float end;
		int time;
		if ((int) repeat == repeat)
			end = 0;
		else
			end = repeat - ((int) repeat);
		time = (int) repeat;
		
		StreamVar backup;
		Stream<Value> to = Stream.empty();
		
		backup = s.duplicate();
		long size = s.getStream().mapToLong(x -> 1L).sum();
		s = backup;
		
		for (int i = 0; i < time; i++) {
			backup = s.duplicate();
			to = Stream.concat(to, s.getStream());
			s = backup;
		}
		to = Stream.concat(to, s.getStream().limit((long) (size * end)));
		return to;
	}
}
