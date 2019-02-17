package programming_assignment_1;

import java.util.ArrayList;

public class NFA {
	private ArrayList<State> states = new ArrayList<>();
	private State startState;
	private ArrayList<State> acceptStates = new ArrayList<>();
	
	public NFA() {}

	public ArrayList<State> getStates() {
		return states;
	}
	
	public State getStartState() {
		return startState;
	}
	
	public ArrayList<State> getAcceptStates() {
		return acceptStates;
	}
	
	public void addState(String name, boolean start, boolean accept) {
		State state = new State(name, start, accept);
		states.add(state);
		
		if(start)
			startState = state;
		
		if(accept)
			acceptStates.add(state);
	}
	
	public boolean stateDNE(String name) {
		for(State state:states) {
			if(state.getName().equals(name))
				return false;
		}
		return true;
	}
	
	public void addTransition(String p, String x, String q) {
		if(stateDNE(p))
			addState(p, false, false);
		
		findState(p).addTransition(x, q);
	}
	
	public State findState(String name) {
		for(State elem:states) {
			if(elem.getName().equals(name))
				return elem;
		}
		return null;
	}
}
