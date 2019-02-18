package programming_assignment_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class hanson_p1 {
	public static void main(String[] args) {
		NFA nfa = new NFA();
		String filename = args[0];
		String input = args[1];
		
		String line = null;
		
		try {
			FileReader fileReader = new FileReader(filename);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) {
				if(line.substring(0, 5).equals("state"))
					executeStateLine(nfa, line);
				if(line.substring(0, 10).equals("transition"))
					executeTransitionLine(nfa, line);
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<String> s = nfa.traverse(input);
		
		ArrayList<State> endStates = new ArrayList<>();
		
		for(String elem:s) {
			endStates.add(nfa.findState(elem));
		}
		
		if(hasAcceptState(endStates))
			printAccept(endStates);
		else
			printReject(endStates);
	}

	public static void executeStateLine(NFA nfa, String line) {
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
	
	public static void executeTransitionLine(NFA nfa, String line) {
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
	
	public static ArrayList<State> removeDuplicates(ArrayList<State> endStates) {
		ArrayList<State> ret = new ArrayList<>();
		
		for(State elem:endStates) {
			if(!ret.contains(elem))
				ret.add(elem);
		}
		return ret;
	}
	
	public static boolean hasAcceptState(ArrayList<State> endStates) {
		for(State elem:endStates) {
			if(elem.isAccept())
				return true;
		}
		return false;
	}
	
	public static void printAccept(ArrayList<State> endStates) {
		ArrayList<State> acceptStates = new ArrayList<>();
		
		for(State elem:endStates) {
			if(elem.isAccept())
				acceptStates.add(elem);
		}
		
		System.out.print("accept ");
		
		for(State elem:acceptStates) {
			System.out.print(elem.getName() + " ");
		}
		System.out.println();
	}
	
	public static void printReject(ArrayList<State> endStates) {
		System.out.print("reject ");
		
		for(State elem:endStates) {
			System.out.print(elem.getName() + " ");
		}
		System.out.println();
	}
}

class NFA {
	private State[] states = new State[1001];
	private State startState;
	private int currIndexStates;
	private ArrayList<State> endStates = new ArrayList<>();
	//private ArrayList<State> acceptStates = new ArrayList<>();
	
	public NFA() {
		currIndexStates = 0;
	}

	public State[] getStates() {
		return states;
	}
	
	public State getStartState() {
		return startState;
	}
	
//	public ArrayList<State> getAcceptStates() {
//		return acceptStates;
//	}
	
	public void addState(String name, boolean start, boolean accept) {
		State state = new State(name, start, accept);
		states[currIndexStates] = state;
		currIndexStates++;
		
		if(start)
			startState = state;
	
//		if(accept)
//			acceptStates.add(state);
	}
	
	public ArrayList<State> getEndStates() {
		return endStates;
	}
	
	public boolean stateDNE(String name) {
		for(int i = 0; i < currIndexStates; i++) {
			if(states[i].getName().equals(name))
				return false;
		}
		
//		for(State state:states) {
//			if(state.getName().equals(name))
//				return false;
//		}
		return true;
	}
	
	public void addTransition(String p, String x, String q) {
		if(stateDNE(p))
			addState(p, false, false);
		
		findState(p).addTransition(x, q);
	}
	
	public State findState(String name) {
		for(int i = 0; i < currIndexStates; i++) {
			if(states[i].getName().equals(name))
				return states[i];
		}
		
//		for(State elem:states) {
//			if(elem.getName().equals(name))
//				return elem;
//		}
		return null;
	}
	
	public Set<String> traverse(String s) {
		// difference from DFA: multiple prev states
		Set<String> prevStates = new TreeSet<String>();
		prevStates.add(getStartState().getName());
		for (int i=0; i<s.length(); i++) {
			String c = "" + s.charAt(i);
			Set<String> nextStates = new TreeSet<String>();
			// transition from each prev state to each of its next states
			for (String state : prevStates) {
				if (findState(state).symbolCount(c) != 0) {
					for(String[] elem:findState(state).getTransitions())
						if(elem[0] != null && elem[0].equals(c))
							nextStates.add(elem[1]);
				}
			}
			prevStates = nextStates;
		}
		return prevStates;
	}
}

class State extends NFA {
	private String[][] transitions = new String[100000][2];
	private int currIndexTransitions;
	//stores transitions as String arrays in the form [x,q]
	//x = transition symbol = transitions[index][0]
	//q = post-transition state = transitions[index][1]
	
	private final String name;
	private boolean start;
	private boolean accept;

	public State(String name, boolean start, boolean accept) {
		this.name = name;
		this.start = start;
		this.accept = accept;
	}

	public void addTransition(String x, String q) {
		String[] transition = new String[2];
		
		if(super.stateDNE(q))
			super.addState(q, false, false);
		
		transition[0] = x;
		transition[1] = q;
		
		transitions[currIndexTransitions] = transition;
		currIndexTransitions++;
	}
	
	public int symbolCount(String symbol) {
		int count = 0;
		
		for(int i = 0; i < currIndexTransitions; i++) {
			if(transitions[i][0].equals(symbol))
				count++;
		}
		
//		for(String[] elem:transitions) {
//			if(elem[0].equals(symbol))
//				count++;
//		}
		return count;
	}
	
	public String[][] getTransitions() {
		return transitions;
	}
	
	public int getCurrIndexTransitions() {
		return currIndexTransitions;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isStart() {
		return start;
	}
	
	public boolean isAccept() {
		return accept;
	}
}
