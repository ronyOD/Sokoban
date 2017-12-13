package search;

import java.util.HashMap;

public interface Searchable<T> {
	State<T> getInitialState();
	State<T> getGoalState();
	//List<State<T>> getAllPossibleStates(State<T> state);
	HashMap<Action, State<T>> getAllPossibleStates(State<T> state);
	int costOfMove(State<T> src, State<T> dest);
}
