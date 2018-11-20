package streash.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.naming.directory.NoSuchAttributeException;

import streash.vars.CharChain;
import streash.vars.Function;
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
			if(npi) { reverseScanner(); }
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
			list.add(0, command.next());
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
		if (expr.startsWith("{")) throw new IllegalStateException("Unhandled lambda type for the moment");
		if (isNumerical(expr)) return Number.parse(expr);
		
		Value var = vars.get(expr);
		if (var == null) throw new NoSuchElementException("No var named "+expr+" registered");
		return var;
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

		if (name.equals("/printvars")) { 
			if (!(command.hasNext())) { printVars(); return; }
			if (command.next().equals("alpha")) {
				if (command.hasNext()) throw new IllegalArgumentException("Too may arguments");
				printVarsAlpha();
				return;
			}
			throw new IllegalArgumentException("Unkown argument");
		}
		if (name.equals("/notation")) {
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
		throw new IllegalArgumentException("Unknown command");
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
		order.forEach( (e) -> { 
			System.out.print(e+" : "); 
			System.out.println(vars.get(e).getConsoleString()); 
			});
	}
	public void printVarsAlpha() {
		order.stream().sorted().forEach( (e) -> { 
			System.out.print(e+" : "); 
			System.out.println(vars.get(e).getConsoleString()); 
			});
	}
	public static void main(String[] args) throws NoSuchAttributeException {
		Console log = new Console();
		while(true) {
			try{
				log.computeCommand();
			} catch (Exception e) {
				System.out.print("Error : ");
				System.out.println(e.getMessage());
			}
		}
		
	}
}
