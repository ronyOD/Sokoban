package plannable;

import java.util.LinkedList;

import search.Action;

public class MoveAction extends PlannerAction {

	private LinkedList<Action> results;

	public LinkedList<Action> getResults() {
		return results;
	}

	public void setResults(LinkedList<Action> results) {
		this.results = results;
	}
	
	public MoveAction(String type, String id, String value, LinkedList<Action> results) {
		super(type, id, value);
		this.setResults(results);
	}
}
