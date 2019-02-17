package programming_assignment_1;

import java.util.ArrayList;

public class State extends NFA {
	private ArrayList<String[]> transitions = new ArrayList<>();
	//stores transitions as String arrays in the form [x,q]
	//x = transition symbol = transitions[0]
	//q = post-transition state = transitions[1]
	
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
		
		transitions.add(transition);
	}
	
	public int symbolCount(String symbol) {
		int count = 0;
		
		for(String[] elem:transitions) {
			if(elem[0].equals(symbol))
				count++;
		}
		return count;
	}
	
	public ArrayList<String[]> getTransitions() {
		return transitions;
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
