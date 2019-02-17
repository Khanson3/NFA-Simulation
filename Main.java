package programming_assignment_1;

import java.io.*;
import java.util.ArrayList;

public class Main {
	static NFA nfa = new NFA();

	public static void main(String[] args) {
		String filename = args[0];
		String input = args[1];
		
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(filename);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				if(line.substring(0, 5).equals("state"))
					executeStateLine(line);
				if(line.substring(0, 10).equals("transition"))
					executeTransitionLine(line);
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<State> endStates = traverse(new ArrayList<State>(), nfa.getStartState(), input);
		
		for(State elem:endStates) {
			System.out.println(elem.getName());
		}
	}

	public static void executeStateLine(String line) {
		String[] args = line.split("\t");
		//args.length = 3 or
		//args.length = 4 if the state is a start state and an accept state
		
		String name = args[1];
		boolean start = false;
		boolean accept = false;
		
		if(arrContains(args, "start")) {
			start = true;
			
		}
		
		if(arrContains(args, "accept")) {
			accept = true;
		}
		
		nfa.addState(name, start, accept);
	}
	
	public static void executeTransitionLine(String line) {
		String[] args = line.split("\t");
		//args.length = 4
		
		//pre-transition state = args[1]
		//transition symbol = args[2]
		//post-transition state = args[3]
		
		nfa.addTransition(args[1], args[2], args[3]);
	}
	
	public static boolean arrContains(String[] arr, String s) {
		for(String elem:arr) {
			if(elem.equals(s))
				return true;
		}
		return false;
	}
	
	public static ArrayList<State> traverse(ArrayList<State> endStates, State currentState, String input) {
		ArrayList<String[]> transitions = currentState.getTransitions();
		
		if(input.length() == 0) {
			endStates.add(currentState);
			return endStates;
		}
		
		String symbol = input.substring(0, 1);
		
		if(currentState.symbolCount(symbol) == 0) {
			endStates.add(currentState);
			return endStates;
		}
		
		for(String[] elem:transitions) {
			if(elem[0].equals(symbol))
				return traverse(endStates, nfa.findState(elem[1]), input.substring(1, input.length()));
		}
		return null;
	}
}
