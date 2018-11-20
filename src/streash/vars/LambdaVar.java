package streash.vars;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

public class LambdaVar implements Value{
	private Map<String, Value> vars;
	private Predicate<Value> predicate;
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
	public LambdaVar(Map<String, Value> vars) {
		this.vars = vars;
	}
	public Predicate<Value> getPredicate(){
		return predicate;
	}
	public LambdaVar parse(Map<String, Value> vars, Scanner s) {
		
	}
}
