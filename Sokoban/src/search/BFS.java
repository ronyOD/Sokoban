package search;

import java.util.HashMap;
import java.util.HashSet;

public class BFS<T> extends CommonSearcher<T>{

	@Override
	public Solution search(Searchable<T> s) {
		
		addToOpenList(s.getInitialState());
		HashSet<State<T>> closedSet = new HashSet<State<T>>();
		
		while(openList.size() > 0){
			State<T> n = popOpenList();
			closedSet.add(n);
			
			if(n.equals(s.getGoalState())){
				return backTrace(n);
			}
			
			HashMap<Action, State<T>> successors = s.getAllPossibleStates(n);
			for(State<T> state : successors.values()){
				
				double newPathPrice = state.getCost() + s.costOfMove(n, state);
				if(!closedSet.contains(state) && !openList.contains(state)){
					state.setCameFrom(n);
/*					Action action = getKeyByValue(successors, state);
					System.out.println(action.getName());*/
					state.setAction(getKeyByValue(successors, state)); //new Action(getKeyByValue(successors, n)) - maybe?
					state.setCost(n.getCost() + s.costOfMove(n, state));
					addToOpenList(state);
					
				}
				else{
					if(newPathPrice < state.getCost()){
						if(!openList.contains(state) && closedSet.contains(state)){
							closedSet.remove(state);
							openList.add(state);
						}
						else if(openList.contains(state) && !closedSet.contains(state)){
							openList.remove(state);
							state.setCost(n.getCost() + s.costOfMove(n, state));
							openList.add(state);
						}
					}
				}
			}
			
			//System.out.println("no path");
			}
		return null;
	}

}
