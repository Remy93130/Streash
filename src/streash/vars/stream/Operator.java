package streash.vars.stream;

import java.util.function.BiFunction;

import streash.vars.Value;

public enum Operator {
	SUP(">", (x, y) -> { return (x.compareTo(y) > 0); } ),
	DIF("<>", (x, y) -> { return (x.compareTo(y) != 0); }),
	INF("<", (x, y) -> { return (x.compareTo(y) < 0); }),
	EQU("=", (x, y) -> { return (x.compareTo(y) == 0); }),
	SUPE(">=", (x, y) -> { return (x.compareTo(y) >= 0); }),
	INFE("<=", (x, y) -> { return (x.compareTo(y) <= 0); });
	
	private BiFunction<Value, Value, Boolean> comparator;
	private String call;
	
	private Operator(String call, BiFunction<Value, Value, Boolean> comparator) {
		this.call = call;
		this.comparator = comparator;
	}
	public static Operator getInstance(String call) {
		for (Operator o : Operator.values())
			if (o.call.equals((call)))
				return o;
		throw new IllegalArgumentException("Unknown operator");
	}
	
	public boolean match(Value v1, Value v2) {
		return comparator.apply(v1,  v2);
	}
	
	public String getCall() {
		return call;
	}
}