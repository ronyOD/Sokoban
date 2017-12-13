package search;


import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.PriorityQueue;


public abstract class CommonSearcher<T> implements Searcher<T> {
	
	
	protected PriorityQueue<State<T>> openList;
	protected int evaluatedNodes;
	
	public CommonSearcher() {
		openList = new PriorityQueue<State<T>>();
		evaluatedNodes = 0;
	}
	
	public PriorityQueue<State<T>> getOpenList() {
		return openList;
	}

	public void setOpenList(PriorityQueue<State<T>> openList) {
		this.openList = openList;
	}

	@Override
	public int getNumberOfNodesEvaluated() {		
		return evaluatedNodes;
	}
	
	public void addToOpenList(State<T> state){
		//optimization? on queue entry 
		this.openList.add(state);
	}
	
	protected State<T> popOpenList(){
		evaluatedNodes++;
		return openList.poll();
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value){
		for(Entry<T, E> entry : map.entrySet()){
			if(Objects.equals(value, entry.getValue())){
				return entry.getKey();
			}
		}
		return null;
		
	}
	
	protected Solution backTrace(State<T> goalState) {
		LinkedList<Action> actions = new LinkedList<Action>();
		
		State<T> currState = goalState;
		while (currState.getCameFrom() != null) {			
			actions.addFirst(currState.getAction());
			currState = currState.getCameFrom();
		}
		
		Solution sol = new Solution();
		sol.setActions(actions);
		return sol;
	}
}
