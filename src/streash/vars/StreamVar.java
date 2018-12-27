package streash.vars;

import java.util.ArrayList;
import java.util.List;

import streash.vars.stream.NumberStreamVar;

public interface StreamVar extends Value {
	@Override
	default int compareTo(Object arg0) { return 0; }
	StreamVar duplicate();
	public static String type() { return "Stream"; }
	boolean hasNext();
	Value next();

	default long print(String source) {
		if (!(source.equals("STDOUT")))
			throw new IllegalStateException("Cannot print in an other stream than STDOUT atm");
		return this.print();	
	}
	
	default List<Value> collect() {
		List<Value> to = new ArrayList<Value>();
		while(hasNext())
			to.add(next());
		return to;
	}
	
	default long len() { 
		StreamVar s = this.duplicate();
		long l = 0;
		while (s.hasNext()) {
			l++;
			s.next();
		}
		return l;
	}
	
	default long print() {
		StreamVar s = this.duplicate();
		long l = 0;
		while (s.hasNext()) {
			l++;
			System.out.println(s.next().toString());
		}
		return l;
	}
	
	@Override
	default String getType() {
		return "Stream";
	}
	
	default Number sum() {
		if (!(this instanceof NumberStreamVar))
			throw new IllegalStateException("Cannot call sum() with a non-numbered Stream");
		StreamVar s = this.duplicate();
		Number sum = new Number(0);
		while (s.hasNext()) {
			sum = sum.add((Number) s.next());
		}
		return sum;
	}
	
	default Value get(long index) {
		int i = 0;
		StreamVar s = duplicate();
		while (s.hasNext()) {
			if (i == index)
				return s.next();
			i++;
			s.next();
		}
		throw new IllegalStateException("Index out of bounds : index "+index+" size "+i);
	}
	
	default Number product() { 
		if (!(this instanceof NumberStreamVar))
			throw new IllegalStateException("Cannot call product() with a non-numbered Stream");
		StreamVar s = this.duplicate();
		Number sum = new Number(1);
		while (s.hasNext()) {
			sum = sum.mul((Number) s.next());
		}
		return sum; 
	}
	
	default Number average() { 
		if (!(this instanceof NumberStreamVar))
			throw new IllegalStateException("Cannot call average() with a non-numbered Stream");
		return sum().div((new Number(this.len())));
	}
	
	default Value min() {
		StreamVar s = this.duplicate();
		if (!(s.hasNext()))
			throw new IllegalStateException("Empty stream");
		Value min = s.next();
		Value temp;
		while (s.hasNext()) {
			temp = s.next();
			if (temp.compareTo(min) < 0)
				min = temp;
		}
		return min;
	}
	default Value max() {
		StreamVar s = this.duplicate();
		if (!(s.hasNext()))
			throw new IllegalStateException("Empty stream");
		Value max = s.next();
		Value temp;
		while (s.hasNext()) {
			temp = s.next();
			if (temp.compareTo(max) > 0)
				max = temp;
		}
		return max;
	}
}
