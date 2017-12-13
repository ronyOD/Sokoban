package search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Dijkstra<T> extends CommonSearcher<T> {

	private PriorityQueue<State<T>> openList;
	private HashSet<State<T>> openSet;
	private HashSet<State<T>> closedSet;
	private HashMap<Action, State<T>> successors;
	
	public Dijkstra() {
		this.openList = new PriorityQueue<>();
		this.openSet = new HashSet<>();
		this.closedSet = new HashSet<>();
		this.evaluatedNodes=0;
	}
	
	@Override
	public Solution search(Searchable<T> s) {
		State<T> initialState = s.getInitialState();
		this.openList.add(initialState);
		this.openSet.add(initialState);
		
		while(!this.openList.isEmpty()){
			
			State<T> state = this.openList.poll();
			this.openSet.remove(state);
			this.evaluatedNodes++;
			
			this.closedSet.add(state);
			this.successors = s.getAllPossibleStates(state);
			
			for(Action a : this.successors.keySet()){
				State<T> n = this.successors.get(a);
				if(!this.closedSet.contains(n)){
					if(!this.openSet.contains(n)){
						n.setCameFrom(state);
						n.setAction(a);
						this.openList.add(n);
						this.openSet.add(n);
					}
					else{
						State<T> st = null;
						for(State<T> ost : this.openList){
							if(ost.equals(n)){
								st = ost;
								break;
							}
						}
						if(st != null){
							if(n.getCost() < st.getCost()){
								this.openList.remove(st);
								this.openSet.remove(st);
								n.setCameFrom(state);
								n.setAction(a);
								this.openList.add(n);
								this.openSet.add(n);
							}
						}
					}
				}
			}
		}
		if(this.closedSet.contains((s.getGoalState()))){
			for(State<T> state : this.closedSet){
				if(state.equals(s.getGoalState())){
					return backTrace(state);
				}
			}
		}
		return null;
	}

}
