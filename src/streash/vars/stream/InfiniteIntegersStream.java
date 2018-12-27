package streash.vars.stream;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;

public class InfiniteIntegersStream implements NumberStreamVar{
	private long from;
	private boolean reversed;
	private Number current;
	
	public InfiniteIntegersStream(long n, boolean reversed) {
		this.from = n;
		this.reversed = reversed;
		if (reversed)
			current = new Number(n + 1);
		else
			current = new Number(n - 1);
	}
	
	public boolean hasNext() {
		return true;
	}
	
	public Value next() {
		if (reversed)
			current = current.sub(new Number(1));
		else
			current = current.add(new Number(1));
		
		return current;
	}
	
	@Override
	public String getConsoleString() {
		return "Numbers from "+from+" to "+(reversed ? "less" : "plus")+" infinity";
	}
	
	@Override
	public StreamVar duplicate() {
		return new InfiniteIntegersStream(from, reversed);
	}
	
	public static void main(String[] args) {
		StreamVar s1 = new InfiniteIntegersStream(1, false);
		System.out.println(s1.next()+"");
		System.out.println(s1.next()+"");
		System.out.println(s1.next()+"");
		System.out.println(s1.next()+"");
	}
}
