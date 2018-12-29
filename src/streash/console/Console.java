package streash.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import streash.vars.CharChain;
import streash.vars.Expression;
import streash.vars.Function;
import streash.vars.LambdaVar;
import streash.vars.Value;
import streash.vars.Number;

import streash.errors.*;

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
	
/*
 ######################################################
 Compute command
 ######################################################
 */

	public void computeCommand() throws
		TooFewArgumentsException, 
		TooManyArgumentsException, 
		UnknownVariableNameExcpetion, 
		SyntaxErrorException {
		
		if (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String begin;
			
			if (line.equals("\n") || line.equals(""))
				return;
			
			command = new Scanner(line); 
			command.hasNext();
			
			if (line.startsWith("/")) { 
				computeMetaCommand(command.next()); 
				return; 
			}
			
			if (!line.contains("="))
				begin = nextTemp();
			else {
				begin = command.next();
				if (!(isAlpha(begin))) 
					throw new IllegalArgumentException("Illegal name for variable : "+begin);
					
				if (!(command.hasNext()))
					throw new TooFewArgumentsException("variable affectation");
	
				if (!(command.next().equals("=")))
					throw new IllegalArgumentException("Wrong call of var affectation");
			}
			
			if (npi) 
				reverseScanner();
			
			Value v = evaluateCommand();
			
			if (command.hasNext())
				throw new TooManyArgumentsException();
			
			putVar(begin, v);
		}
	}
	
	private void putVar(String name, Value v) {
		if (!(vars.containsKey(name)))
			order.add(name);
		vars.put(name, v);
	}
	
	private void reverseScanner() throws 
		TooFewArgumentsException, 
		SyntaxErrorException {
		
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
		command = new Scanner(sb.toString());
	}
	
	private String reverseLambda(String first) throws 
		TooFewArgumentsException, 
		SyntaxErrorException {
		
		if (first.length() > 1)
			throw new SyntaxErrorException();
		
		if (!command.hasNext())
			throw new TooFewArgumentsException("lambda expression");
		
		StringBuilder sb = new StringBuilder();
		ArrayList<String> list = new ArrayList<String>();

		while (command.hasNext()) {
			String arg = command.next();
			if (arg.endsWith("}")) {
				if (arg.length() > 1)
					throw new SyntaxErrorException();
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
		
		sb.append("{ ");
		for (String s : list) {
			sb.append(s);
		}
		sb.append(" }");
		
		return sb.toString();
	}
	
	private String nextTemp() {
		int i = 0;
		while (true) {
			if (!(vars.containsKey("temp"+i))) 
				break;
			i++; 
			continue;
		}
		return "temp"+i;
	}
	
	public Value evaluateCommand() throws 
		UnknownVariableNameExcpetion, 
		SyntaxErrorException {
		
		if (!command.hasNext())
			throw new IllegalStateException("Too few arguments");
		String expr = command.next();
		Expression e = getExpression(expr);
		if (e == null) throw new UnknownVariableNameExcpetion(expr);
		if (e instanceof Function)
			return evaluateFunction((Function) e);
		
		return (Value) e;
	}
	
	private Expression getExpression(String name) throws 
		UnknownVariableNameExcpetion, 
		SyntaxErrorException {
		
		if (isFunction(name))
			return Function.getFunctionByName(name);
			
		if (name.startsWith("\""))
			return CharChain.parse(name, command);
			
		if (name.startsWith("{"))
			return parseLambda(name);
		
		if (isNumerical(name))
			return Number.parse(name);
		
		return vars.get(name);
	}
	
	private LambdaVar parseLambda(String first) throws 
		UnknownVariableNameExcpetion, 
		SyntaxErrorException {
		
		if (first.length() > 1) throw new SyntaxErrorException();
		
		List<Expression> comprehension = new ArrayList<Expression>();
		
		while (command.hasNext()) {
			String next = command.next();
			
			if (next.endsWith("}")) {
				if (next.length() > 1)
					throw new SyntaxErrorException();
				break;
			}
			if (next.equals("it")) {
				comprehension.add(null);
				continue;
			}
			Expression e = getExpression(next);
			if (e == null) throw new UnknownVariableNameExcpetion(next);

			comprehension.add(e);
		}
		return LambdaVar.parse(comprehension, npi);
	}
	
	public Value evaluateFunction(Function f) throws 
		UnknownVariableNameExcpetion, 
		SyntaxErrorException {

		for (int i = 0; i < f.argNumber(); i++)
			f.takeArgument(evaluateCommand(), npi);

		return f.evaluate();
	}
	
/*
 ######################################################
Regex
 ######################################################
 */

	private boolean isNumerical(String expr) {
		return expr.matches("(-)?[0-9]*(/(-)?[0-9]*)?");
	}
	
	private boolean isAlpha(String expr) {
		return expr.matches("[a-zA-Z]{1}[a-zA-Z0-9_]*");
	}
	
	private boolean isFunction(String expr) {
		return expr.matches("<.*>");
	}
	
/*
 ######################################################
 Meta commands
 ######################################################
 */
	
	public void computeMetaCommand(String name) throws 
		TooFewArgumentsException, 
		TooManyArgumentsException, 
		UnknownVariableNameExcpetion {
		if (name.equals("/printvar")) { printVar(); return; }
		if (name.equals("/printvars")) { printVars(); return; }
		if (name.equals("/notation")) { notation(); return; }
		if (name.equals("/quit")) { quit(); return; }
		if (name.equals("/save")) { save(); return; }
		if (name.equals("/load")) { load(); return; }
			
		throw new IllegalArgumentException("Unknown meta command");
	}
	
	public void printVar() throws 
		UnknownVariableNameExcpetion {
		
		if (!command.hasNext()) throw new IllegalStateException("Too few arguments");

		String var = command.next();
		if (var.split(" ").length>1) throw new IllegalArgumentException("Too many arguents for command printvar");
		
		Value v = vars.get(var);
		if (v == null) throw new UnknownVariableNameExcpetion(var);
		
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
	
	private void notation() throws 
		TooFewArgumentsException, 
		TooManyArgumentsException {
		
		if (!(command.hasNext())) { throw new TooFewArgumentsException("function notation"); }
		
		String arg = command.next();
		if (command.hasNext()) throw new TooManyArgumentsException("function notation");
		
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
	
	private void save() throws TooManyArgumentsException {
		JSONConverter c = new JSONConverter();
		c.addMap(vars);
		if (command.hasNext()) {
			String next = command.next();
			if (command.hasNext())
				throw new TooManyArgumentsException("command save");
			c.writeJSON(next);
			return;
		}
		c.writeJSON();
	}
	
	private void load() throws TooManyArgumentsException {
		JSONConverter c = new JSONConverter();
		if (command.hasNext()) {
			String next = command.next();
			if (command.hasNext())
				throw new TooManyArgumentsException("command save");
			c.readJSON(next);
		}else
			c.readJSON();
		vars = c.getMap();
		updateOrder();
	}
	
	private void updateOrder() {
		order = new ArrayList<String>();
		for (String key : vars.keySet())
			order.add(key);
	}
	
	public static void main(String[] args) throws TooFewArgumentsException, TooManyArgumentsException, UnknownVariableNameExcpetion, SyntaxErrorException {
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
