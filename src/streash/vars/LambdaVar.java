package streash.vars;

import java.util.List;

public class LambdaVar implements Value{
	private List<Expression> list;
	private int point;
	private boolean npi;
	
	private LambdaVar(List<Expression> list, boolean npi) {
		this.list = list;
		this.point = 0;
		this.npi = npi;
	}
	
	public Value produce(Value v) {
		point = 0;
		if (list.get(point) == null)
			return v;
		if (list.get(point) instanceof Function) {
			point++;
			return productOfFunction((Function) list.get(point-1), v);
		}
		point = 0;
		return (Value) list.get(point);
	}
	
	private Value productOfFunction(Function f, Value v) {
		f.empty();
		for (int i = 0; i < f.argNumber(); i++) {
			if (list.get(point) == null) {
				f.takeArgument(v, npi);
				point++;
				continue;
			}
			if (list.get(point) instanceof Function) {
				point++;
				f.takeArgument(productOfFunction((Function) list.get(point-1), v), npi);
				continue;
			}
			f.takeArgument((Value) list.get(point), npi);
			point++;
		}
		return f.evaluate();
	}
	
	@Override
	public String getType() {
		return "Lambda";
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}
	@Override
	public String getConsoleString() {
		return "Lambda expression";
	}
	
	public static LambdaVar parse(List<Expression> values, boolean npi) {
		LambdaVar lambda = new LambdaVar(values, npi);
		if (values.size() < 1)
			throw new IllegalArgumentException("Syntax Error for lambda");
		int count = 1;
		int i = 0;
		
		while(count > 0) {
			if (values.size() <= i)
				throw new IllegalStateException("Too few arguments for lambda expression");
			count--;
			if (values.get(i) instanceof Function) {
				count += ((Function) values.get(i)).argNumber();
			}
			i++;
		}
		
		if (i != values.size())
			throw new IllegalStateException("Too much arguments for lambda expression");
		
		return lambda;
	}
	
	
}
