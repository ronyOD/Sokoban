package search;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class State<T> implements Comparable<State>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4298987147796853336L;
	/**
	 * 
	 */
	private T state;
	private State<T> cameFrom;
	private Action action;
	private double cost;
	
	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State(T state) {
		this.state = state; 
	}	
	
	@Override
	public String toString() {		
		return state.toString();
	}
	
	@Override
	public int hashCode() {	
		return state.hashCode();
	}
		
	public boolean equals(State s) {
		return state.equals(s.state);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		//change back to this.action = action -- if needed
		this.action = action;
	}

	@Override
	public int compareTo(State o) {
		if(this.cost < o.cost){
			return -1;
		}
		if(this.cost > o.cost){
			return 1;
		}
		return 0;
	}
}
