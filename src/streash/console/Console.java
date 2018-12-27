package streash.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.naming.directory.NoSuchAttributeException;

import streash.vars.CharChain;
import streash.vars.Expression;
import streash.vars.Function;
import streash.vars.LambdaVar;
import streash.vars.Value;
import streash.vars.Number;

public class Console {
	private Scanner scanner;
	private Scanner command;
	private Map<String,Value> vars;
	private boolean npi = true;
	private ArrayList<String> order;
	
	public Console() {
		scanner = new Scanner(System.in);
		vars = new HashMap<String, Value>();
		order = new ArrayList<String>();
	}
	
	public void computeCommand() throws NoSuchAttributeException {
		if (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.equals("\n") || line.equals(""))
				return;
			
			command = new Scanner(line); command.hasNext();
			
			String begin;
			
			if (line.startsWith("/")) { computeMetaCommand(command.next()); return; }
			
			if (!line.contains("="))
				begin = nextTemp();
			else {
				begin = command.next();
				if (!(isAlpha(begin))) 
					throw new IllegalArgumentException("Illegal name for variable : "+begin);
					
				if (!(command.hasNext()))
					throw new IllegalArgumentException("Too few arguments for variable affectation");
	
				if (!(command.next().equals("=")))
					throw new IllegalArgumentException("Wrong call of var affectation");
			}
			if (npi) { reverseScanner(); }
			Value v = evaluateCommand();
			if (command.hasNext())
				throw new IllegalStateException("Too many arguments");
			
			putVar(begin, v);
		}
	}
	
	private void putVar(String name, Value v) {
		if (!(vars.containsKey(name)))
			order.add(name);
		vars.put(name, v);
	}
	
	private void reverseScanner() {
		ArrayList<String> list = new ArrayList<String>();
		while (command.hasNext()) {
			String arg = command.next();
			if (arg.startsWith("\""))
				list.add(0, CharChain.parse(arg, command).getConsoleString());
			else if (arg.startsWith("{"))
				list.add(0, reverseLambda(arg));
			else
				list.add(0, arg);
			
			if (command.hasNext())
				list.add(0, " ");
		}
		
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(s);
		}
		String c = sb.toString();
		command = new Scanner(c);
	}
	
	private String reverseLambda(String first) {
		if (first.length() > 1)
			throw new IllegalArgumentException("Syntax error");
		if (!command.hasNext())
			throw new IllegalStateException("Too few arguments");
		
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		
		ArrayList<String> list = new ArrayList<String>();
		
		while (command.hasNext()) {
			String arg = command.next();
			if (arg.endsWith("}")) {
				if (arg.length() > 1)
					throw new IllegalArgumentException("Syntax error");
				break;
			}
			
			if (arg.startsWith("\""))
				list.add(0, CharChain.parse(arg, command).getConsoleString());
			else if (arg.startsWith("{"))
				list.add(0, reverseLambda(arg));
			else
				list.add(0, arg);
			
			if (command.hasNext())
				list.add(0, " ");
		}
		for (String s : list) {
			sb.append(s);
		}
		
		sb.append(" }");
		return sb.toString();
	}
	
	private String nextTemp() {
		int i = 0;
		while (true) {
			if (!(vars.containsKey("temp"+i))) break;
			i++; continue;
		}
		return "temp"+i;
	}
	
	public Value evaluateCommand() {
		if (!command.hasNext())
			throw new IllegalStateException("Too few arguments");
		String expr = command.next();
		if (expr.matches("<.*>")) return evaluateFunction(expr);
		if (expr.startsWith("\"")) return CharChain.parse(expr, command);
		if (expr.startsWith("{")) return parseLambda(expr);
		if (isNumerical(expr)) return Number.parse(expr);
		
		Value var = vars.get(expr);
		if (var == null) throw new NoSuchElementException("No var named "+expr+" registered");
		return var;
	}
	
	private LambdaVar parseLambda(String first) {
		if (first.length() > 1)
			throw new IllegalArgumentException("Syntax Error");
		
		List<Expression> comprehension = new ArrayList<Expression>();
		while (command.hasNext()) {
			String next = command.next();
			if (next.endsWith("}")) {
				if (next.length() > 1)
					throw new IllegalArgumentException("Syntax Error");
				break;
			}
			if (next.equals("it")) {
				comprehension.add(null);
				continue;
			}
			if (next.matches("<.*>")) {
				comprehension.add(Function.getFunctionByName(next));
				continue;
			}
			if (next.startsWith("\"")) {
				comprehension.add(CharChain.parse(next, command));
				continue;
			}
			if (next.startsWith("{")) {
				comprehension.add(parseLambda(next));
				continue;
			}
			
			if (isNumerical(next)) {
				comprehension.add(Number.parse(next));
				continue;
			}
			
			Value var = vars.get(next);
			if (var == null) throw new NoSuchElementException("No var named "+next+" registered");
			comprehension.add(var);
		}
		return LambdaVar.parse(comprehension, npi);
	}
	
	public Value evaluateFunction(String functionName) {
		Function func = Function.getFunctionByName(functionName);
		for (int i = 0; i < func.argNumber(); i++) { 
			func.takeArgument(evaluateCommand(), npi);
		}
		return func.evaluate();
	}
	
	private boolean isNumerical(String expr) {
		return expr.matches("(-)?[0-9]*(/(-)?[0-9]*)?");
	}
	
	private boolean isAlpha(String expr) {
		return expr.matches("[a-zA-Z]{1}[a-zA-Z0-9_]*");
	}
	
	public void computeMetaCommand(String name) throws NoSuchAttributeException {
		if (name.equals("/printvar")) { printVar(); return; }
		if (name.equals("/printvars")) { printVars(); return; }
		if (name.equals("/notation")) { notation(); return; }
		if (name.equals("/quit")) { quit(); return; }
			
		throw new IllegalArgumentException("Unknown meta command");
	}
	
	public void printVar() throws NoSuchAttributeException {
		if (!command.hasNext()) throw new IllegalStateException("Too few arguments");

		String var = command.next();
		if (var.split(" ").length>1) throw new IllegalArgumentException("Too many arguents for command printvar");
		
		Value v = vars.get(var);
		if (v == null) throw new NoSuchAttributeException("There is no var named "+var);
		
		System.out.println(v.getConsoleString());
	}
	
	public void printVars() {
		if (command.hasNext()) {
			if (command.next().equals("alpha")) { printVarsAlpha(); return; }
			throw new IllegalArgumentException("Unkown argument");
		}
		
		order.forEach( (e) -> { 
			System.out.print(e+" : "); 
			System.out.println(vars.get(e).getConsoleString()); 
			});
	}
	
	public void printVarsAlpha() {
		if (command.hasNext()) throw new IllegalArgumentException("Too may arguments");
		
		order.stream().sorted().forEach( (e) -> { 
			System.out.print(e+" : "); 
			System.out.println(vars.get(e).getConsoleString()); 
			});
	}
	
	private void notation() {
		if (!(command.hasNext())) { throw new IllegalStateException("Too few arguments for function notation"); }
		
		String arg = command.next();
		if (command.hasNext()) throw new IllegalStateException("Too many arguments");
		
		if (arg.equals("npi")) {
			npi = true;
			return;
		}
		if (arg.equals("npr")) {
			npi = false;
			return;
		}
		
		throw new IllegalArgumentException("Unkown argument");
	}
	
	private void quit() {
		if (command.hasNext()) throw new IllegalStateException("Too few arguments for function quit");
		System.exit(0);
	}
	
	public static void main(String[] args) throws NoSuchAttributeException {
		Console log = new Console();
		while(true) {
//			try{
				log.computeCommand();
//			} catch (Exception e) {
//				System.out.print("Error : ");
//				System.out.println(e.getMessage());
//			}
		}
	}
}
