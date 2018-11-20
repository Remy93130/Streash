package streash.vars;

import java.util.stream.Stream;

public interface StreamVar extends Value {
	@Override
	default String getType() { return "Stream"; }
	StreamVar duplicate();
	default long print(String source) {
		if (!(source.equals("STDOUT")))
			throw new IllegalStateException("Cannot print in an other stream than STDOUT atm");
		return this.print();	
	}
	long print();
	Stream<Value> getStream();
	default long len() { return this.duplicate().getStream().mapToLong(x -> 1L).sum(); }
	default Number sum() { return this.duplicate()
									.getStream()
									.map(x -> {
													if (!(x instanceof Number))
														throw new IllegalStateException("Cannot call sum() on Streams with others Values than Numbers");
													return (Number) x;
												}).reduce(new Number(0), (a, b) -> a.add(b)); 
							}
	
	default Number product() { return this.duplicate()
									.getStream()
									.map(x -> {
													if (!(x instanceof Number))
														throw new IllegalStateException("Cannot call product() on Streams with others Values than Numbers");
													return (Number) x;
												}).reduce(new Number(1), (a, b) -> a.mul(b)); 
							}
	
	default Number average() { 
		return this.sum().div(new Number(this.duplicate().len())); 
	}
	default Value min() {
		return this.duplicate().getStream().reduce(null, (prec, next) ->{
			if (prec == null)
				return next;
			if (prec.compareTo(next) > 0)
				return next;
			return prec;
		});
	}
	default Value max() {
		return this.duplicate().getStream().reduce(null, (prec, next) ->{
			if (prec == null)
				return next;
			if (prec.compareTo(next) < 0)
				return next;
			return prec;
		});
	}
	@Override
	default int compareTo(Object arg0) {
		return 0;
	}
}
